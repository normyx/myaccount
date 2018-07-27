import { Route } from '@angular/router';
import { UserRouteAccessService } from '../core';
import { AccountDashboardComponent } from './account-dashboard.component';

export const accountDashboardRoute: Route = {
    path: 'accountdashboard',
    component: AccountDashboardComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboard.linechart.home.title'
    },
    canActivate: [UserRouteAccessService]
};
