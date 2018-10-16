import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { MyaAccountDashboardComponent } from './mya-account-dashboard.component';

export const myaAccountDashboardRoute: Route = {
    path: 'accountdashboard',
    component: MyaAccountDashboardComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboard.linechart.home.title'
    },
    canActivate: [UserRouteAccessService]
};
