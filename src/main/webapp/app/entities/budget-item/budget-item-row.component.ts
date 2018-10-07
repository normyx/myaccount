import { Component, OnInit, Input, OnChanges, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { IBudgetItemPeriod } from 'app/shared/model/budget-item-period.model';
import { BudgetItemPeriodService } from './../budget-item-period/budget-item-period.service';
import { BudgetItemService } from './budget-item.service';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import * as Moment from 'moment';

@Component({
    /* tslint:disable-next-line */
    selector: '[jhi-budget-item-row]',
    templateUrl: './budget-item-row.component.html'
})
export class BudgetItemRowComponent implements OnInit, OnChanges, OnDestroy {
    @Input() budgetItem: IBudgetItem;
    @Input() monthsToDisplay: Date[];
    budgetItemPeriods: IBudgetItemPeriod[];
    eventSubscriber: Subscription;
    lastBudgetItemPeriodOfBudgetItem: IBudgetItemPeriod;

    constructor(
        private budgetItemService: BudgetItemService,
        private budgetItemPeriodService: BudgetItemPeriodService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {}

    ngOnChanges() {
        this.loadAll();
        this.registerChangeInBudgetItemRow();
    }

    loadAll() {
        const lastMonth: Date = this.monthsToDisplay[this.monthsToDisplay.length - 1];
        const firstMonth: Date = this.monthsToDisplay[0];
        const criteria = {
            'budgetItemId.equals': this.budgetItem.id,
            'month.greaterOrEqualThan': Moment(firstMonth).format('YYYY-MM-DD'),
            'month.lessOrEqualThan': Moment(lastMonth).format('YYYY-MM-DD')
        };
        let budgetItemPeriodQueryResult: IBudgetItemPeriod[];
        this.budgetItemPeriodService.query(criteria).subscribe(
            (res: HttpResponse<IBudgetItemPeriod[]>) => {
                budgetItemPeriodQueryResult = res.body;
                this.budgetItemPeriods = new Array(this.monthsToDisplay.length);
                let i: number;
                if (budgetItemPeriodQueryResult) {
                    // if result is defined
                    for (i = 0; i < this.monthsToDisplay.length; i++) {
                        const month: Date = this.monthsToDisplay[i];
                        // find corresponding budgetItemPeriod
                        const correspondingBudgetItemPeriod: IBudgetItemPeriod = budgetItemPeriodQueryResult.find(function(el) {
                            return el.month.month() === month.getMonth() && el.month.year() === month.getFullYear();
                        });
                        this.budgetItemPeriods[i] = correspondingBudgetItemPeriod;
                        // console.log(correspondingBudgetItemPeriod);
                    }
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.budgetItemService.lastBudgetItem(this.budgetItem.id).subscribe(
            (res: HttpResponse<IBudgetItemPeriod>) => {
                this.lastBudgetItemPeriodOfBudgetItem = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        // console.log(this.budgetItemPeriods);
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    registerChangeInBudgetItemRow() {
        this.eventSubscriber = this.eventManager.subscribe('budgetItemRowModification' + this.budgetItem.id, response => this.loadAll());
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    public extend() {
        this.budgetItemService.extend(this.budgetItem.id).subscribe(
            (res: HttpResponse<void>) => {
                this.eventManager.broadcast({ name: 'budgetItemRowModification' + this.budgetItem.id, content: 'OK' });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    public isLast(bip: IBudgetItemPeriod): boolean {
        return this.lastBudgetItemPeriodOfBudgetItem && this.lastBudgetItemPeriodOfBudgetItem.id === bip.id;
    }
}
