import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ReportDataByDate } from 'app/shared/model/report-data-by-date.model';
import { ReportDataByDateService } from './report-data-by-date.service';
import { ReportDataByDateComponent } from './report-data-by-date.component';
import { ReportDataByDateDetailComponent } from './report-data-by-date-detail.component';
import { ReportDataByDateUpdateComponent } from './report-data-by-date-update.component';
import { ReportDataByDateDeletePopupComponent } from './report-data-by-date-delete-dialog.component';
import { IReportDataByDate } from 'app/shared/model/report-data-by-date.model';

@Injectable({ providedIn: 'root' })
export class ReportDataByDateResolve implements Resolve<IReportDataByDate> {
    constructor(private service: ReportDataByDateService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((reportDataByDate: HttpResponse<ReportDataByDate>) => reportDataByDate.body));
        }
        return of(new ReportDataByDate());
    }
}

export const reportDataByDateRoute: Routes = [
    {
        path: 'report-data-by-date',
        component: ReportDataByDateComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReportDataByDates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'report-data-by-date/:id/view',
        component: ReportDataByDateDetailComponent,
        resolve: {
            reportDataByDate: ReportDataByDateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReportDataByDates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'report-data-by-date/new',
        component: ReportDataByDateUpdateComponent,
        resolve: {
            reportDataByDate: ReportDataByDateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReportDataByDates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'report-data-by-date/:id/edit',
        component: ReportDataByDateUpdateComponent,
        resolve: {
            reportDataByDate: ReportDataByDateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReportDataByDates'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reportDataByDatePopupRoute: Routes = [
    {
        path: 'report-data-by-date/:id/delete',
        component: ReportDataByDateDeletePopupComponent,
        resolve: {
            reportDataByDate: ReportDataByDateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReportDataByDates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
