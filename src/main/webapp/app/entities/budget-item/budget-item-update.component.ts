import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { BudgetItemService } from './budget-item.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-budget-item-update',
    templateUrl: './budget-item-update.component.html'
})
export class BudgetItemUpdateComponent implements OnInit {
    budgetItem: IBudgetItem;
    isSaving: boolean;

    categories: ICategory[];

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private budgetItemService: BudgetItemService,
        private categoryService: CategoryService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

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
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.budgetItem.id !== undefined) {
            this.subscribeToSaveResponse(this.budgetItemService.update(this.budgetItem));
        } else {
            this.subscribeToSaveResponse(this.budgetItemService.create(this.budgetItem));
        }
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
