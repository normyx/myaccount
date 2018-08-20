import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyaccountSharedModule } from 'app/shared';
import { MyaccountAdminModule } from 'app/admin/admin.module';
import {
    ReportDataByDateComponent,
    ReportDataByDateDetailComponent,
    ReportDataByDateUpdateComponent,
    ReportDataByDateDeletePopupComponent,
    ReportDataByDateDeleteDialogComponent,
    reportDataByDateRoute,
    reportDataByDatePopupRoute
} from './';

const ENTITY_STATES = [...reportDataByDateRoute, ...reportDataByDatePopupRoute];

@NgModule({
    imports: [MyaccountSharedModule, MyaccountAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReportDataByDateComponent,
        ReportDataByDateDetailComponent,
        ReportDataByDateUpdateComponent,
        ReportDataByDateDeleteDialogComponent,
        ReportDataByDateDeletePopupComponent
    ],
    entryComponents: [
        ReportDataByDateComponent,
        ReportDataByDateUpdateComponent,
        ReportDataByDateDeleteDialogComponent,
        ReportDataByDateDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountReportDataByDateModule {}
