import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'jhi-account-dashboard',
    templateUrl: './account-dashboard.component.html',
    styles: []
})
export class AccountDashboardComponent implements OnInit {
    monthSelected: Date;
    numberOfMonths: number;
    monthMinusNumberOfMonth: Date;

    constructor() {}

    ngOnInit() {
        const current: Date = new Date(Date.now());
        this.monthSelected = new Date(current.getFullYear(), current.getMonth(), 1);
        this.numberOfMonths = 12;
        this.computeMonthMinusNumberOfMonth();
    }

    computeMonthMinusNumberOfMonth() {
        this.monthMinusNumberOfMonth = new Date(this.monthSelected.getFullYear(), this.monthSelected.getMonth() - this.numberOfMonths, 1);
    }

    handleSelectMonth(event) {
        // console.warn(event);
    }

    handleNumberOfMonthChange(event) {
        this.computeMonthMinusNumberOfMonth();
    }
}
