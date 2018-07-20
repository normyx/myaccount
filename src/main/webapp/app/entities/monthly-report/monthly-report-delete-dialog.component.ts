import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMonthlyReport } from 'app/shared/model/monthly-report.model';
import { MonthlyReportService } from './monthly-report.service';

@Component({
    selector: 'jhi-monthly-report-delete-dialog',
    templateUrl: './monthly-report-delete-dialog.component.html'
})
export class MonthlyReportDeleteDialogComponent {
    monthlyReport: IMonthlyReport;

    constructor(
        private monthlyReportService: MonthlyReportService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.monthlyReportService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'monthlyReportListModification',
                content: 'Deleted an monthlyReport'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-monthly-report-delete-popup',
    template: ''
})
export class MonthlyReportDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ monthlyReport }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MonthlyReportDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.monthlyReport = monthlyReport;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
