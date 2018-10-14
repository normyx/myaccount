import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MyaccountSharedModule } from 'app/shared';
import { MyaccountAdminModule } from 'app/admin/admin.module';

import { CategoryIconComponent } from './';

@NgModule({
    imports: [MyaccountSharedModule, MyaccountAdminModule],
    declarations: [CategoryIconComponent],
    entryComponents: [CategoryIconComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountCategoryIconModule {}
