import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBudgetItemPeriod } from 'app/shared/model/budget-item-period.model';
import { BudgetItemPeriodService } from './budget-item-period.service';
import { BudgetItemService } from '../budget-item/budget-item.service';
import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { OperationService } from '../operation/operation.service';
import { IOperation } from 'app/shared/model/operation.model';

@Component({
    selector: 'jhi-budget-item-period-update-dialog',
    templateUrl: './budget-item-period-update-dialog.component.html'
})
export class BudgetItemPeriodUpdateDialogComponent implements OnInit {
    budgetItemPeriod: IBudgetItemPeriod;
    isSaving: boolean;
    day: number;
    modifyNext: boolean;
    operationsClose: IOperation[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private budgetItemPeriodService: BudgetItemPeriodService,
        private operationService: OperationService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.modifyNext = true;
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

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBudgetItemPeriod>>) {
        result.subscribe(
            (res: HttpResponse<IBudgetItemPeriod>) => this.onSaveSuccess(res.body),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: IBudgetItemPeriod) {
        this.eventManager.broadcast({ name: 'budgetItemRowModification' + this.budgetItemPeriod.budgetItemId, content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
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

@Component({
    selector: 'jhi-budget-item-period-update-popup',
    template: ''
})
export class BudgetItemPeriodUpdatePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ budgetItemPeriod }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BudgetItemPeriodUpdateDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.budgetItemPeriod = budgetItemPeriod;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
