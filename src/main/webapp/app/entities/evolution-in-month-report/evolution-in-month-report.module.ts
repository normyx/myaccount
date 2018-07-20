import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyaccountSharedModule } from 'app/shared';
import { MyaccountAdminModule } from 'app/admin/admin.module';
import {
    EvolutionInMonthReportComponent,
    EvolutionInMonthReportDetailComponent,
    EvolutionInMonthReportUpdateComponent,
    EvolutionInMonthReportDeletePopupComponent,
    EvolutionInMonthReportDeleteDialogComponent,
    evolutionInMonthReportRoute,
    evolutionInMonthReportPopupRoute
} from './';

const ENTITY_STATES = [...evolutionInMonthReportRoute, ...evolutionInMonthReportPopupRoute];

@NgModule({
    imports: [MyaccountSharedModule, MyaccountAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EvolutionInMonthReportComponent,
        EvolutionInMonthReportDetailComponent,
        EvolutionInMonthReportUpdateComponent,
        EvolutionInMonthReportDeleteDialogComponent,
        EvolutionInMonthReportDeletePopupComponent
    ],
    entryComponents: [
        EvolutionInMonthReportComponent,
        EvolutionInMonthReportUpdateComponent,
        EvolutionInMonthReportDeleteDialogComponent,
        EvolutionInMonthReportDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountEvolutionInMonthReportModule {}
