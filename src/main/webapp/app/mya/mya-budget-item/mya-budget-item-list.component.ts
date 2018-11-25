import { Component, OnInit, Input, OnDestroy, OnChanges } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Subscription } from 'rxjs';

import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { ICategory } from 'app/shared/model/category.model';
import { Principal } from 'app/core';
import { MyaBudgetItemService } from './mya-budget-item.service';
import * as Moment from 'moment';
import 'moment/locale/fr';

@Component({
    selector: 'jhi-mya-budget-item-list',
    templateUrl: './mya-budget-item-list.component.html'
})
export class MyaBudgetItemListComponent implements OnInit, OnDestroy, OnChanges {
    readonly NUMBER_OF_MONTHS_TO_DISPLAY: number = 6;
    budgetItems: IBudgetItem[];
    currentAccount: any;
    @Input()
    selectedMonth: Date;
    monthsToDisplay: Date[];
    @Input()
    filterSelectedCategory: ICategory;
    @Input()
    filterContains: string;
    eventSubscriber: Subscription;

    constructor(private budgetItemService: MyaBudgetItemService, private jhiAlertService: JhiAlertService, private eventManager: JhiEventManager, private principal: Principal) { }

    resetMonthsToDisplay() {
        // set the NUMBER_OF_MONTHS_TO_DISPLAY elements of monthsToDisplay
        const mtd = new Array(this.NUMBER_OF_MONTHS_TO_DISPLAY);
        let i: number;
        for (i = 0; i < this.NUMBER_OF_MONTHS_TO_DISPLAY; i++) {
            mtd[i] = new Date(this.selectedMonth.getFullYear(), this.selectedMonth.getMonth() + i, 1);
        }
        this.monthsToDisplay = mtd;
    }

    loadAll() {
        this.resetMonthsToDisplay();
        let categoryId;
        if (this.filterSelectedCategory) {
            categoryId = this.filterSelectedCategory.id;
        }
        this.budgetItemService
            .findEligible(
                Moment(this.monthsToDisplay[0]).format('YYYY-MM-DD'),
                Moment(this.monthsToDisplay[this.monthsToDisplay.length - 1]).format('YYYY-MM-DD'),
                this.filterContains,
                categoryId
            )
            .subscribe(
                (res: HttpResponse<IBudgetItem[]>) => {
                    this.budgetItems = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.registerChangeInBudgetItemList();
    }

    ngOnChanges() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
    }

    registerChangeInBudgetItemList() {
        this.eventSubscriber = this.eventManager.subscribe('myaBudgetItemListModification', response => this.loadAll());
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBudgetItem) {
        return item.id;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
