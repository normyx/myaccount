import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEvolutionInMonthReport } from 'app/shared/model/evolution-in-month-report.model';

type EntityResponseType = HttpResponse<IEvolutionInMonthReport>;
type EntityArrayResponseType = HttpResponse<IEvolutionInMonthReport[]>;

@Injectable({ providedIn: 'root' })
export class EvolutionInMonthReportService {
    private resourceUrl = SERVER_API_URL + 'api/evolution-in-month-reports';

    constructor(private http: HttpClient) {}

    create(evolutionInMonthReport: IEvolutionInMonthReport): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(evolutionInMonthReport);
        return this.http
            .post<IEvolutionInMonthReport>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(evolutionInMonthReport: IEvolutionInMonthReport): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(evolutionInMonthReport);
        return this.http
            .put<IEvolutionInMonthReport>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEvolutionInMonthReport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEvolutionInMonthReport[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(evolutionInMonthReport: IEvolutionInMonthReport): IEvolutionInMonthReport {
        const copy: IEvolutionInMonthReport = Object.assign({}, evolutionInMonthReport, {
            month:
                evolutionInMonthReport.month != null && evolutionInMonthReport.month.isValid()
                    ? evolutionInMonthReport.month.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.month = res.body.month != null ? moment(res.body.month) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((evolutionInMonthReport: IEvolutionInMonthReport) => {
            evolutionInMonthReport.month = evolutionInMonthReport.month != null ? moment(evolutionInMonthReport.month) : null;
        });
        return res;
    }
}
