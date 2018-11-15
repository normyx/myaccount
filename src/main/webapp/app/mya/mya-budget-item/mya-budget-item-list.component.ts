import { Component, OnInit, Input, OnDestroy, OnChanges } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';

import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { ICategory } from 'app/shared/model/category.model';
import { Principal } from 'app/core';
import { MyaBudgetItemService } from './mya-budget-item.service';
import { Moment } from 'moment';
import * as moment from 'moment';
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
    selectedMonth: Moment;
    monthsToDisplay: Moment[];
    @Input()
    filterSelectedCategory: ICategory;
    @Input()
    filterContains: string;

    constructor(private budgetItemService: MyaBudgetItemService, private jhiAlertService: JhiAlertService, private principal: Principal) {}

    resetMonthsToDisplay() {
        // set the NUMBER_OF_MONTHS_TO_DISPLAY elements of monthsToDisplay
        const mtd = new Array(this.NUMBER_OF_MONTHS_TO_DISPLAY);
        let i: number;
        for (i = 0; i < this.NUMBER_OF_MONTHS_TO_DISPLAY; i++) {
            const m = moment();
            m.year(this.selectedMonth.year());
            m.month(this.selectedMonth.month());
            m.date(1);
            mtd[i] = m;
        }
        this.monthsToDisplay = mtd;
    }

    loadAll() {
        console.warn(this.selectedMonth);
        this.resetMonthsToDisplay();
        let categoryId;
        if (this.filterSelectedCategory) {
            categoryId = this.filterSelectedCategory.id;
        }
        this.budgetItemService
            .findEligible(
                this.monthsToDisplay[0].format('YYYY-MM-DD'),
                this.monthsToDisplay[this.monthsToDisplay.length - 1].format('YYYY-MM-DD'),
                this.filterContains,
                categoryId
            )
            .subscribe(
                (res: HttpResponse<IBudgetItem[]>) => {
                    console.warn(res.body);
                    this.budgetItems = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        // this.registerChangeInBudgetItems();
    }

    ngOnChanges() {
        this.loadAll();
    }

    ngOnDestroy() {}

    trackId(index: number, item: IBudgetItem) {
        return item.id;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
