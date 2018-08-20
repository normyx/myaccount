import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IReportDataByDate } from 'app/shared/model/report-data-by-date.model';
import { ReportDataByDateService } from './report-data-by-date.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-report-data-by-date-update',
    templateUrl: './report-data-by-date-update.component.html'
})
export class ReportDataByDateUpdateComponent implements OnInit {
    private _reportDataByDate: IReportDataByDate;
    isSaving: boolean;

    categories: ICategory[];

    users: IUser[];
    dateDp: any;
    monthDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private reportDataByDateService: ReportDataByDateService,
        private categoryService: CategoryService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reportDataByDate }) => {
            this.reportDataByDate = reportDataByDate;
        });
        this.categoryService.query().subscribe(
            (res: HttpResponse<ICategory[]>) => {
                this.categories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.reportDataByDate.id !== undefined) {
            this.subscribeToSaveResponse(this.reportDataByDateService.update(this.reportDataByDate));
        } else {
            this.subscribeToSaveResponse(this.reportDataByDateService.create(this.reportDataByDate));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReportDataByDate>>) {
        result.subscribe((res: HttpResponse<IReportDataByDate>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCategoryById(index: number, item: ICategory) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get reportDataByDate() {
        return this._reportDataByDate;
    }

    set reportDataByDate(reportDataByDate: IReportDataByDate) {
        this._reportDataByDate = reportDataByDate;
    }
}
