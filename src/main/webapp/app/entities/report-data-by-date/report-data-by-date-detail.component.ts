import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReportDataByDate } from 'app/shared/model/report-data-by-date.model';

@Component({
    selector: 'jhi-report-data-by-date-detail',
    templateUrl: './report-data-by-date-detail.component.html'
})
export class ReportDataByDateDetailComponent implements OnInit {
    reportDataByDate: IReportDataByDate;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reportDataByDate }) => {
            this.reportDataByDate = reportDataByDate;
        });
    }

    previousState() {
        window.history.back();
    }
}
