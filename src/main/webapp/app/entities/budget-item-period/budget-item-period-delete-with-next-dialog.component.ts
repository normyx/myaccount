import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBudgetItemPeriod } from 'app/shared/model/budget-item-period.model';
import { BudgetItemPeriodService } from './budget-item-period.service';

@Component({
    selector: 'jhi-budget-item-period-delete-with-next-dialog',
    templateUrl: './budget-item-period-delete-with-next-dialog.component.html'
})
export class BudgetItemPeriodDeleteWithNextDialogComponent {
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
        this.budgetItemPeriodService.deleteBudgetItemPeriodWithNext(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'myaBudgetItemRowModification' + this.budgetItemPeriod.budgetItemId,
                content: 'Deleted an budgetItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}
