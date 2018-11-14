import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBudgetItemPeriod } from 'app/shared/model/budget-item-period.model';
import { MyaBudgetItemPeriodService } from './mya-budget-item-period.service';
import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { OperationService } from 'app/entities/operation/operation.service';
import { IOperation } from 'app/shared/model/operation.model';

@Component({
    selector: 'jhi-mya-budget-item-period-update-dialog',
    templateUrl: './mya-budget-item-period-update-dialog.component.html'
})
export class MyaBudgetItemPeriodUpdateDialogComponent implements OnInit {
    budgetItemPeriod: IBudgetItemPeriod;
    isSaving: boolean;
    day: number;
    modifyNext: boolean;
    operationsClose: IOperation[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private budgetItemPeriodService: MyaBudgetItemPeriodService,
        private operationService: OperationService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        if (this.budgetItemPeriod.date) {
            this.day = this.budgetItemPeriod.date.date();
        }
        if (!this.budgetItemPeriod.isSmoothed) {
            this.operationService.findCloseToBudgetItemPeriod(this.budgetItemPeriod.id).subscribe(
                (res: HttpResponse<IOperation[]>) => {
                    this.operationsClose = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        }
        if (this.budgetItemPeriod.isRecurrent) {
            this.modifyNext = true;
        } else {
            this.modifyNext = false;
        }
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    onOperationSelect(isChecked: boolean) {
        const selectedOperation: IOperation = this.operationsClose.find(el => el.id === this.budgetItemPeriod.operationId);
        this.budgetItemPeriod.date = selectedOperation.date;
        this.budgetItemPeriod.amount = selectedOperation.amount;
        this.day = this.budgetItemPeriod.date.date();
    }
    save() {
        this.isSaving = true;
        if (!this.budgetItemPeriod.isSmoothed) {
            this.budgetItemPeriod.date.year(this.budgetItemPeriod.month.year());
            this.budgetItemPeriod.date.month(this.budgetItemPeriod.month.month());
            this.budgetItemPeriod.date.date(this.day);
        }
        if (this.modifyNext && this.budgetItemPeriod.isRecurrent) {
            this.subscribeToSaveResponse(this.budgetItemPeriodService.updateWithNext(this.budgetItemPeriod));
        } else {
            this.subscribeToSaveResponse(this.budgetItemPeriodService.update(this.budgetItemPeriod));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBudgetItem>>) {
        result.subscribe((res: HttpResponse<IBudgetItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.eventManager.broadcast({ name: 'myaBudgetItemRowModification' + this.budgetItemPeriod.budgetItemId, content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(true);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBudgetItemById(index: number, item: IBudgetItem) {
        return item.id;
    }

    trackOperationById(index: number, item: IOperation) {
        return item.id;
    }
}
