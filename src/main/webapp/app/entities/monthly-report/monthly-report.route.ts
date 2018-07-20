import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MonthlyReport } from 'app/shared/model/monthly-report.model';
import { MonthlyReportService } from './monthly-report.service';
import { MonthlyReportComponent } from './monthly-report.component';
import { MonthlyReportDetailComponent } from './monthly-report-detail.component';
import { MonthlyReportUpdateComponent } from './monthly-report-update.component';
import { MonthlyReportDeletePopupComponent } from './monthly-report-delete-dialog.component';
import { IMonthlyReport } from 'app/shared/model/monthly-report.model';

@Injectable({ providedIn: 'root' })
export class MonthlyReportResolve implements Resolve<IMonthlyReport> {
    constructor(private service: MonthlyReportService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((monthlyReport: HttpResponse<MonthlyReport>) => monthlyReport.body));
        }
        return of(new MonthlyReport());
    }
}

export const monthlyReportRoute: Routes = [
    {
        path: 'monthly-report',
        component: MonthlyReportComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlyReports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'monthly-report/:id/view',
        component: MonthlyReportDetailComponent,
        resolve: {
            monthlyReport: MonthlyReportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlyReports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'monthly-report/new',
        component: MonthlyReportUpdateComponent,
        resolve: {
            monthlyReport: MonthlyReportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlyReports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'monthly-report/:id/edit',
        component: MonthlyReportUpdateComponent,
        resolve: {
            monthlyReport: MonthlyReportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlyReports'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const monthlyReportPopupRoute: Routes = [
    {
        path: 'monthly-report/:id/delete',
        component: MonthlyReportDeletePopupComponent,
        resolve: {
            monthlyReport: MonthlyReportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlyReports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
