import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { BudgetItemService } from './budget-item.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';
import { Moment } from 'moment';
import { SelectItem } from 'primeng/api';

@Component({
    selector: 'jhi-budget-item-create',
    templateUrl: './budget-item-create.component.html'
})
export class BudgetItemCreateComponent implements OnInit {
    private _budgetItem: IBudgetItem;
    isSaving: boolean;
    month: Date;
    smoothed: boolean;
    amount: number;
    dayOfMonth: number;
    categories: ICategory[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private budgetItemService: BudgetItemService,
        private categoryService: CategoryService,
        private activatedRoute: ActivatedRoute
    ) {
        // get the current time
        const current: Date = new Date(Date.now());
        // set the selected date to this month
        this.month = new Date(current.getFullYear(), current.getMonth(), 1);
        this.smoothed = false;
        this.dayOfMonth = 1;
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ budgetItem }) => {
            this.budgetItem = budgetItem;
        });
        this.categoryService.query().subscribe(
            (res: HttpResponse<ICategory[]>) => {
                this.categories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.subscribeToSaveResponse(
            this.budgetItemService.createWithBudgetItemPeriods(this.budgetItem, this.smoothed, this.month, this.amount, this.dayOfMonth)
        );
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBudgetItem>>) {
        result.subscribe((res: HttpResponse<IBudgetItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCategoryById(index: number, item: ICategory) {
        return item.id;
    }

    get budgetItem() {
        return this._budgetItem;
    }

    set budgetItem(budgetItem: IBudgetItem) {
        this._budgetItem = budgetItem;
    }
}
