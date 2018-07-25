import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MyaccountSharedModule } from 'app/shared';
import { AccountCategoryMonthReportComponent } from './account-category-month-report.component';
import { ChartModule } from 'primeng/chart';

@NgModule({
    imports: [MyaccountSharedModule, ChartModule],
    declarations: [
        AccountCategoryMonthReportComponent
    ],
    entryComponents: [AccountCategoryMonthReportComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountAccountCategoryMonthReportModule {}
