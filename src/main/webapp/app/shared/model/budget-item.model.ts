import { IBudgetItemPeriod } from 'app/shared/model//budget-item-period.model';

export interface IBudgetItem {
    id?: number;
    name?: string;
    order?: number;
    budgetItemPeriods?: IBudgetItemPeriod[];
    categoryCategoryName?: string;
    categoryId?: number;
    accountLogin?: string;
    accountId?: number;
}

export class BudgetItem implements IBudgetItem {
    constructor(
        public id?: number,
        public name?: string,
        public order?: number,
        public budgetItemPeriods?: IBudgetItemPeriod[],
        public categoryCategoryName?: string,
        public categoryId?: number,
        public accountLogin?: string,
        public accountId?: number
    ) {}
}
