import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { ICategory } from 'app/shared/model/category.model';
import { Principal } from 'app/core';
import { CategoryService } from 'app/entities/category/category.service';

@Component({
    selector: 'jhi-mya-budget-item',
    templateUrl: './mya-budget-item.component.html'
})
export class MyaBudgetItemComponent implements OnInit {
    currentAccount: any;
    selectedMonth: Date;
    filterCategories: ICategory[];
    filterSelectedCategory: ICategory;
    filterContains: string;

    constructor(
        private categoryService: CategoryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
        // get the current time
        const current: Date = new Date(Date.now());
        // set the selected date to this month
        this.selectedMonth = new Date(current.getFullYear(), current.getMonth(), 1);
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
