import { Moment } from 'moment';

export interface IEvolutionInMonthReport {
    id?: number;
    month?: Moment;
    operation?: number;
    budget?: number;
    accountLogin?: string;
    accountId?: number;
}

export class EvolutionInMonthReport implements IEvolutionInMonthReport {
    constructor(
        public id?: number,
        public month?: Moment,
        public operation?: number,
        public budget?: number,
        public accountLogin?: string,
        public accountId?: number
    ) {}
}
