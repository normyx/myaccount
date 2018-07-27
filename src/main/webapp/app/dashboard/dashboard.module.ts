import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MyaccountBarchartModule } from './barchart/barchart.module';
import { MyaccountDoughnutchartModule } from './doughnutchart/doughnutchart.module';
import { MyaccountLinechartModule } from './linechart/linechart.module';
import { MyaccountPiechartModule } from './piechart/piechart.module';
import { MyaccountPolarareachartModule } from './polarareachart/polarareachart.module';
import { MyaccountRadarchartModule } from './radarchart/radarchart.module';

@NgModule({
    imports: [
        MyaccountBarchartModule,
        MyaccountDoughnutchartModule,
        MyaccountLinechartModule,
        MyaccountPiechartModule,
        MyaccountPolarareachartModule,
        MyaccountRadarchartModule,
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountDashboardModule {}
