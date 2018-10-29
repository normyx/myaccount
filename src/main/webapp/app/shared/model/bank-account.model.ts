export interface IBankAccount {
    id?: number;
    accountName?: string;
    accountBank?: string;
    initialAmount?: number;
    accountLogin?: string;
    accountId?: number;
}

export class BankAccount implements IBankAccount {
    constructor(
        public id?: number,
        public accountName?: string,
        public accountBank?: string,
        public initialAmount?: number,
        public accountLogin?: string,
        public accountId?: number
    ) {}
}
