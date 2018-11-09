import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { MyaDashboardUIComponentsService } from './mya-dashboard-ui-components.service';
// import { IAccountCategoryMonthReport } from './account-category-month-report.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ChartModule } from 'primeng/chart';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import * as moment from 'moment';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';

@Component({
    selector: 'jhi-mya-category-report-summary',
    templateUrl: './mya-caterory-report-summary.component.html'
})
export class MyaCategoryReportSummaryComponent implements OnInit, OnChanges {
    @Input() categoryId: number;
    @Input() monthTo: Date;
    @Input() monthFrom: Date;
    // accountCategoryMonthReport: IAccountCategoryMonthReport;
    dataPerMonth: any;
    optionsPerMonth: any;
    category: ICategory;

    constructor(
        private dashboardUIComponentsService: MyaDashboardUIComponentsService,
        private categoryService: CategoryService,
        private jhiAlertService: JhiAlertService
    ) {}

    loadAll() {
        if (this.categoryId) {
            this.categoryService.find(this.categoryId).subscribe(
                (res: HttpResponse<ICategory>) => {
                    this.category = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
            this.dashboardUIComponentsService
                .getAmountCategoryPerMonth(this.categoryId, moment(this.monthFrom), moment(this.monthTo))
                .subscribe(
                    (res: HttpResponse<any>) => {
                        this.dataPerMonth = {
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
                        this.optionsPerMonth = {
                            title: {
                                display: true,
                                text: 'Evolutions',
                                fontSize: 12
                            },
                            legend: {
                                position: 'bottom'
                            },
                            scales: {
                                yAxes: [
                                    {
                                        scaleLabel: {
                                            display: true,
                                            labelString: 'Montants'
                                        },
                                        ticks: {
                                            suggestedMax: 0,
                                            callback: function(value, index, values) {
                                                return value + ' €';
                                            }
                                        }
                                    }
                                ],
                                xAxes: [
                                    {
                                        display: false
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
