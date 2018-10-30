import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { MyaBudgetItemService } from './mya-budget-item.service';

@Component({
    selector: 'jhi-mya-budget-item-delete-dialog',
    templateUrl: './mya-budget-item-delete-dialog.component.html'
})
export class MyaBudgetItemDeleteDialogComponent {
    budgetItem: IBudgetItem;

    constructor(
        private budgetItemService: MyaBudgetItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.budgetItemService.deleteWithPeriods(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'myaBudgetItemListModification',
                content: 'Deleted an budgetItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}
