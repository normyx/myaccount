import { element, by, promise, ElementFinder } from 'protractor';

export class CategoryComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-category div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class CategoryUpdatePage {
    pageTitle = element(by.id('jhi-category-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    categoryNameInput = element(by.id('field_categoryName'));
    categoryTypeSelect = element(by.id('field_categoryType'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setCategoryNameInput(categoryName): promise.Promise<void> {
        return this.categoryNameInput.sendKeys(categoryName);
    }

    getCategoryNameInput() {
        return this.categoryNameInput.getAttribute('value');
    }

    setCategoryTypeSelect(categoryType): promise.Promise<void> {
        return this.categoryTypeSelect.sendKeys(categoryType);
    }

    getCategoryTypeSelect() {
        return this.categoryTypeSelect.element(by.css('option:checked')).getText();
    }

    categoryTypeSelectLastOption(): promise.Promise<void> {
        return this.categoryTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
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
