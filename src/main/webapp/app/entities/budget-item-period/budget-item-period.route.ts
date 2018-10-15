import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BudgetItemPeriod } from 'app/shared/model/budget-item-period.model';
import { BudgetItemPeriodService } from './budget-item-period.service';
import { BudgetItemPeriodComponent } from './budget-item-period.component';
import { BudgetItemPeriodDetailComponent } from './budget-item-period-detail.component';
import { BudgetItemPeriodUpdateComponent } from './budget-item-period-update.component';
import { BudgetItemPeriodDeletePopupComponent } from './budget-item-period-delete-dialog.component';
import { IBudgetItemPeriod } from 'app/shared/model/budget-item-period.model';

@Injectable({ providedIn: 'root' })
export class BudgetItemPeriodResolve implements Resolve<IBudgetItemPeriod> {
    constructor(private service: BudgetItemPeriodService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((budgetItemPeriod: HttpResponse<BudgetItemPeriod>) => budgetItemPeriod.body));
        }
        return of(new BudgetItemPeriod());
    }
}

export const budgetItemPeriodRoute: Routes = [
    {
        path: 'budget-item-period',
        component: BudgetItemPeriodComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetItemPeriods'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'budget-item-period/:id/view',
        component: BudgetItemPeriodDetailComponent,
        resolve: {
            budgetItemPeriod: BudgetItemPeriodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetItemPeriods'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'budget-item-period/new',
        component: BudgetItemPeriodUpdateComponent,
        resolve: {
            budgetItemPeriod: BudgetItemPeriodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetItemPeriods'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'budget-item-period/:id/edit',
        component: BudgetItemPeriodUpdateComponent,
        resolve: {
            budgetItemPeriod: BudgetItemPeriodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetItemPeriods'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const budgetItemPeriodPopupRoute: Routes = [
    {
        path: 'budget-item-period/:id/delete',
        component: BudgetItemPeriodDeletePopupComponent,
        resolve: {
            budgetItemPeriod: BudgetItemPeriodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetItemPeriods'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
