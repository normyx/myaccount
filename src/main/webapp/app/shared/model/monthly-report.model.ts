import { Moment } from 'moment';

export interface IMonthlyReport {
    id?: number;
    month?: Moment;
    monthValue?: number;
    monthValueAvg3Months?: number;
    monthValueAvg12Months?: number;
    accountLogin?: string;
    accountId?: number;
    categoryCategoryName?: string;
    categoryId?: number;
}

export class MonthlyReport implements IMonthlyReport {
    constructor(
        public id?: number,
        public month?: Moment,
        public monthValue?: number,
        public monthValueAvg3Months?: number,
        public monthValueAvg12Months?: number,
        public accountLogin?: string,
        public accountId?: number,
        public categoryCategoryName?: string,
        public categoryId?: number
    ) {}
}
