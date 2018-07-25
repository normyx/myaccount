import { Moment } from 'moment';

export interface IAccountCategoryMonthReport {
    id?: number;
    accountId?: number;
    categoryId?: number;
    categoryName?: string;
    months?: Moment[];
    amounts?: number[];
    amountsAvg3?: number[];
    amountsAvg12?: number[];
    budgetAmounts?: number[];
}

export class AccountCategoryMonthReport implements IAccountCategoryMonthReport {
    constructor(
        public id?: number,
        public accountId?: number,
        public categoryId?: number,
        public categoryName?: string,
        public months?: Moment[],
        public amounts?: number[],
        public amountsAvg3?: number[],
        public amountsAvg12?: number[],
        public budgetAmounts?: number[]
    ) {}
}
