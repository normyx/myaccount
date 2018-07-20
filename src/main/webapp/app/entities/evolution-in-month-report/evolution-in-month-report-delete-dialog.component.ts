import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEvolutionInMonthReport } from 'app/shared/model/evolution-in-month-report.model';
import { EvolutionInMonthReportService } from './evolution-in-month-report.service';

@Component({
    selector: 'jhi-evolution-in-month-report-delete-dialog',
    templateUrl: './evolution-in-month-report-delete-dialog.component.html'
})
export class EvolutionInMonthReportDeleteDialogComponent {
    evolutionInMonthReport: IEvolutionInMonthReport;

    constructor(
        private evolutionInMonthReportService: EvolutionInMonthReportService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.evolutionInMonthReportService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'evolutionInMonthReportListModification',
                content: 'Deleted an evolutionInMonthReport'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-evolution-in-month-report-delete-popup',
    template: ''
})
export class EvolutionInMonthReportDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ evolutionInMonthReport }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EvolutionInMonthReportDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.evolutionInMonthReport = evolutionInMonthReport;
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
