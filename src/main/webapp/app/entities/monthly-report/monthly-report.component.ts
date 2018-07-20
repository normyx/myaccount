import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMonthlyReport } from 'app/shared/model/monthly-report.model';
import { Principal } from 'app/core';
import { MonthlyReportService } from './monthly-report.service';

@Component({
    selector: 'jhi-monthly-report',
    templateUrl: './monthly-report.component.html'
})
export class MonthlyReportComponent implements OnInit, OnDestroy {
    monthlyReports: IMonthlyReport[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private monthlyReportService: MonthlyReportService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.monthlyReportService.query().subscribe(
            (res: HttpResponse<IMonthlyReport[]>) => {
                this.monthlyReports = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMonthlyReports();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMonthlyReport) {
        return item.id;
    }

    registerChangeInMonthlyReports() {
        this.eventSubscriber = this.eventManager.subscribe('monthlyReportListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
