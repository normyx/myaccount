import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InputSwitchModule } from 'primeng/inputswitch';
import { InputTextModule } from 'primeng/inputtext';

import { MyaccountSharedModule } from 'app/shared';
import {
    BudgetItemPeriodComponent,
    BudgetItemPeriodDetailComponent,
    BudgetItemPeriodUpdateComponent,
    BudgetItemPeriodDeletePopupComponent,
    BudgetItemPeriodDeleteDialogComponent,
    BudgetItemPeriodUpdatePopupComponent,
    BudgetItemPeriodUpdateDialogComponent,
    BudgetItemDeleteBudgetItemPeriodPopupComponent,
    BudgetItemDeleteBudgetItemPeriodDialogComponent,
    budgetItemPeriodRoute,
    budgetItemPeriodPopupRoute
} from './';

const ENTITY_STATES = [...budgetItemPeriodRoute, ...budgetItemPeriodPopupRoute];

@NgModule({
    imports: [MyaccountSharedModule, InputSwitchModule, InputTextModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BudgetItemPeriodComponent,
        BudgetItemPeriodDetailComponent,
        BudgetItemPeriodUpdateComponent,
        BudgetItemPeriodDeleteDialogComponent,
        BudgetItemPeriodDeletePopupComponent,
        BudgetItemPeriodUpdatePopupComponent,
        BudgetItemPeriodUpdateDialogComponent,
        BudgetItemDeleteBudgetItemPeriodPopupComponent,
        BudgetItemDeleteBudgetItemPeriodDialogComponent
    ],
    entryComponents: [
        BudgetItemPeriodComponent,
        BudgetItemPeriodUpdateComponent,
        BudgetItemPeriodDeleteDialogComponent,
        BudgetItemPeriodDeletePopupComponent,
        BudgetItemPeriodUpdatePopupComponent,
        BudgetItemPeriodUpdateDialogComponent,
        BudgetItemDeleteBudgetItemPeriodPopupComponent,
        BudgetItemDeleteBudgetItemPeriodDialogComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountBudgetItemPeriodModule {}
