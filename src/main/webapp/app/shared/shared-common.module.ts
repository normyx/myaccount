import { NgModule } from '@angular/core';

import { MyaccountSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [MyaccountSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [MyaccountSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class MyaccountSharedCommonModule {}
