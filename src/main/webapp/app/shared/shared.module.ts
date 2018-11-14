import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { MyaccountSharedLibsModule, MyaccountSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { JhiMaterialModule } from 'app/shared/jhi-material.module';

@NgModule({
    imports: [MyaccountSharedLibsModule, MyaccountSharedCommonModule, JhiMaterialModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [MyaccountSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, JhiMaterialModule],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountSharedModule {
    static forRoot() {
        return {
            ngModule: MyaccountSharedModule
        };
    }
}
