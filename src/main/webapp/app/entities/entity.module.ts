import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MyaccountCategoryModule } from './category/category.module';
import { MyaccountSubCategoryModule } from './sub-category/sub-category.module';
import { MyaccountBudgetItemModule } from './budget-item/budget-item.module';
import { MyaccountOperationModule } from './operation/operation.module';
import { MyaccountBudgetItemPeriodModule } from './budget-item-period/budget-item-period.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */
// import { MyaccountAccountCategoryMonthReportModule } from './account-category-month-report/account-category-month-report.module';
@NgModule({
    // prettier-ignore
    imports: [
        MyaccountCategoryModule,
        MyaccountSubCategoryModule,
        MyaccountBudgetItemModule,
        MyaccountOperationModule,
        MyaccountBudgetItemPeriodModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
   ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountEntityModule {}
