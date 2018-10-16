import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MyaccountMyaBudgetItemModule } from './mya-budget-item/mya-budget-item.module';
import { MyaccountMyaBudgetItemPeriodModule } from './mya-budget-item-period/mya-budget-item-period.module';
import { MyaCategoryIconModule } from './mya-category-icon/mya-category-icon.module';

@NgModule({
    // prettier-ignore
    imports: [
        MyaCategoryIconModule,
        MyaccountMyaBudgetItemModule,
        MyaccountMyaBudgetItemPeriodModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountMyaModule {}
