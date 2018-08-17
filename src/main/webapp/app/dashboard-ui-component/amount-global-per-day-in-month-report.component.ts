import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { DashboardUIComponentsService } from './dashboard-ui-components.service';
// import { IAccountCategoryMonthReport } from './account-category-month-report.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ChartModule } from 'primeng/chart';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import * as moment from 'moment';

@Component({
    selector: 'jhi-amount-global-per-day-in-month-report',
    templateUrl: './amount-global-per-day-in-month-report.component.html'
})
export class AmountGlobalPerDayInMonthReportComponent implements OnInit, OnChanges {
    @Input() month: Date;
    // accountCategoryMonthReport: IAccountCategoryMonthReport;
    data: any;
    options: any;

    constructor(private dashboardUIComponentsService: DashboardUIComponentsService, private jhiAlertService: JhiAlertService) {}

    loadAll() {
        if (this.month) {
            this.dashboardUIComponentsService.getAmountGlobalPerDayInMonth(moment(this.month)).subscribe(
                (res: HttpResponse<any>) => {
                    this.data = {
                        labels: res.body.dates,
                        datasets: [
                            {
                                label: 'Operation',
                                data: res.body.operationAmounts,
                                borderColor: '#0099ff',
                                backgroundColor: '#0099ff',
                                fill: false
                            },
                            {
                                label: 'Budget',
                                data: res.body.budgetAmounts,
                                borderColor: '#565656',
                                backgroundColor: '#565656',
                                borderWidth: 1,
                                fill: false
                            },
                            {
                                label: 'Evolution prévue',
                                data: res.body.predictiveBudgetAmounts,
                                borderColor: '#ff0000',
                                backgroundColor: '#ff0000',
                                fill: false
                            }
                        ]
                    };
                    this.options = {
                        title: {
                            display: true,
                            text: res.body.month,
                            fontSize: 16
                        },
                        legend: {
                            position: 'bottom'
                        },
                        tooltips: {
                            position: 'average',
                            mode: 'index',
                            intersect: false
                        }
                    };
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        }
    }

    ngOnInit() {}

    ngOnChanges() {
        this.loadAll();
    }
    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
