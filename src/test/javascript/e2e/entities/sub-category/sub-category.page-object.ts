import { element, by, promise, ElementFinder } from 'protractor';

export class SubCategoryComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-sub-category div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class SubCategoryUpdatePage {
    pageTitle = element(by.id('jhi-sub-category-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    subCategoryNameInput = element(by.id('field_subCategoryName'));
    categorySelect = element(by.id('field_category'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setSubCategoryNameInput(subCategoryName): promise.Promise<void> {
        return this.subCategoryNameInput.sendKeys(subCategoryName);
    }

    getSubCategoryNameInput() {
        return this.subCategoryNameInput.getAttribute('value');
    }

    categorySelectLastOption(): promise.Promise<void> {
        return this.categorySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    categorySelectOption(option): promise.Promise<void> {
        return this.categorySelect.sendKeys(option);
    }

    getCategorySelect(): ElementFinder {
        return this.categorySelect;
    }

    getCategorySelectedOption() {
        return this.categorySelect.element(by.css('option:checked')).getText();
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
