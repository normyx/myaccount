import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { BudgetItemService } from './budget-item.service';

@Component({
    selector: 'jhi-budget-item-delete-dialog',
    templateUrl: './budget-item-delete-dialog.component.html'
})
export class BudgetItemDeleteDialogComponent {
    budgetItem: IBudgetItem;

    constructor(private budgetItemService: BudgetItemService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.budgetItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'budgetItemListModification',
                content: 'Deleted an budgetItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}
