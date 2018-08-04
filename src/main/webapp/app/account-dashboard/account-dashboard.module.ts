import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyaccountSharedModule } from '../shared';
import { ChartModule } from 'primeng/primeng';
import { AccountCategoryMonthReportComponent } from '../account-category-month-report/account-category-month-report.component';
import { AmountEvolutionInMonthComponent } from '../dashboard-ui-component/amount-evolution-in-month.component';

import {
    AccountDashboardComponent,
    accountDashboardRoute
} from './';

const DASHBOARD_STATES = [
    accountDashboardRoute
];

@NgModule({
    imports: [
        MyaccountSharedModule,
        ChartModule,
        RouterModule.forRoot(DASHBOARD_STATES, { useHash: true })
    ],
    declarations: [
        AccountDashboardComponent,
        AccountCategoryMonthReportComponent,
        AmountEvolutionInMonthComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountAccountDashboardModule {}
