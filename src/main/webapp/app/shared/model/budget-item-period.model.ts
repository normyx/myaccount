import { Moment } from 'moment';

export interface IBudgetItemPeriod {
    id?: number;
    date?: Moment;
    month?: Moment;
    amount?: number;
    isSmoothed?: boolean;
    isRecurrent?: boolean;
    budgetItemId?: number;
    operationId?: number;
}

export class BudgetItemPeriod implements IBudgetItemPeriod {
    constructor(
        public id?: number,
        public date?: Moment,
        public month?: Moment,
        public amount?: number,
        public isSmoothed?: boolean,
        public isRecurrent?: boolean,
        public budgetItemId?: number,
        public operationId?: number
    ) {
        this.isSmoothed = false;
        this.isRecurrent = false;
    }
}
