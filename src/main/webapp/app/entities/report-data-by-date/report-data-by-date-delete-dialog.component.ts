import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReportDataByDate } from 'app/shared/model/report-data-by-date.model';
import { ReportDataByDateService } from './report-data-by-date.service';

@Component({
    selector: 'jhi-report-data-by-date-delete-dialog',
    templateUrl: './report-data-by-date-delete-dialog.component.html'
})
export class ReportDataByDateDeleteDialogComponent {
    reportDataByDate: IReportDataByDate;

    constructor(
        private reportDataByDateService: ReportDataByDateService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reportDataByDateService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'reportDataByDateListModification',
                content: 'Deleted an reportDataByDate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-report-data-by-date-delete-popup',
    template: ''
})
export class ReportDataByDateDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reportDataByDate }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReportDataByDateDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.reportDataByDate = reportDataByDate;
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
