import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBudgetItem } from 'app/shared/model/budget-item.model';

@Component({
    selector: 'jhi-mya-budget-item-detail',
    templateUrl: './mya-budget-item-detail.component.html'
})
export class MyaBudgetItemDetailComponent implements OnInit {
    budgetItem: IBudgetItem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ budgetItem }) => {
            this.budgetItem = budgetItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
