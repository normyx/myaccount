import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IOperation } from 'app/shared/model/operation.model';
import { OperationService } from './operation.service';
import { ISubCategory } from 'app/shared/model/sub-category.model';
import { SubCategoryService } from 'app/entities/sub-category';
import { IUser, UserService } from 'app/core';
import { IBudgetItemPeriod } from 'app/shared/model/budget-item-period.model';
import { BudgetItemPeriodService } from 'app/entities/budget-item-period';

@Component({
    selector: 'jhi-operation-update',
    templateUrl: './operation-update.component.html'
})
export class OperationUpdateComponent implements OnInit {
    private _operation: IOperation;
    isSaving: boolean;

    subcategories: ISubCategory[];

    users: IUser[];

    budgetitemperiods: IBudgetItemPeriod[];
    dateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private operationService: OperationService,
        private subCategoryService: SubCategoryService,
        private userService: UserService,
        private budgetItemPeriodService: BudgetItemPeriodService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ operation }) => {
            this.operation = operation;
        });
        this.subCategoryService.query().subscribe(
            (res: HttpResponse<ISubCategory[]>) => {
                this.subcategories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.budgetItemPeriodService.query().subscribe(
            (res: HttpResponse<IBudgetItemPeriod[]>) => {
                this.budgetitemperiods = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.operation.id !== undefined) {
            this.subscribeToSaveResponse(this.operationService.update(this.operation));
        } else {
            this.subscribeToSaveResponse(this.operationService.create(this.operation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOperation>>) {
        result.subscribe((res: HttpResponse<IOperation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSubCategoryById(index: number, item: ISubCategory) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackBudgetItemPeriodById(index: number, item: IBudgetItemPeriod) {
        return item.id;
    }
    get operation() {
        return this._operation;
    }

    set operation(operation: IOperation) {
        this._operation = operation;
    }
}
