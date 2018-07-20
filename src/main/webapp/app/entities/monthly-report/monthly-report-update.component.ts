import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMonthlyReport } from 'app/shared/model/monthly-report.model';
import { MonthlyReportService } from './monthly-report.service';
import { IUser, UserService } from 'app/core';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';

@Component({
    selector: 'jhi-monthly-report-update',
    templateUrl: './monthly-report-update.component.html'
})
export class MonthlyReportUpdateComponent implements OnInit {
    private _monthlyReport: IMonthlyReport;
    isSaving: boolean;

    users: IUser[];

    categories: ICategory[];
    monthDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private monthlyReportService: MonthlyReportService,
        private userService: UserService,
        private categoryService: CategoryService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ monthlyReport }) => {
            this.monthlyReport = monthlyReport;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.categoryService.query().subscribe(
            (res: HttpResponse<ICategory[]>) => {
                this.categories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.monthlyReport.id !== undefined) {
            this.subscribeToSaveResponse(this.monthlyReportService.update(this.monthlyReport));
        } else {
            this.subscribeToSaveResponse(this.monthlyReportService.create(this.monthlyReport));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMonthlyReport>>) {
        result.subscribe((res: HttpResponse<IMonthlyReport>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCategoryById(index: number, item: ICategory) {
        return item.id;
    }
    get monthlyReport() {
        return this._monthlyReport;
    }

    set monthlyReport(monthlyReport: IMonthlyReport) {
        this._monthlyReport = monthlyReport;
    }
}
