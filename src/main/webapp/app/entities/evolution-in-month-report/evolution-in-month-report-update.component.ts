import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEvolutionInMonthReport } from 'app/shared/model/evolution-in-month-report.model';
import { EvolutionInMonthReportService } from './evolution-in-month-report.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-evolution-in-month-report-update',
    templateUrl: './evolution-in-month-report-update.component.html'
})
export class EvolutionInMonthReportUpdateComponent implements OnInit {
    private _evolutionInMonthReport: IEvolutionInMonthReport;
    isSaving: boolean;

    users: IUser[];
    monthDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private evolutionInMonthReportService: EvolutionInMonthReportService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ evolutionInMonthReport }) => {
            this.evolutionInMonthReport = evolutionInMonthReport;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.evolutionInMonthReport.id !== undefined) {
            this.subscribeToSaveResponse(this.evolutionInMonthReportService.update(this.evolutionInMonthReport));
        } else {
            this.subscribeToSaveResponse(this.evolutionInMonthReportService.create(this.evolutionInMonthReport));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEvolutionInMonthReport>>) {
        result.subscribe(
            (res: HttpResponse<IEvolutionInMonthReport>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get evolutionInMonthReport() {
        return this._evolutionInMonthReport;
    }

    set evolutionInMonthReport(evolutionInMonthReport: IEvolutionInMonthReport) {
        this._evolutionInMonthReport = evolutionInMonthReport;
    }
}
