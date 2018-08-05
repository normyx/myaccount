
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MyaccountButtonDemoModule } from './buttons/button/buttondemo.module';
import { MyaccountSplitbuttonDemoModule } from './buttons/splitbutton/splitbuttondemo.module';

import { MyaccountDialogDemoModule } from './overlay/dialog/dialogdemo.module';
import { MyaccountConfirmDialogDemoModule } from './overlay/confirmdialog/confirmdialogdemo.module';
import { MyaccountLightboxDemoModule } from './overlay/lightbox/lightboxdemo.module';
import { MyaccountTooltipDemoModule } from './overlay/tooltip/tooltipdemo.module';
import { MyaccountOverlayPanelDemoModule } from './overlay/overlaypanel/overlaypaneldemo.module';
import { MyaccountSideBarDemoModule } from './overlay/sidebar/sidebardemo.module';

import { MyaccountKeyFilterDemoModule } from './inputs/keyfilter/keyfilterdemo.module';
import { MyaccountInputTextDemoModule } from './inputs/inputtext/inputtextdemo.module';
import { MyaccountInputTextAreaDemoModule } from './inputs/inputtextarea/inputtextareademo.module';
import { MyaccountInputGroupDemoModule } from './inputs/inputgroup/inputgroupdemo.module';
import { MyaccountCalendarDemoModule } from './inputs/calendar/calendardemo.module';
import { MyaccountCheckboxDemoModule } from './inputs/checkbox/checkboxdemo.module';
import { MyaccountChipsDemoModule } from './inputs/chips/chipsdemo.module';
import { MyaccountColorPickerDemoModule } from './inputs/colorpicker/colorpickerdemo.module';
import { MyaccountInputMaskDemoModule } from './inputs/inputmask/inputmaskdemo.module';
import { MyaccountInputSwitchDemoModule } from './inputs/inputswitch/inputswitchdemo.module';
import { MyaccountPasswordIndicatorDemoModule } from './inputs/passwordindicator/passwordindicatordemo.module';
import { MyaccountAutoCompleteDemoModule } from './inputs/autocomplete/autocompletedemo.module';
import { MyaccountSliderDemoModule } from './inputs/slider/sliderdemo.module';
import { MyaccountSpinnerDemoModule } from './inputs/spinner/spinnerdemo.module';
import { MyaccountRatingDemoModule } from './inputs/rating/ratingdemo.module';
import { MyaccountSelectDemoModule } from './inputs/select/selectdemo.module';
import { MyaccountSelectButtonDemoModule } from './inputs/selectbutton/selectbuttondemo.module';
import { MyaccountListboxDemoModule } from './inputs/listbox/listboxdemo.module';
import { MyaccountRadioButtonDemoModule } from './inputs/radiobutton/radiobuttondemo.module';
import { MyaccountToggleButtonDemoModule } from './inputs/togglebutton/togglebuttondemo.module';
import { MyaccountEditorDemoModule } from './inputs/editor/editordemo.module';

import { MyaccountGrowlDemoModule } from './messages/growl/growldemo.module';
import { MyaccountMessagesDemoModule } from './messages/messages/messagesdemo.module';
import { MyaccountGalleriaDemoModule } from './multimedia/galleria/galleriademo.module';

import { MyaccountFileUploadDemoModule } from './fileupload/fileupload/fileuploaddemo.module';

import { MyaccountAccordionDemoModule } from './panel/accordion/accordiondemo.module';
import { MyaccountPanelDemoModule } from './panel/panel/paneldemo.module';
import { MyaccountTabViewDemoModule } from './panel/tabview/tabviewdemo.module';
import { MyaccountFieldsetDemoModule } from './panel/fieldset/fieldsetdemo.module';
import { MyaccountToolbarDemoModule } from './panel/toolbar/toolbardemo.module';
import { MyaccountGridDemoModule } from './panel/grid/griddemo.module';
import { MyaccountScrollPanelDemoModule } from './panel/scrollpanel/scrollpaneldemo.module';
import { MyaccountCardDemoModule } from './panel/card/carddemo.module';

import { MyaccountDataTableDemoModule } from './data/datatable/datatabledemo.module';
import { MyaccountTableDemoModule } from './data/table/tabledemo.module';
import { MyaccountDataGridDemoModule } from './data/datagrid/datagriddemo.module';
import { MyaccountDataListDemoModule } from './data/datalist/datalistdemo.module';
import { MyaccountDataScrollerDemoModule } from './data/datascroller/datascrollerdemo.module';
import { MyaccountPickListDemoModule } from './data/picklist/picklistdemo.module';
import { MyaccountOrderListDemoModule } from './data/orderlist/orderlistdemo.module';
import { MyaccountScheduleDemoModule } from './data/schedule/scheduledemo.module';
import { MyaccountTreeDemoModule } from './data/tree/treedemo.module';
import { MyaccountTreeTableDemoModule } from './data/treetable/treetabledemo.module';
import { MyaccountPaginatorDemoModule } from './data/paginator/paginatordemo.module';
import { MyaccountGmapDemoModule } from './data/gmap/gmapdemo.module';
import { MyaccountOrgChartDemoModule } from './data/orgchart/orgchartdemo.module';
import { MyaccountCarouselDemoModule } from './data/carousel/carouseldemo.module';
import { MyaccountDataViewDemoModule } from './data/dataview/dataviewdemo.module';

import { MyaccountBarchartDemoModule } from './charts/barchart/barchartdemo.module';
import { MyaccountDoughnutchartDemoModule } from './charts/doughnutchart/doughnutchartdemo.module';
import { MyaccountLinechartDemoModule } from './charts/linechart/linechartdemo.module';
import { MyaccountPiechartDemoModule } from './charts/piechart/piechartdemo.module';
import { MyaccountPolarareachartDemoModule } from './charts/polarareachart/polarareachartdemo.module';
import { MyaccountRadarchartDemoModule } from './charts/radarchart/radarchartdemo.module';

import { MyaccountDragDropDemoModule } from './dragdrop/dragdrop/dragdropdemo.module';

import { MyaccountMenuDemoModule } from './menu/menu/menudemo.module';
import { MyaccountContextMenuDemoModule } from './menu/contextmenu/contextmenudemo.module';
import { MyaccountPanelMenuDemoModule } from './menu/panelmenu/panelmenudemo.module';
import { MyaccountStepsDemoModule } from './menu/steps/stepsdemo.module';
import { MyaccountTieredMenuDemoModule } from './menu/tieredmenu/tieredmenudemo.module';
import { MyaccountBreadcrumbDemoModule } from './menu/breadcrumb/breadcrumbdemo.module';
import { MyaccountMegaMenuDemoModule } from './menu/megamenu/megamenudemo.module';
import { MyaccountMenuBarDemoModule } from './menu/menubar/menubardemo.module';
import { MyaccountSlideMenuDemoModule } from './menu/slidemenu/slidemenudemo.module';
import { MyaccountTabMenuDemoModule } from './menu/tabmenu/tabmenudemo.module';

import { MyaccountBlockUIDemoModule } from './misc/blockui/blockuidemo.module';
import { MyaccountCaptchaDemoModule } from './misc/captcha/captchademo.module';
import { MyaccountDeferDemoModule } from './misc/defer/deferdemo.module';
import { MyaccountInplaceDemoModule } from './misc/inplace/inplacedemo.module';
import { MyaccountProgressBarDemoModule } from './misc/progressbar/progressbardemo.module';
import { MyaccountRTLDemoModule } from './misc/rtl/rtldemo.module';
import { MyaccountTerminalDemoModule } from './misc/terminal/terminaldemo.module';
import { MyaccountValidationDemoModule } from './misc/validation/validationdemo.module';
import { MyaccountProgressSpinnerDemoModule } from './misc/progressspinner/progressspinnerdemo.module';

@NgModule({
    imports: [

        MyaccountMenuDemoModule,
        MyaccountContextMenuDemoModule,
        MyaccountPanelMenuDemoModule,
        MyaccountStepsDemoModule,
        MyaccountTieredMenuDemoModule,
        MyaccountBreadcrumbDemoModule,
        MyaccountMegaMenuDemoModule,
        MyaccountMenuBarDemoModule,
        MyaccountSlideMenuDemoModule,
        MyaccountTabMenuDemoModule,

        MyaccountBlockUIDemoModule,
        MyaccountCaptchaDemoModule,
        MyaccountDeferDemoModule,
        MyaccountInplaceDemoModule,
        MyaccountProgressBarDemoModule,
        MyaccountInputMaskDemoModule,
        MyaccountRTLDemoModule,
        MyaccountTerminalDemoModule,
        MyaccountValidationDemoModule,

        MyaccountButtonDemoModule,
        MyaccountSplitbuttonDemoModule,

        MyaccountInputTextDemoModule,
        MyaccountInputTextAreaDemoModule,
        MyaccountInputGroupDemoModule,
        MyaccountCalendarDemoModule,
        MyaccountChipsDemoModule,
        MyaccountInputMaskDemoModule,
        MyaccountInputSwitchDemoModule,
        MyaccountPasswordIndicatorDemoModule,
        MyaccountAutoCompleteDemoModule,
        MyaccountSliderDemoModule,
        MyaccountSpinnerDemoModule,
        MyaccountRatingDemoModule,
        MyaccountSelectDemoModule,
        MyaccountSelectButtonDemoModule,
        MyaccountListboxDemoModule,
        MyaccountRadioButtonDemoModule,
        MyaccountToggleButtonDemoModule,
        MyaccountEditorDemoModule,
        MyaccountColorPickerDemoModule,
        MyaccountCheckboxDemoModule,
        MyaccountKeyFilterDemoModule,

        MyaccountGrowlDemoModule,
        MyaccountMessagesDemoModule,
        MyaccountGalleriaDemoModule,

        MyaccountFileUploadDemoModule,

        MyaccountAccordionDemoModule,
        MyaccountPanelDemoModule,
        MyaccountTabViewDemoModule,
        MyaccountFieldsetDemoModule,
        MyaccountToolbarDemoModule,
        MyaccountGridDemoModule,
        MyaccountScrollPanelDemoModule,
        MyaccountCardDemoModule,

        MyaccountBarchartDemoModule,
        MyaccountDoughnutchartDemoModule,
        MyaccountLinechartDemoModule,
        MyaccountPiechartDemoModule,
        MyaccountPolarareachartDemoModule,
        MyaccountRadarchartDemoModule,

        MyaccountDragDropDemoModule,

        MyaccountDialogDemoModule,
        MyaccountConfirmDialogDemoModule,
        MyaccountLightboxDemoModule,
        MyaccountTooltipDemoModule,
        MyaccountOverlayPanelDemoModule,
        MyaccountSideBarDemoModule,

        MyaccountDataTableDemoModule,
        MyaccountTableDemoModule,
        MyaccountDataGridDemoModule,
        MyaccountDataListDemoModule,
        MyaccountDataViewDemoModule,
        MyaccountDataScrollerDemoModule,
        MyaccountScheduleDemoModule,
        MyaccountOrderListDemoModule,
        MyaccountPickListDemoModule,
        MyaccountTreeDemoModule,
        MyaccountTreeTableDemoModule,
        MyaccountPaginatorDemoModule,
        MyaccountOrgChartDemoModule,
        MyaccountGmapDemoModule,
        MyaccountCarouselDemoModule,
        MyaccountProgressSpinnerDemoModule

    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyaccountprimengModule {}
