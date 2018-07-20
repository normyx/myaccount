import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMonthlyReport } from 'app/shared/model/monthly-report.model';

@Component({
    selector: 'jhi-monthly-report-detail',
    templateUrl: './monthly-report-detail.component.html'
})
export class MonthlyReportDetailComponent implements OnInit {
    monthlyReport: IMonthlyReport;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ monthlyReport }) => {
            this.monthlyReport = monthlyReport;
        });
    }

    previousState() {
        window.history.back();
    }
}
