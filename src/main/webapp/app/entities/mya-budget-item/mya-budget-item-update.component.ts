import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { MyaBudgetItemService } from './mya-budget-item.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';

@Component({
    selector: 'jhi-mya-budget-item-update',
    templateUrl: './mya-budget-item-update.component.html'
})
export class MyaBudgetItemUpdateComponent implements OnInit {
    budgetItem: IBudgetItem;
    isSaving: boolean;

    categories: ICategory[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private budgetItemService: MyaBudgetItemService,
        private categoryService: CategoryService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.categoryService.query().subscribe(
            (res: HttpResponse<ICategory[]>) => {
                this.categories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.budgetItem.id !== undefined) {
            this.subscribeToSaveResponse(this.budgetItemService.update(this.budgetItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBudgetItem>>) {
        result.subscribe((res: HttpResponse<IBudgetItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.eventManager.broadcast({ name: 'myaBudgetItemRowModification' + this.budgetItem.id, content: 'OK' });
        this.isSaving = false;
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
