import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { IBudgetItem, BudgetItem } from 'app/shared/model/budget-item.model';
import { MyaBudgetItemService } from './mya-budget-item.service';
import { ICategory, Category } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';

@Component({
    selector: 'jhi-mya-budget-item-create',
    templateUrl: './mya-budget-item-create.component.html'
})
export class MyaBudgetItemCreateComponent implements OnInit {
    budgetItem: IBudgetItem;
    isSaving: boolean;
    month: Date;
    smoothed: boolean;
    amount: number;
    dayOfMonth: number;
    categories: Category[];
    selectedCategory: Category;

    constructor(
        private jhiAlertService: JhiAlertService,
        private budgetItemService: MyaBudgetItemService,
        private categoryService: CategoryService,
        private activatedRoute: ActivatedRoute,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
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
        if (!this.budgetItem) {
            this.budgetItem = new BudgetItem();
        }
        this.categoryService.query().subscribe(
            (res: HttpResponse<ICategory[]>) => {
                this.categories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.budgetItem.categoryId = this.selectedCategory.id;
        this.subscribeToSaveResponse(
            this.budgetItemService.createWithBudgetItemPeriods(this.budgetItem, this.smoothed, this.month, this.amount, this.dayOfMonth)
        );
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBudgetItem>>) {
        result.subscribe((res: HttpResponse<IBudgetItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.eventManager.broadcast({
            name: 'myaBudgetItemListModification',
            content: 'Deleted an budgetItem'
        });
        this.activeModal.dismiss(true);
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
}
