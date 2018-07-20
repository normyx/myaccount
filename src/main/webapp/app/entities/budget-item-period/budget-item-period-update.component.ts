import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBudgetItemPeriod } from 'app/shared/model/budget-item-period.model';
import { BudgetItemPeriodService } from './budget-item-period.service';
import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { BudgetItemService } from 'app/entities/budget-item';
import { IOperation } from 'app/shared/model/operation.model';
import { OperationService } from 'app/entities/operation';

@Component({
    selector: 'jhi-budget-item-period-update',
    templateUrl: './budget-item-period-update.component.html'
})
export class BudgetItemPeriodUpdateComponent implements OnInit {
    private _budgetItemPeriod: IBudgetItemPeriod;
    isSaving: boolean;

    budgetitems: IBudgetItem[];

    operations: IOperation[];
    dateDp: any;
    monthDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private budgetItemPeriodService: BudgetItemPeriodService,
        private budgetItemService: BudgetItemService,
        private operationService: OperationService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ budgetItemPeriod }) => {
            this.budgetItemPeriod = budgetItemPeriod;
        });
        this.budgetItemService.query().subscribe(
            (res: HttpResponse<IBudgetItem[]>) => {
                this.budgetitems = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.operationService.query({ filter: 'budgetitem-is-null' }).subscribe(
            (res: HttpResponse<IOperation[]>) => {
                if (!this.budgetItemPeriod.operationId) {
                    this.operations = res.body;
                } else {
                    this.operationService.find(this.budgetItemPeriod.operationId).subscribe(
                        (subRes: HttpResponse<IOperation>) => {
                            this.operations = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.budgetItemPeriod.id !== undefined) {
            this.subscribeToSaveResponse(this.budgetItemPeriodService.update(this.budgetItemPeriod));
        } else {
            this.subscribeToSaveResponse(this.budgetItemPeriodService.create(this.budgetItemPeriod));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBudgetItemPeriod>>) {
        result.subscribe((res: HttpResponse<IBudgetItemPeriod>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBudgetItemById(index: number, item: IBudgetItem) {
        return item.id;
    }

    trackOperationById(index: number, item: IOperation) {
        return item.id;
    }
    get budgetItemPeriod() {
        return this._budgetItemPeriod;
    }

    set budgetItemPeriod(budgetItemPeriod: IBudgetItemPeriod) {
        this._budgetItemPeriod = budgetItemPeriod;
    }
}
