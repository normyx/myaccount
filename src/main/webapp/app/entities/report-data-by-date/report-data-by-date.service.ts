import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReportDataByDate } from 'app/shared/model/report-data-by-date.model';

type EntityResponseType = HttpResponse<IReportDataByDate>;
type EntityArrayResponseType = HttpResponse<IReportDataByDate[]>;

@Injectable({ providedIn: 'root' })
export class ReportDataByDateService {
    private resourceUrl = SERVER_API_URL + 'api/report-data-by-dates';

    constructor(private http: HttpClient) {}

    create(reportDataByDate: IReportDataByDate): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reportDataByDate);
        return this.http
            .post<IReportDataByDate>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(reportDataByDate: IReportDataByDate): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reportDataByDate);
        return this.http
            .put<IReportDataByDate>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IReportDataByDate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IReportDataByDate[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(reportDataByDate: IReportDataByDate): IReportDataByDate {
        const copy: IReportDataByDate = Object.assign({}, reportDataByDate, {
            date: reportDataByDate.date != null && reportDataByDate.date.isValid() ? reportDataByDate.date.format(DATE_FORMAT) : null,
            month: reportDataByDate.month != null && reportDataByDate.month.isValid() ? reportDataByDate.month.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        res.body.month = res.body.month != null ? moment(res.body.month) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((reportDataByDate: IReportDataByDate) => {
            reportDataByDate.date = reportDataByDate.date != null ? moment(reportDataByDate.date) : null;
            reportDataByDate.month = reportDataByDate.month != null ? moment(reportDataByDate.month) : null;
        });
        return res;
    }
}
