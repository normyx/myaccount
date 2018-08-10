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
    constructor(private http: HttpClient) {}

    getAmountGlobalPerDayInMonth(month: Moment): Observable<EntityResponseType> {
        const resourceUrl = SERVER_API_URL + 'api/report-amount-global-per-day-in-month';
        return this.http.get<any>(`${resourceUrl}/${month.format('YYYY-MM-DD')}`, { observe: 'response' });
    }

    getAmountCategoryPerMonth(categoryId: number): Observable<EntityResponseType> {
        const resourceUrl = SERVER_API_URL + 'api/report-amount-category-per-month';
        return this.http.get<any>(`${resourceUrl}/${categoryId}`, { observe: 'response' });
    }
}
