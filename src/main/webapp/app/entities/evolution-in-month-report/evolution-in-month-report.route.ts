import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EvolutionInMonthReport } from 'app/shared/model/evolution-in-month-report.model';
import { EvolutionInMonthReportService } from './evolution-in-month-report.service';
import { EvolutionInMonthReportComponent } from './evolution-in-month-report.component';
import { EvolutionInMonthReportDetailComponent } from './evolution-in-month-report-detail.component';
import { EvolutionInMonthReportUpdateComponent } from './evolution-in-month-report-update.component';
import { EvolutionInMonthReportDeletePopupComponent } from './evolution-in-month-report-delete-dialog.component';
import { IEvolutionInMonthReport } from 'app/shared/model/evolution-in-month-report.model';

@Injectable({ providedIn: 'root' })
export class EvolutionInMonthReportResolve implements Resolve<IEvolutionInMonthReport> {
    constructor(private service: EvolutionInMonthReportService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((evolutionInMonthReport: HttpResponse<EvolutionInMonthReport>) => evolutionInMonthReport.body));
        }
        return of(new EvolutionInMonthReport());
    }
}

export const evolutionInMonthReportRoute: Routes = [
    {
        path: 'evolution-in-month-report',
        component: EvolutionInMonthReportComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EvolutionInMonthReports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'evolution-in-month-report/:id/view',
        component: EvolutionInMonthReportDetailComponent,
        resolve: {
            evolutionInMonthReport: EvolutionInMonthReportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EvolutionInMonthReports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'evolution-in-month-report/new',
        component: EvolutionInMonthReportUpdateComponent,
        resolve: {
            evolutionInMonthReport: EvolutionInMonthReportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EvolutionInMonthReports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'evolution-in-month-report/:id/edit',
        component: EvolutionInMonthReportUpdateComponent,
        resolve: {
            evolutionInMonthReport: EvolutionInMonthReportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EvolutionInMonthReports'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const evolutionInMonthReportPopupRoute: Routes = [
    {
        path: 'evolution-in-month-report/:id/delete',
        component: EvolutionInMonthReportDeletePopupComponent,
        resolve: {
            evolutionInMonthReport: EvolutionInMonthReportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EvolutionInMonthReports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
