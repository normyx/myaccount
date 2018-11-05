import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyaccountSharedModule } from 'app/shared';
import { MyaccountMyaDashboardUIComponentModule } from 'app/mya/mya-dashboard-ui-components/mya-dashboard-ui_components.module';

import { MyaAccountDashboardComponent, myaAccountDashboardRoute } from './';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonModule, CalendarModule, SliderModule, AccordionModule, CardModule } from 'primeng/primeng';
const DASHBOARD_STATES = [myaAccountDashboardRoute];

@NgModule({
    imports: [
        MyaccountSharedModule,
        BrowserAnimationsModule,
        ButtonModule,
        CalendarModule,
        SliderModule,
        AccordionModule,
        CardModule,
        MyaccountMyaDashboardUIComponentModule,
        RouterModule.forRoot(DASHBOARD_STATES, { useHash: true })
    ],
    declarations: [MyaAccountDashboardComponent],

    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountMyaAccountDashboardModule {}
