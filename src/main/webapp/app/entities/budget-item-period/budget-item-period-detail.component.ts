import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBudgetItemPeriod } from 'app/shared/model/budget-item-period.model';

@Component({
    selector: 'jhi-budget-item-period-detail',
    templateUrl: './budget-item-period-detail.component.html'
})
export class BudgetItemPeriodDetailComponent implements OnInit {
    budgetItemPeriod: IBudgetItemPeriod;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ budgetItemPeriod }) => {
            this.budgetItemPeriod = budgetItemPeriod;
        });
    }

    previousState() {
        window.history.back();
    }
}
