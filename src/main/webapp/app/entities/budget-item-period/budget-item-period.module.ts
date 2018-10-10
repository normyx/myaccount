import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InputSwitchModule } from 'primeng/inputswitch';
import { InputTextModule } from 'primeng/inputtext';

import { MyaccountSharedModule } from 'app/shared';
import {
    BudgetItemPeriodComponent,
    BudgetItemPeriodDetailComponent,
    BudgetItemPeriodUpdateComponent,
    BudgetItemPeriodDeleteDialogComponent,
    BudgetItemPeriodUpdateDialogComponent,
    BudgetItemPeriodDeleteWithNextDialogComponent,
    BudgetItemPopupComponent,
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
        BudgetItemPeriodUpdateDialogComponent,
        BudgetItemPeriodDeleteWithNextDialogComponent,
        BudgetItemPopupComponent
    ],
    entryComponents: [
        BudgetItemPeriodComponent,
        BudgetItemPeriodUpdateComponent,
        BudgetItemPeriodDeleteDialogComponent,
        BudgetItemPeriodUpdateDialogComponent,
        BudgetItemPeriodDeleteWithNextDialogComponent,
        BudgetItemPopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountBudgetItemPeriodModule {}
