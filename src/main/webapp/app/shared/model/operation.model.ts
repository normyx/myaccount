import { Moment } from 'moment';

export interface IOperation {
    id?: number;
    label?: string;
    date?: Moment;
    amount?: number;
    note?: string;
    checkNumber?: string;
    isUpToDate?: boolean;
    subCategorySubCategoryName?: string;
    subCategoryId?: number;
    accountLogin?: string;
    accountId?: number;
    budgetItemId?: number;
    bankAccountId?: number;
}

export class Operation implements IOperation {
    constructor(
        public id?: number,
        public label?: string,
        public date?: Moment,
        public amount?: number,
        public note?: string,
        public checkNumber?: string,
        public isUpToDate?: boolean,
        public subCategorySubCategoryName?: string,
        public subCategoryId?: number,
        public accountLogin?: string,
        public accountId?: number,
        public budgetItemId?: number,
        public bankAccountId?: number
    ) {
        this.isUpToDate = this.isUpToDate || false;
    }
}
