import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISubCategory } from 'app/shared/model/sub-category.model';
import { Principal } from 'app/core';
import { SubCategoryService } from './sub-category.service';

@Component({
    selector: 'jhi-sub-category',
    templateUrl: './sub-category.component.html'
})
export class SubCategoryComponent implements OnInit, OnDestroy {
    subCategories: ISubCategory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private subCategoryService: SubCategoryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.subCategoryService.query().subscribe(
            (res: HttpResponse<ISubCategory[]>) => {
                this.subCategories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSubCategories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISubCategory) {
        return item.id;
    }

    registerChangeInSubCategories() {
        this.eventSubscriber = this.eventManager.subscribe('subCategoryListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
