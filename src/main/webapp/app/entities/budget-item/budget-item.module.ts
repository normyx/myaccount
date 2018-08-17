import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyaccountSharedModule } from 'app/shared';
import { MyaccountAdminModule } from 'app/admin/admin.module';
import { CategoryIconComponent } from '../category-icon/category-icon.component';
import { BudgetItemPeriodUpdateDialogComponent } from '../budget-item-period/budget-item-period-update-dialog.component';
// import { AccountCategoryMonthReportComponent } from '../account-category-month-report/account-category-month-report.component';
import { ChartModule } from 'primeng/chart';
import { CalendarModule } from 'primeng/calendar';

// import { MyaccountAccountCategoryMonthReportModule } from '../account-category-month-report/account-category-month-report.module';
// import { ChartModule } from 'primeng/chart';

import {
    BudgetItemComponent,
    BudgetItemDetailComponent,
    BudgetItemUpdateComponent,
    BudgetItemDeletePopupComponent,
    BudgetItemDeleteDialogComponent,
    BudgetItemRowComponent,
    budgetItemRoute,
    budgetItemPopupRoute
} from './';

const ENTITY_STATES = [...budgetItemRoute, ...budgetItemPopupRoute];

@NgModule({
    imports: [MyaccountSharedModule, MyaccountAdminModule, ChartModule, CalendarModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BudgetItemComponent,
        BudgetItemDetailComponent,
        BudgetItemUpdateComponent,
        BudgetItemDeleteDialogComponent,
        BudgetItemDeletePopupComponent,
        BudgetItemRowComponent,
        CategoryIconComponent
        // AccountCategoryMonthReportComponent
    ],
    entryComponents: [
        BudgetItemComponent,
        BudgetItemUpdateComponent,
        BudgetItemDeleteDialogComponent,
        BudgetItemDeletePopupComponent,
        BudgetItemRowComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountBudgetItemModule {}
