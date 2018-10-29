import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBudgetItemPeriod } from 'app/shared/model/budget-item-period.model';

type EntityResponseType = HttpResponse<IBudgetItemPeriod>;
type EntityArrayResponseType = HttpResponse<IBudgetItemPeriod[]>;

@Injectable({ providedIn: 'root' })
export class BudgetItemPeriodService {
    public resourceUrl = SERVER_API_URL + 'api/budget-item-periods';

    constructor(private http: HttpClient) {}

    create(budgetItemPeriod: IBudgetItemPeriod): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(budgetItemPeriod);
        return this.http
            .post<IBudgetItemPeriod>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(budgetItemPeriod: IBudgetItemPeriod): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(budgetItemPeriod);
        return this.http
            .put<IBudgetItemPeriod>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBudgetItemPeriod>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBudgetItemPeriod[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    public convertDateFromClient(budgetItemPeriod: IBudgetItemPeriod): IBudgetItemPeriod {
        const copy: IBudgetItemPeriod = Object.assign({}, budgetItemPeriod, {
            date: budgetItemPeriod.date != null && budgetItemPeriod.date.isValid() ? budgetItemPeriod.date.format(DATE_FORMAT) : null,
            month: budgetItemPeriod.month != null && budgetItemPeriod.month.isValid() ? budgetItemPeriod.month.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        res.body.month = res.body.month != null ? moment(res.body.month) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((budgetItemPeriod: IBudgetItemPeriod) => {
            budgetItemPeriod.date = budgetItemPeriod.date != null ? moment(budgetItemPeriod.date) : null;
            budgetItemPeriod.month = budgetItemPeriod.month != null ? moment(budgetItemPeriod.month) : null;
        });
        return res;
    }
}
