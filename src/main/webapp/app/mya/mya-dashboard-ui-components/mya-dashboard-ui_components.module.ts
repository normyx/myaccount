import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MyaccountSharedModule } from 'app/shared';
import { ChartModule } from 'primeng/primeng';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonModule, CalendarModule, SliderModule, AccordionModule } from 'primeng/primeng';

import { MyaAmountGlobalPerDayInMonthReportComponent, MyaAmountCategoryPerMonthReportComponent } from './';

@NgModule({
    imports: [MyaccountSharedModule, ChartModule, BrowserAnimationsModule, ButtonModule, CalendarModule, SliderModule, AccordionModule],
    declarations: [MyaAmountGlobalPerDayInMonthReportComponent, MyaAmountCategoryPerMonthReportComponent],
    exports: [MyaAmountGlobalPerDayInMonthReportComponent, MyaAmountCategoryPerMonthReportComponent],
    entryComponents: [MyaAmountGlobalPerDayInMonthReportComponent, MyaAmountCategoryPerMonthReportComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountMyaDashboardUIComponentModule {}
