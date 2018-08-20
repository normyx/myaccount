import { Moment } from 'moment';

export interface IReportDataByDate {
    id?: number;
    date?: Moment;
    month?: Moment;
    hasOperation?: boolean;
    operationAmount?: number;
    budgetSmoothedAmount?: number;
    budgetUnsmoothedMarkedAmount?: number;
    budgetUnsmoothedUnmarkedAmount?: number;
    categoryId?: number;
    accountLogin?: string;
    accountId?: number;
}

export class ReportDataByDate implements IReportDataByDate {
    constructor(
        public id?: number,
        public date?: Moment,
        public month?: Moment,
        public hasOperation?: boolean,
        public operationAmount?: number,
        public budgetSmoothedAmount?: number,
        public budgetUnsmoothedMarkedAmount?: number,
        public budgetUnsmoothedUnmarkedAmount?: number,
        public categoryId?: number,
        public accountLogin?: string,
        public accountId?: number
    ) {
        this.hasOperation = false;
    }
}
