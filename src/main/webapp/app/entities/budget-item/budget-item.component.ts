import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { Principal } from 'app/core';
import { BudgetItemService } from './budget-item.service';
import * as Moment from 'moment';
import 'moment/locale/fr';

@Component({
    selector: 'jhi-budget-item',
    templateUrl: './budget-item.component.html'
})
export class BudgetItemComponent implements OnInit, OnDestroy {
    readonly NUMBER_OF_MONTHS_TO_DISPLAY: number = 6;
    budgetItems: IBudgetItem[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    selectedMonth: Date;
    monthsToDisplay: Date[];
    selectedYearAsText: string;
    selectedMonthIndex: number;
    selectedMonthAsText: string;

    constructor(
        private budgetItemService: BudgetItemService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
        // get the current time
        const current: Date = new Date(Date.now());
        // set the selected date to this month
        this.selectedMonth = new Date(current.getFullYear(), current.getMonth(), 1);
        this.resetMonthsToDisplay();
    }

    resetMonthsToDisplay() {
        // set the NUMBER_OF_MONTHS_TO_DISPLAY elements of monthsToDisplay
        const mtd = new Array(this.NUMBER_OF_MONTHS_TO_DISPLAY);
        let i: number;
        for (i = 0 ; i < this.NUMBER_OF_MONTHS_TO_DISPLAY ; i++) {
            mtd[i] = new Date(this.selectedMonth.getFullYear(), this.selectedMonth.getMonth() + i, 1);
        }
        this.monthsToDisplay = mtd;
    }

    onChange(event: { monthIndex: number, year: number }) {
        this.selectedYearAsText = event.year.toString();
        this.selectedMonthIndex = event.monthIndex;
        this.selectedMonthAsText = Moment().month(event.monthIndex).format('MMMM');
        this.selectedMonth = new Date(event.year, event.monthIndex, 1 );
        console.warn(this.selectedYearAsText, this.selectedMonthAsText, `(month index: ${this.selectedMonthIndex})`);
        this.resetMonthsToDisplay();
        this.eventManager.broadcast({ name: 'budgetItemListModification', content: 'OK'});
    }

    loadAll() {
        if (this.currentSearch) {
            this.budgetItemService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IBudgetItem[]>) => (this.budgetItems = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
         const criteria = {
            'month.greaterOrEqualThan': Moment(this.monthsToDisplay[0]).format('YYYY-MM-DD'),
            'month.lessOrEqualThan': Moment(this.monthsToDisplay[this.monthsToDisplay.length - 1]).format('YYYY-MM-DD'),
        };
        this.budgetItemService.all(criteria).subscribe(
            (res: HttpResponse<IBudgetItem[]>) => {
                this.budgetItems = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBudgetItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBudgetItem) {
        return item.id;
    }

    registerChangeInBudgetItems() {
        this.eventSubscriber = this.eventManager.subscribe('budgetItemListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
