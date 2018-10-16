import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyaccountSharedModule } from 'app/shared';
import { ChartModule } from 'primeng/primeng';
import { MyaAmountGlobalPerDayInMonthReportComponent } from 'app/mya/mya-dashboard-ui-component/mya-amount-global-per-day-in-month-report.component';
import { MyaAmountCategoryPerMonthReportComponent } from 'app/mya/mya-dashboard-ui-component/mya-amount-category-per-month-report.component';

import { MyaAccountDashboardComponent, myaAccountDashboardRoute } from './';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonModule, CalendarModule, SliderModule, AccordionModule } from 'primeng/primeng';
const DASHBOARD_STATES = [myaAccountDashboardRoute];

@NgModule({
    imports: [
        MyaccountSharedModule,
        ChartModule,
        BrowserAnimationsModule,
        ButtonModule,
        CalendarModule,
        SliderModule,
        AccordionModule,
        RouterModule.forRoot(DASHBOARD_STATES, { useHash: true })
    ],
    declarations: [MyaAccountDashboardComponent, MyaAmountGlobalPerDayInMonthReportComponent, MyaAmountCategoryPerMonthReportComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountMyaAccountDashboardModule {}
