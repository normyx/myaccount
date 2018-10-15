import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyaccountSharedModule } from 'app/shared';
import { MyaccountAdminModule } from 'app/admin/admin.module';
import { MyaCategoryIconModule } from '../../mya/mya-category-icon/mya-category-icon.module';
import { ChartModule } from 'primeng/chart';
import { CalendarModule } from 'primeng/calendar';
import { InputSwitchModule } from 'primeng/inputswitch';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';

import {
    MyaBudgetItemComponent,
    MyaBudgetItemDetailComponent,
    MyaBudgetItemUpdateComponent,
    MyaBudgetItemCreateComponent,
    MyaBudgetItemPopupComponent,
    MyaBudgetItemDeleteDialogComponent,
    MyaBudgetItemRowComponent,
    myaBudgetItemRoute,
    myaBudgetItemPopupRoute
} from './';

const ENTITY_STATES = [...myaBudgetItemRoute, ...myaBudgetItemPopupRoute];

@NgModule({
    imports: [
        MyaccountSharedModule,
        MyaccountAdminModule,
        ChartModule,
        CalendarModule,
        InputSwitchModule,
        InputTextModule,
        DropdownModule,
        MyaCategoryIconModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MyaBudgetItemComponent,
        MyaBudgetItemDetailComponent,
        MyaBudgetItemUpdateComponent,
        MyaBudgetItemCreateComponent,
        MyaBudgetItemPopupComponent,
        MyaBudgetItemDeleteDialogComponent,
        MyaBudgetItemRowComponent
        // AccountCategoryMonthReportComponent
    ],
    entryComponents: [
        MyaBudgetItemComponent,
        MyaBudgetItemUpdateComponent,
        MyaBudgetItemCreateComponent,
        MyaBudgetItemDeleteDialogComponent,
        MyaBudgetItemPopupComponent,
        MyaBudgetItemRowComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountMyaBudgetItemModule {}
