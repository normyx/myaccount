import { ISubCategory } from 'app/shared/model//sub-category.model';

export const enum CategoryType {
    REVENUE = 'REVENUE',
    SPENDING = 'SPENDING',
    OTHER = 'OTHER'
}

export interface ICategory {
    id?: number;
    categoryName?: string;
    categoryType?: CategoryType;
    subCategories?: ISubCategory[];
}

export class Category implements ICategory {
    constructor(
        public id?: number,
        public categoryName?: string,
        public categoryType?: CategoryType,
        public subCategories?: ISubCategory[]
    ) {}
}
