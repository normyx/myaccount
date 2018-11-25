import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InputSwitchModule } from 'primeng/inputswitch';
import { InputTextModule } from 'primeng/inputtext';

import { MyaccountSharedModule } from 'app/shared';
import {
    MyaBudgetItemPeriodUpdateDialogComponent,
    MyaBudgetItemPeriodCreateDialogComponent,
    MyaBudgetItemPeriodDeleteWithNextDialogComponent,
    MyaBudgetItemPopupComponent,
    MyaBudgetItemPeriodCellComponent,
    myaBudgetItemPeriodRoute,
    myaBudgetItemPeriodPopupRoute
} from './';

const ENTITY_STATES = [...myaBudgetItemPeriodRoute, ...myaBudgetItemPeriodPopupRoute];

@NgModule({
    imports: [MyaccountSharedModule, InputSwitchModule, InputTextModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MyaBudgetItemPeriodUpdateDialogComponent,
        MyaBudgetItemPeriodCreateDialogComponent,
        MyaBudgetItemPeriodDeleteWithNextDialogComponent,
        MyaBudgetItemPopupComponent,
        MyaBudgetItemPeriodCellComponent
    ],
    entryComponents: [
        MyaBudgetItemPeriodUpdateDialogComponent,
        MyaBudgetItemPeriodCreateDialogComponent,
        MyaBudgetItemPeriodDeleteWithNextDialogComponent,
        MyaBudgetItemPopupComponent,
        MyaBudgetItemPeriodCellComponent
    ],
    exports: [MyaBudgetItemPeriodCellComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountMyaBudgetItemPeriodModule {}
