import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import * as Moment from 'moment';
// import 'moment/locale/de';

@Component({
    selector: 'jhi-month-picker',
    templateUrl: './monthpicker.component.html'
})
export class MonthpickerComponent implements OnInit {

    @Input() locale: string;
    @Input() year: number;
    @Input() month: number;

    @Input() enabledMonths: Array<number> = [];
    @Input() disabledMonths: Array<number> = [];

    @Input() enabledYears: Array<number> = [];
    @Input() disabledYears: Array<number> = [];
    @Input() multiple: boolean; // TODO

    @Output() change = new EventEmitter<{ monthIndex: number, year: number }>();

    model: MonthPickerModel;
    isShowYears: boolean;
    years: Array<number> = [];

    ngOnInit() {
        if (this.locale) {
            Moment.locale(this.locale);
        } else {
            Moment.locale('de');
        }

        this.model = new MonthPickerModel();

        if (this.year) {
            this.model.selectedYearMoment = Moment().year(this.year);
            this.model.updateYearText();
        }

        if (this.month) {
            this.model.selectedMonthIndex = this.month;
            this.model.selectedMonthMoment = Moment().month(this.month);
            if (this.year) {
                this.model.selectedMonthYear = this.year;
            }
        }

        this.onChange(this.model.selectedMonthIndex, this.model.selectedMonthYear);
        console.warn('noOnInit');
    }

    decrement() {
        this.model.decrementYear();
        if (this.isShowYears) {
            this.renderYears();
        }
    }

    increment() {
        this.model.incrementYear();
        if (this.isShowYears) {
            this.renderYears();
        }
    }

    selectMonth(index: number) {
        this.model.selectMonth(index);
        this.onChange(this.model.selectedMonthIndex, this.model.selectedMonthYear);
    }

    isSelectedMonth(monthIndex: number) {
        return this.model.selectedMonthIndex === monthIndex && this.model.selectedMonthYear === this.model.selectedYearMoment.year();
    }

    onChange(monthIndex: number, year: number) {
        this.change.emit({monthIndex, year});
    }

    isDisabled(index: number) {
        let disabled = false;
        if (this.enabledMonths && this.enabledMonths.length > 0) {
            disabled = this.enabledMonths.indexOf(index) < 0;
        }
        if (this.disabledMonths && this.disabledMonths.length > 0) {
            disabled = this.disabledMonths.indexOf(index) >= 0;
        }
        return disabled;
    }

    toggleShowYears() {
        this.isShowYears = !this.isShowYears;
        this.renderYears();
    }

    renderYears() {
        this.years = [];
        for (let i = 5; i > 0; i--) {
            this.years.push(this.model.selectedYearMoment.year() - i);
        }
        for (let i = 0; i <= 6; i++) {
            this.years.push(this.model.selectedYearMoment.year() + i);
        }
    }

    selectYear(year: number) {
        this.isShowYears = false;
        this.model.selectedYearMoment = Moment().year(year);
        this.model.updateYearText(); // in set get aendern
    }

    isSelectedYear(year: number) {
        return this.model.selectedYearMoment.year() === year;
    }

    isDisabledYear(year: number) {
        // console.warn(year)
        let disabled = false;
        if (this.enabledYears && this.enabledYears.length > 0) {
            disabled = this.enabledYears.findIndex( y => y === year) < 0;
        }
        if (this.disabledYears && this.disabledYears.length > 0) {
            disabled = this.disabledYears.findIndex( y => y === year) >= 0;
        }
        return disabled;
    }
}

export class MonthPickerModel {
    selectedYearMoment: Moment.Moment;
    selectedYearText: string;
    selectedMonthMoment: Moment.Moment;
    selectedMonthIndex: number;
    selectedMonthYear: number;
    months: Array<string> = [];

    constructor() {
        this.selectedYearMoment = Moment();
        this.updateYearText();

        this.selectedMonthMoment = Moment();

        this.months = Moment.months();

        this.selectedMonthIndex = this.selectedMonthMoment.month();
        this.selectedMonthYear = this.selectedYearMoment.year();
    }

    updateYearText() {
        this.selectedYearText = Moment(this.selectedYearMoment).format('YYYY');
    }

    selectMonth(index: number) {
        this.selectedMonthMoment = Moment().month(index);
        this.selectedMonthIndex = this.selectedMonthMoment.month();
        this.selectedMonthYear = this.selectedYearMoment.year();
    }

    incrementYear() {
        this.selectedYearMoment = this.selectedYearMoment.add(1, 'year');
        this.updateYearText();
    }

    decrementYear() {
        this.selectedYearMoment = this.selectedYearMoment.subtract(1, 'year');
        this.updateYearText();
    }
}
