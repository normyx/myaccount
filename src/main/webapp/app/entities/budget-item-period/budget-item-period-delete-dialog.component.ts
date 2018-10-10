import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBudgetItemPeriod } from 'app/shared/model/budget-item-period.model';
import { BudgetItemPeriodService } from './budget-item-period.service';

@Component({
    selector: 'jhi-budget-item-period-delete-dialog',
    templateUrl: './budget-item-period-delete-dialog.component.html'
})
export class BudgetItemPeriodDeleteDialogComponent {
    budgetItemPeriod: IBudgetItemPeriod;

    constructor(
        private budgetItemPeriodService: BudgetItemPeriodService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.budgetItemPeriodService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'budgetItemPeriodListModification',
                content: 'Deleted an budgetItemPeriod'
            });
            this.activeModal.dismiss(true);
        });
    }
}
