import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyaccountSharedModule } from 'app/shared';
import { MyaccountAdminModule } from 'app/admin/admin.module';
import {
    BudgetItemComponent,
    BudgetItemDetailComponent,
    BudgetItemUpdateComponent,
    BudgetItemDeletePopupComponent,
    BudgetItemDeleteDialogComponent,
    budgetItemRoute,
    budgetItemPopupRoute
} from './';

const ENTITY_STATES = [...budgetItemRoute, ...budgetItemPopupRoute];

@NgModule({
    imports: [MyaccountSharedModule, MyaccountAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BudgetItemComponent,
        BudgetItemDetailComponent,
        BudgetItemUpdateComponent,
        BudgetItemDeleteDialogComponent,
        BudgetItemDeletePopupComponent
    ],
    entryComponents: [BudgetItemComponent, BudgetItemUpdateComponent, BudgetItemDeleteDialogComponent, BudgetItemDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountBudgetItemModule {}
