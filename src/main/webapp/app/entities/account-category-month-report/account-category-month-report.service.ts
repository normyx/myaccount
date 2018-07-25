import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAccountCategoryMonthReport } from './account-category-month-report.model';

type EntityResponseType = HttpResponse<IAccountCategoryMonthReport>;

@Injectable({ providedIn: 'root' })
export class AccountCategoryMonthReportService {
    private resourceUrl = SERVER_API_URL + 'api/account-month-report';

    constructor(private http: HttpClient) {}

    getData(categoryId: number): Observable<EntityResponseType> {
        return this.http.get<IAccountCategoryMonthReport>(`${this.resourceUrl}/${categoryId}`, { observe: 'response' });
    }
}
