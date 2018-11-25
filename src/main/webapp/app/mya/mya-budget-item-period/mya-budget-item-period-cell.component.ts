import { Component, OnInit, Input, OnChanges, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { IBudgetItemPeriod } from 'app/shared/model/budget-item-period.model';
import { OperationService } from 'app/entities/operation/operation.service';
import { Subscription } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import * as Moment from 'moment'; 

@Component({
    /* tslint:disable-next-line */
    selector: 'jhi-mya-budget-item-period-cell',
    templateUrl: './mya-budget-item-period-cell.component.html'
})
export class MyaBudgetItemPeriodCellComponent implements OnInit, OnChanges {
    @Input() budgetItemPeriod: IBudgetItemPeriod;
    @Input() isFirst: boolean;
    @Input() isLast: boolean;
    countOperationsClose: number = 0;
    eventSubscriber: Subscription;
    lastBudgetItemPeriodOfBudgetItem: IBudgetItemPeriod;
    isInFuture: boolean;

    constructor(
        private operationService: OperationService,
        private jhiAlertService: JhiAlertService,
    ) { }

    ngOnInit() {
    }

    ngOnChanges() {
        this.loadAll();
    }

    loadAll() {
        if (this.isFirst) {
            this.operationService.countCloseToBudgetItemPeriod(this.budgetItemPeriod.id).subscribe(
                (res: HttpResponse<number>) => {
                    this.countOperationsClose = res.body;
                    console.warn(this.countOperationsClose);
                },
                (res: HttpErrorResponse) => {
                    this.onError(res.message);
                    console.error(res.message);
                }
            );
        }
        Moment now = moment();
        isInFuture =  (moment.min(budgetItemPeriod.month, now) === now);
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
