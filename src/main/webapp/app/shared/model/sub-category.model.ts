export interface ISubCategory {
    id?: number;
    subCategoryName?: string;
    categoryCategoryName?: string;
    categoryId?: number;
}

export class SubCategory implements ISubCategory {
    constructor(public id?: number, public subCategoryName?: string, public categoryCategoryName?: string, public categoryId?: number) {}
}
