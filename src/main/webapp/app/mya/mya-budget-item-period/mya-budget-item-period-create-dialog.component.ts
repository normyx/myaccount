import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBudgetItemPeriod, BudgetItemPeriod } from 'app/shared/model/budget-item-period.model';
import { MyaBudgetItemPeriodService } from './mya-budget-item-period.service';
import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { IOperation } from 'app/shared/model/operation.model';
import * as Moment from 'moment';

@Component({
    selector: 'jhi-mya-budget-item-period-create-dialog',
    templateUrl: './mya-budget-item-period-create-dialog.component.html'
})
export class MyaBudgetItemPeriodCreateDialogComponent implements OnInit {
    newBudgetItemPeriod: IBudgetItemPeriod;
    budgetItemPeriod: IBudgetItemPeriod;
    isSaving: boolean;
    day: number;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private budgetItemPeriodService: MyaBudgetItemPeriodService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.day = 1;
        this.newBudgetItemPeriod = new BudgetItemPeriod();
        this.newBudgetItemPeriod.amount = 0;
        this.newBudgetItemPeriod.isSmoothed = false;
        this.newBudgetItemPeriod.isRecurrent = false;
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.newBudgetItemPeriod.month = this.budgetItemPeriod.month;
        if (!this.newBudgetItemPeriod.isSmoothed) {
            this.newBudgetItemPeriod.date = Moment();
            this.newBudgetItemPeriod.date.year(this.newBudgetItemPeriod.month.year());
            this.newBudgetItemPeriod.date.month(this.newBudgetItemPeriod.month.month());
            this.newBudgetItemPeriod.date.date(this.day);
        }
        this.newBudgetItemPeriod.budgetItemId = this.budgetItemPeriod.budgetItemId;
        this.newBudgetItemPeriod.month = this.budgetItemPeriod.month;
        this.subscribeToSaveResponse(this.budgetItemPeriodService.create(this.newBudgetItemPeriod));
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
