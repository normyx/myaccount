import { Component, OnInit, Input, OnChanges, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { IBudgetItem, BudgetItem } from 'app/shared/model/budget-item.model';
import { IBudgetItemPeriod } from 'app/shared/model/budget-item-period.model';
import { MyaBudgetItemPeriodService } from './../mya-budget-item-period/mya-budget-item-period.service';
import { MyaBudgetItemService } from './mya-budget-item.service';
import { Subscription } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import * as Moment from 'moment';

@Component({
    /* tslint:disable-next-line */
    selector: '[jhi-mya-budget-item-row]',
    templateUrl: './mya-budget-item-row.component.html'
})
export class MyaBudgetItemRowComponent implements OnInit, OnChanges, OnDestroy {
    @Input()
    budgetItem: IBudgetItem;
    @Input()
    isFirstInList: boolean;
    @Input()
    isLastInList: boolean;
    @Input()
    monthsToDisplay: Date[];
    budgetItemPeriods: IBudgetItemPeriod[][];
    eventSubscriber: Subscription;
    lastBudgetItemPeriodOfBudgetItem: IBudgetItemPeriod;

    constructor(
        private budgetItemService: MyaBudgetItemService,
        private budgetItemPeriodService: MyaBudgetItemPeriodService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {}

    ngOnChanges() {
        this.loadAll();
        this.registerChangeInBudgetItemRow();
    }

    loadAll() {
        this.budgetItemService.find(this.budgetItem.id).subscribe(
            (res: HttpResponse<IBudgetItem>) => {
                this.budgetItem = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        const lastMonth: Date = this.monthsToDisplay[this.monthsToDisplay.length - 1];
        const firstMonth: Date = this.monthsToDisplay[0];
        const criteria = {
            'budgetItemId.equals': this.budgetItem.id,
            'month.greaterOrEqualThan': Moment(firstMonth).format('YYYY-MM-DD'),
            'month.lessOrEqualThan': Moment(lastMonth).format('YYYY-MM-DD'),
            sort: ['isRecurrent,desc']
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
                        const correspondingBudgetItemPeriod: IBudgetItemPeriod[] = budgetItemPeriodQueryResult.filter(function(el) {
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
        this.eventSubscriber = this.eventManager.subscribe('myaBudgetItemRowModification' + this.budgetItem.id, response => this.loadAll());
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    public extend() {
        this.budgetItemService.extend(this.budgetItem.id).subscribe(
            (res: HttpResponse<void>) => {
                this.eventManager.broadcast({ name: 'myaBudgetItemRowModification' + this.budgetItem.id, content: 'OK' });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    public isLast(bip: IBudgetItemPeriod): boolean {
        return this.lastBudgetItemPeriodOfBudgetItem && this.lastBudgetItemPeriodOfBudgetItem.id === bip.id && bip.isRecurrent;
    }

    public up() {
        this.budgetItemService.up(this.budgetItem.id).subscribe(
            (res: HttpResponse<void>) => {
                this.eventManager.broadcast({ name: 'myaBudgetItemListModification', content: 'OK' });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    public down() {
        this.budgetItemService.down(this.budgetItem.id).subscribe(
            (res: HttpResponse<void>) => {
                this.eventManager.broadcast({ name: 'myaBudgetItemListModification', content: 'OK' });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
}
