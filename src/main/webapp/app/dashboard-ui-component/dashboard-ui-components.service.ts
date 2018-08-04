import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { Moment } from 'moment';
// import { IAccountCategoryMonthReport } from './account-category-month-report.model';

type EntityResponseType = HttpResponse<any>;

@Injectable({ providedIn: 'root' })
export class DashboardUIComponentsService {
    private resourceUrl = SERVER_API_URL + 'api/report-data-by-month';

    constructor(private http: HttpClient) {}

    getDataDatesWhereMonth(accountId: number, month: Moment): Observable<EntityResponseType> {
        return this.http.get<any>(`${this.resourceUrl}/${accountId}/${month.format('YYYY-MM-DD')}`, { observe: 'response' });
    }
}
