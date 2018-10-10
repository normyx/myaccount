import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { BudgetItemPeriodDeleteWithNextDialogComponent } from './budget-item-period-delete-with-next-dialog.component';

@Component({
    selector: 'jhi-budget-item-period-popup',
    template: ''
})
export class BudgetItemPopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        console.warn(this.activatedRoute.snapshot);
        const componentOfModal = BudgetItemPeriodDeleteWithNextDialogComponent;
        this.activatedRoute.data.subscribe(({ budgetItemPeriod }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(this.activatedRoute.snapshot.data['componentClass'] as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.budgetItemPeriod = budgetItemPeriod;
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
