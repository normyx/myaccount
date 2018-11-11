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
    dataPerMonthWithMarked: any;
    optionsPerMonthWithMarked: any;
    dataCurrentBudget: any;
    optionsCurrentBudget: any;
    dataDeltaAtDate: any;
    optionsDeltaAtDate: any;
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
                                    borderColor: '#49ab81',
                                    backgroundColor: '#49ab81',
                                    fill: false,
                                    pointRadius: 0
                                },
                                {
                                    label: 'Budget',
                                    data: res.body.budgetAmounts,
                                    borderColor: '#3b5998',
                                    backgroundColor: '#3b5998',
                                    fill: false,
                                    pointRadius: 0
                                },
                                {
                                    label: 'Montant Moy. 3 mois',
                                    data: res.body.amountsAvg3,
                                    borderColor: '#8b9dc3',
                                    backgroundColor: '#8b9dc3',
                                    // borderColor: '##35bf4d',
                                    fill: false,
                                    // borderDash: [5, 5],
                                    borderWidth: 2,
                                    pointRadius: 0
                                },
                                {
                                    label: 'Montant Moy. 12 mois',
                                    data: res.body.amountsAvg12,
                                    borderColor: '#dfe3ee',
                                    backgroundColor: '#dfe3ee',
                                    // borderColor: '##35bf4d',
                                    fill: false,
                                    // borderDash: [10, 10],
                                    borderWidth: 2,
                                    pointRadius: 0
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
                                            callback(value, index, values) {
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
            this.dashboardUIComponentsService
                .getAmountCategoryPerMonthWithMarked(this.categoryId, moment(this.monthFrom), moment(this.monthTo))
                .subscribe(
                    (res: HttpResponse<any>) => {
                        this.dataPerMonthWithMarked = {
                            labels: res.body.months,
                            datasets: [
                                {
                                    label: 'Montant',
                                    data: res.body.operationAmounts,
                                    borderColor: '#49ab81',
                                    backgroundColor: '#49ab81',
                                    fill: false,
                                    pointRadius: 0
                                },
                                {
                                    label: 'Budget non lissé pointé',
                                    data: res.body.budgetUnSmoothedMarkedAmounts,
                                    borderColor: '#3b5998',
                                    backgroundColor: '#3b5998',
                                    fill: true,
                                    pointRadius: 0
                                },
                                {
                                    label: 'Montant non lissé non pointé',
                                    data: res.body.budgetUnSmoothedUnMarkedAmounts,
                                    borderColor: '#8b9dc3',
                                    backgroundColor: '#8b9dc3',
                                    fill: true,
                                    borderWidth: 2,
                                    pointRadius: 0
                                },
                                {
                                    label: 'Budget Lissé',
                                    data: res.body.budgetSmoothedAmounts,
                                    borderColor: '#dfe3ee',
                                    backgroundColor: '#dfe3ee',
                                    fill: true,
                                    borderWidth: 2,
                                    pointRadius: 0
                                }
                            ]
                        };
                        this.optionsPerMonthWithMarked = {
                            title: {
                                display: true,
                                text: 'Evolutions par pointage',
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
                                            callback(value, index, values) {
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

                        this.dataCurrentBudget = {
                            labels: [''],
                            datasets: [
                                {
                                    label: 'Budget',
                                    data: [res.body.budgetSmoothedAmounts[res.body.budgetSmoothedAmounts.length - 1]],
                                    borderColor: '#3b5998',
                                    backgroundColor: '#3b599880',
                                    fill: false,
                                    borderWidth: 2
                                },
                                {
                                    label: 'Operations',
                                    data: [res.body.operationAmounts[res.body.operationAmounts.length - 1]],
                                    borderColor: '#49ab81',
                                    backgroundColor: '#49ab8180',
                                    borderWidth: 2,
                                    fill: false
                                }
                            ]
                        };
                        this.optionsCurrentBudget = {
                            title: {
                                display: true,
                                text: 'Consommation du budget',
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
                                            beginAtZero: true,
                                            // suggestedMax: 0,
                                            callback(value, index, values) {
                                                return value + ' €';
                                            }
                                        }
                                    }
                                ],
                                xAxes: [
                                    {
                                        stacked: true,
                                        display: true,
                                        gridLines: {
                                            offsetGridLines: true
                                        }
                                    }
                                ]
                            }
                        };
                        const delta: number =
                            res.body.budgetAtDateAmounts[res.body.budgetAtDateAmounts.length - 1] -
                            res.body.operationAmounts[res.body.operationAmounts.length - 1];
                        let deltaColor: string;
                        if (delta >= 0) {
                            deltaColor = '#d62d20';
                        } else {
                            deltaColor = '#008744';
                        }
                        this.dataDeltaAtDate = {
                            labels: [''],
                            datasets: [
                                {
                                    label: 'Delta',
                                    data: [delta],
                                    borderColor: '#3b5998',
                                    backgroundColor: deltaColor,
                                    fill: false,
                                    borderWidth: 0
                                }
                            ]
                        };
                        this.optionsDeltaAtDate = {
                            title: {
                                display: true,
                                text: 'Delta à date',
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
                                            beginAtZero: true,
                                            // suggestedMax: 0,
                                            callback(value, index, values) {
                                                return value + ' €';
                                            }
                                        }
                                    }
                                ],
                                xAxes: [
                                    {
                                        stacked: true,
                                        display: true,
                                        gridLines: {
                                            offsetGridLines: true
                                        }
                                    }
                                ]
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
