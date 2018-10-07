import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { IBudgetItemPeriod } from 'app/shared/model/budget-item-period.model';

type EntityResponseType = HttpResponse<IBudgetItem>;
type EntityArrayResponseType = HttpResponse<IBudgetItem[]>;

@Injectable({ providedIn: 'root' })
export class BudgetItemService {
    private resourceUrl = SERVER_API_URL + 'api/budget-items';
    private resourceAvailableUrl = SERVER_API_URL + 'api/budget-eligible-items';
    private resourceExtendUrl = SERVER_API_URL + 'api/extend-budget-item-periods-and-next';
    private resourceLastBudgetItemPeriodUrl = SERVER_API_URL + 'api/last-budget-item-period';

    constructor(private http: HttpClient) {}

    create(budgetItem: IBudgetItem): Observable<EntityResponseType> {
        return this.http.post<IBudgetItem>(this.resourceUrl, budgetItem, { observe: 'response' });
    }

    update(budgetItem: IBudgetItem): Observable<EntityResponseType> {
        return this.http.put<IBudgetItem>(this.resourceUrl, budgetItem, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBudgetItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBudgetItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    all(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBudgetItem[]>(this.resourceAvailableUrl, { params: options, observe: 'response' });
    }

    extend(id: number): Observable<HttpResponse<void>> {
        return this.http.post<any>(`${this.resourceExtendUrl}/${id}`, { observe: 'response' });
    }

    lastBudgetItem(id: number): Observable<HttpResponse<IBudgetItemPeriod>> {
        return this.http.get<IBudgetItemPeriod>(`${this.resourceLastBudgetItemPeriodUrl}/${id}`, { observe: 'response' });
    }
}
