import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyaccountSharedModule } from 'app/shared';
import { MyaccountAdminModule } from 'app/admin/admin.module';

import { MyaCategoryIconComponent } from './mya-category-icon.component';

@NgModule({
    imports: [MyaccountSharedModule, MyaccountAdminModule, CommonModule],
    declarations: [MyaCategoryIconComponent],
    exports: [MyaCategoryIconComponent],
    entryComponents: [MyaCategoryIconComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaCategoryIconModule {}
