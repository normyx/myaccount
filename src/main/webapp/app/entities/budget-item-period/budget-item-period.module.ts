import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyaccountSharedModule } from 'app/shared';
import {
    BudgetItemPeriodComponent,
    BudgetItemPeriodDetailComponent,
    BudgetItemPeriodUpdateComponent,
    BudgetItemPeriodDeletePopupComponent,
    BudgetItemPeriodDeleteDialogComponent,
    budgetItemPeriodRoute,
    budgetItemPeriodPopupRoute
} from './';

const ENTITY_STATES = [...budgetItemPeriodRoute, ...budgetItemPeriodPopupRoute];

@NgModule({
    imports: [MyaccountSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BudgetItemPeriodComponent,
        BudgetItemPeriodDetailComponent,
        BudgetItemPeriodUpdateComponent,
        BudgetItemPeriodDeleteDialogComponent,
        BudgetItemPeriodDeletePopupComponent
    ],
    entryComponents: [
        BudgetItemPeriodComponent,
        BudgetItemPeriodUpdateComponent,
        BudgetItemPeriodDeleteDialogComponent,
        BudgetItemPeriodDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountBudgetItemPeriodModule {}
