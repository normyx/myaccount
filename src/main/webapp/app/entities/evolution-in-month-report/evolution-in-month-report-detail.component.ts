import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvolutionInMonthReport } from 'app/shared/model/evolution-in-month-report.model';

@Component({
    selector: 'jhi-evolution-in-month-report-detail',
    templateUrl: './evolution-in-month-report-detail.component.html'
})
export class EvolutionInMonthReportDetailComponent implements OnInit {
    evolutionInMonthReport: IEvolutionInMonthReport;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ evolutionInMonthReport }) => {
            this.evolutionInMonthReport = evolutionInMonthReport;
        });
    }

    previousState() {
        window.history.back();
    }
}
