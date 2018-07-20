import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyaccountSharedModule } from 'app/shared';
import { MyaccountAdminModule } from 'app/admin/admin.module';
import {
    MonthlyReportComponent,
    MonthlyReportDetailComponent,
    MonthlyReportUpdateComponent,
    MonthlyReportDeletePopupComponent,
    MonthlyReportDeleteDialogComponent,
    monthlyReportRoute,
    monthlyReportPopupRoute
} from './';

const ENTITY_STATES = [...monthlyReportRoute, ...monthlyReportPopupRoute];

@NgModule({
    imports: [MyaccountSharedModule, MyaccountAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MonthlyReportComponent,
        MonthlyReportDetailComponent,
        MonthlyReportUpdateComponent,
        MonthlyReportDeleteDialogComponent,
        MonthlyReportDeletePopupComponent
    ],
    entryComponents: [
        MonthlyReportComponent,
        MonthlyReportUpdateComponent,
        MonthlyReportDeleteDialogComponent,
        MonthlyReportDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountMonthlyReportModule {}
