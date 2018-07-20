import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMonthlyReport } from 'app/shared/model/monthly-report.model';

type EntityResponseType = HttpResponse<IMonthlyReport>;
type EntityArrayResponseType = HttpResponse<IMonthlyReport[]>;

@Injectable({ providedIn: 'root' })
export class MonthlyReportService {
    private resourceUrl = SERVER_API_URL + 'api/monthly-reports';

    constructor(private http: HttpClient) {}

    create(monthlyReport: IMonthlyReport): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(monthlyReport);
        return this.http
            .post<IMonthlyReport>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(monthlyReport: IMonthlyReport): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(monthlyReport);
        return this.http
            .put<IMonthlyReport>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMonthlyReport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMonthlyReport[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(monthlyReport: IMonthlyReport): IMonthlyReport {
        const copy: IMonthlyReport = Object.assign({}, monthlyReport, {
            month: monthlyReport.month != null && monthlyReport.month.isValid() ? monthlyReport.month.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.month = res.body.month != null ? moment(res.body.month) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((monthlyReport: IMonthlyReport) => {
            monthlyReport.month = monthlyReport.month != null ? moment(monthlyReport.month) : null;
        });
        return res;
    }
}
