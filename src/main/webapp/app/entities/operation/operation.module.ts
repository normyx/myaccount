import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyaccountSharedModule } from 'app/shared';
import { MyaccountAdminModule } from 'app/admin/admin.module';
import {
    OperationComponent,
    OperationDetailComponent,
    OperationUpdateComponent,
    OperationDeletePopupComponent,
    OperationDeleteDialogComponent,
    operationRoute,
    operationPopupRoute
} from './';

const ENTITY_STATES = [...operationRoute, ...operationPopupRoute];

@NgModule({
    imports: [MyaccountSharedModule, MyaccountAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OperationComponent,
        OperationDetailComponent,
        OperationUpdateComponent,
        OperationDeleteDialogComponent,
        OperationDeletePopupComponent
    ],
    entryComponents: [OperationComponent, OperationUpdateComponent, OperationDeleteDialogComponent, OperationDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountOperationModule {}
