import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BudgetItem } from 'app/shared/model/budget-item.model';
import { MyaBudgetItemService } from './mya-budget-item.service';
import { MyaBudgetItemComponent } from './mya-budget-item.component';
import { MyaBudgetItemDetailComponent } from './mya-budget-item-detail.component';
import { MyaBudgetItemUpdateComponent } from './mya-budget-item-update.component';
import { MyaBudgetItemCreateComponent } from './mya-budget-item-create.component';
import { MyaBudgetItemDeleteDialogComponent } from './mya-budget-item-delete-dialog.component';
import { MyaBudgetItemPopupComponent } from './mya-budget-item-popup.component';
import { IBudgetItem } from 'app/shared/model/budget-item.model';

@Injectable({ providedIn: 'root' })
export class MyaBudgetItemResolve implements Resolve<IBudgetItem> {
    constructor(private service: MyaBudgetItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((budgetItem: HttpResponse<BudgetItem>) => budgetItem.body));
        }
        return of(new BudgetItem());
    }
}

export const myaBudgetItemRoute: Routes = [
    {
        path: 'mya-budget-item',
        component: MyaBudgetItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mya-budget-item/:id/view',
        component: MyaBudgetItemDetailComponent,
        resolve: {
            budgetItem: MyaBudgetItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mya-budget-item/:id/edit',
        component: MyaBudgetItemUpdateComponent,
        resolve: {
            budgetItem: MyaBudgetItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mya-budget-item/new',
        component: MyaBudgetItemUpdateComponent,
        resolve: {
            budgetItem: MyaBudgetItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const myaBudgetItemPopupRoute: Routes = [
    {
        path: 'mya-budget-item/:id/delete',
        component: MyaBudgetItemPopupComponent,
        resolve: {
            budgetItem: MyaBudgetItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetItems',
            componentClass: MyaBudgetItemDeleteDialogComponent
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mya-budget-item/newwithdependencies',
        component: MyaBudgetItemPopupComponent,
        resolve: {
            budgetItem: MyaBudgetItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetItems',
            componentClass: MyaBudgetItemCreateComponent
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
