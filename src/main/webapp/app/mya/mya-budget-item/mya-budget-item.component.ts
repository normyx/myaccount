import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { ICategory } from 'app/shared/model/category.model';
import { Principal } from 'app/core';
import { CategoryService } from 'app/entities/category/category.service';

import { FormControl } from '@angular/forms';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatDatepicker } from '@angular/material/datepicker';

// Depending on whether rollup is used, moment needs to be imported differently.
// Since Moment.js doesn't have a default export, we normally need to import using the `* as`
// syntax. However, rollup creates a synthetic default module and we thus need to import it using
// the `default as` syntax.
import * as _moment from 'moment';
// tslint:disable-next-line:no-duplicate-imports
import { Moment } from 'moment';

const moment = _moment;

// See the Moment.js docs for the meaning of these formats:
// https://momentjs.com/docs/#/displaying/format/
export const MY_FORMATS = {
    parse: {
        dateInput: 'MM/YYYY'
    },
    display: {
        dateInput: 'MM/YYYY',
        monthYearLabel: 'MMM YYYY',
        dateA11yLabel: 'LL',
        monthYearA11yLabel: 'MMMM YYYY'
    }
};

@Component({
    selector: 'jhi-mya-budget-item',
    templateUrl: './mya-budget-item.component.html',
    providers: [
        // `MomentDateAdapter` can be automatically provided by importing `MomentDateModule` in your
        // application's root module. We provide it at the component level here, due to limitations of
        // our example generation script.
        { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },

        { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS }
    ]
})
export class MyaBudgetItemComponent implements OnInit {
    currentAccount: any;
    // selectedMonth: Date;
    filterCategories: ICategory[];
    filterSelectedCategory: ICategory;
    filterContains: string;
    dateFormControl = new FormControl(moment());
    selectedMonth: Date;

    constructor(
        private categoryService: CategoryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
        // get the current time
        const current: Moment = moment();
        current.date(1);
        // set the selected date to this month
        this.dateFormControl.setValue(current);
        this.selectedMonth = new Date(this.dateFormControl.value.year(), this.dateFormControl.value.month(), 1);
    }

    chosenYearHandler(normalizedYear: Moment) {
        const ctrlValue = this.dateFormControl.value;
        ctrlValue.year(normalizedYear.year());
        this.dateFormControl.setValue(ctrlValue);
    }

    chosenMonthHandler(normlizedMonth: Moment, datepicker: MatDatepicker<Moment>) {
        const ctrlValue = this.dateFormControl.value;
        ctrlValue.month(normlizedMonth.month());
        ctrlValue.date(1);
        this.dateFormControl.setValue(ctrlValue);
        this.selectedMonth = new Date(ctrlValue.year(), ctrlValue.month(), 1);
        datepicker.close();
    }

    handleFilter() {
        // this.eventManager.broadcast({ name: 'myaBudgetItemListModification', content: 'OK' });
    }

    loadAll() {
        let categoryId;
        if (this.filterSelectedCategory) {
            categoryId = this.filterSelectedCategory.id;
        }
    }

    ngOnInit() {
        this.categoryService.query().subscribe(
            (res: HttpResponse<ICategory[]>) => {
                this.filterCategories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
    }

    trackId(index: number, item: IBudgetItem) {
        return item.id;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
