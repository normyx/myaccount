import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BudgetItemPeriod } from 'app/shared/model/budget-item-period.model';
import { MyaBudgetItemPeriodService } from './mya-budget-item-period.service';
import { IBudgetItemPeriod } from 'app/shared/model/budget-item-period.model';
import { MyaBudgetItemPeriodUpdateDialogComponent } from './mya-budget-item-period-update-dialog.component';
import { MyaBudgetItemPopupComponent } from './mya-budget-item-period-popup.component';
import { MyaBudgetItemPeriodDeleteWithNextDialogComponent } from './mya-budget-item-period-delete-with-next-dialog.component';

@Injectable({ providedIn: 'root' })
export class MyaBudgetItemPeriodResolve implements Resolve<IBudgetItemPeriod> {
    constructor(private service: MyaBudgetItemPeriodService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((budgetItemPeriod: HttpResponse<BudgetItemPeriod>) => budgetItemPeriod.body));
        }
        return of(new BudgetItemPeriod());
    }
}

export const myaBudgetItemPeriodRoute: Routes = [];

export const myaBudgetItemPeriodPopupRoute: Routes = [
    {
        path: 'mya-budget-item-period/:id/update',
        component: MyaBudgetItemPopupComponent,
        resolve: {
            budgetItemPeriod: MyaBudgetItemPeriodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetItemPeriods',
            componentClass: MyaBudgetItemPeriodUpdateDialogComponent
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mya-budget-item-period/:id/delete-with-next',
        component: MyaBudgetItemPopupComponent,
        resolve: {
            budgetItemPeriod: MyaBudgetItemPeriodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetItems',
            componentClass: MyaBudgetItemPeriodDeleteWithNextDialogComponent
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
