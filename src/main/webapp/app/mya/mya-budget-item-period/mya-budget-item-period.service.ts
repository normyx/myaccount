import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IBudgetItemPeriod } from 'app/shared/model/budget-item-period.model';
import { BudgetItemPeriodService } from 'app/entities/budget-item-period/budget-item-period.service';

type EntityResponseType = HttpResponse<IBudgetItemPeriod>;
type EntityArrayResponseType = HttpResponse<IBudgetItemPeriod[]>;

@Injectable({ providedIn: 'root' })
export class MyaBudgetItemPeriodService {
    private resourceBudgetItemPeriodAndNextUrl = SERVER_API_URL + 'api/budget-item-periods-and-next';

    constructor(private http: HttpClient, private budgetItemService: BudgetItemPeriodService) {}

    query(req?: any): Observable<EntityArrayResponseType> {
        return this.budgetItemService.query(req);
    }

    find(id: number): Observable<EntityResponseType> {
        return this.budgetItemService.find(id);
    }

    create(budgetItemPeriod: IBudgetItemPeriod): Observable<EntityResponseType> {
        return this.budgetItemService.create(budgetItemPeriod);
    }

    update(budgetItemPeriod: IBudgetItemPeriod): Observable<EntityResponseType> {
        return this.budgetItemService.update(budgetItemPeriod);
    }
    updateWithNext(budgetItemPeriod: IBudgetItemPeriod): Observable<HttpResponse<any>> {
        const copy = this.budgetItemService.convertDateFromClient(budgetItemPeriod);
        return this.http.put<IBudgetItemPeriod>(`${this.resourceBudgetItemPeriodAndNextUrl}`, copy, { observe: 'response' });
    }

    deleteBudgetItemPeriodWithNext(budgetItemPeriodId: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceBudgetItemPeriodAndNextUrl}/${budgetItemPeriodId}`, { observe: 'response' });
    }
}
