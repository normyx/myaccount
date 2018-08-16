import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyaccountSharedModule } from '../shared';
import { ChartModule } from 'primeng/primeng';
import { AmountGlobalPerDayInMonthReportComponent } from '../dashboard-ui-component/amount-global-per-day-in-month-report.component';
import { AmountCategoryPerMonthReportComponent } from '../dashboard-ui-component/amount-category-per-month-report.component';

import { AccountDashboardComponent, accountDashboardRoute } from './';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonModule } from 'primeng/primeng';
const DASHBOARD_STATES = [accountDashboardRoute];

@NgModule({
    imports: [
        MyaccountSharedModule,
        ChartModule,
        BrowserAnimationsModule,
        ButtonModule,
        RouterModule.forRoot(DASHBOARD_STATES, { useHash: true })
    ],
    declarations: [AccountDashboardComponent, AmountGlobalPerDayInMonthReportComponent, AmountCategoryPerMonthReportComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountAccountDashboardModule {}
