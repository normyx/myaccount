import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { MyaDashboardUIComponentsService } from './mya-dashboard-ui-components.service';
// import { IAccountCategoryMonthReport } from './account-category-month-report.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ChartModule } from 'primeng/chart';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import * as moment from 'moment';

@Component({
    selector: 'jhi-mya-amount-category-per-month-report',
    templateUrl: './mya-amount-category-per-month-report.component.html'
})
export class MyaAmountCategoryPerMonthReportComponent implements OnInit, OnChanges {
    @Input() categoryId: number;
    @Input() monthTo: Date;
    @Input() monthFrom: Date;
    // accountCategoryMonthReport: IAccountCategoryMonthReport;
    data: any;
    options: any;

    constructor(private dashboardUIComponentsService: MyaDashboardUIComponentsService, private jhiAlertService: JhiAlertService) {}

    loadAll() {
        if (this.categoryId) {
            this.dashboardUIComponentsService
                .getAmountCategoryPerMonth(this.categoryId, moment(this.monthFrom), moment(this.monthTo))
                .subscribe(
                    (res: HttpResponse<any>) => {
                        this.data = {
                            labels: res.body.months,
                            datasets: [
                                {
                                    label: 'Montant',
                                    data: res.body.amounts,
                                    borderColor: '#0099ff',
                                    backgroundColor: '#0099ff',
                                    fill: false
                                },
                                {
                                    label: 'Budget',
                                    data: res.body.budgetAmounts,
                                    borderColor: '#565656',
                                    backgroundColor: '#565656',
                                    fill: false
                                },
                                {
                                    label: 'Montant Moy. 3 mois',
                                    data: res.body.amountsAvg3,
                                    borderColor: '#005b99',
                                    // backgroundColor: '#005b99',
                                    // borderColor: '##35bf4d',
                                    fill: false,
                                    borderDash: [5, 5],
                                    borderWidth: 1
                                },
                                {
                                    label: 'Montant Moy. 12 mois',
                                    data: res.body.amountsAvg12,
                                    borderColor: '#005b99',
                                    // backgroundColor: '#005b99',
                                    // borderColor: '##35bf4d',
                                    fill: false,
                                    borderDash: [10, 10],
                                    borderWidth: 1
                                }
                            ]
                        };
                        this.options = {
                            title: {
                                display: true,
                                text: res.body.categoryName,
                                fontSize: 16
                            },
                            legend: {
                                position: 'bottom'
                            },
                            scales: {
                                yAxes: [
                                    {
                                        ticks: {
                                            suggestedMax: 0
                                        }
                                    }
                                ]
                            },
                            tooltips: {
                                position: 'average',
                                mode: 'index',
                                intersect: false,
                                callbacks: {
                                    label: (tooltipItem, data) => {
                                        let label = data.datasets[tooltipItem.datasetIndex].label || '';
                                        if (label) {
                                            label += ' : ';
                                        }
                                        label += Math.round(tooltipItem.yLabel * 100) / 100;
                                        label += ' €';
                                        return label;
                                    }
                                }
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
