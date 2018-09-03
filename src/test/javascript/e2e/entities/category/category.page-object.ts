import { element, by, ElementFinder } from 'protractor';

export class CategoryComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-category div table .btn-danger'));
    title = element.all(by.css('jhi-category div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class CategoryUpdatePage {
    pageTitle = element(by.id('jhi-category-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    categoryNameInput = element(by.id('field_categoryName'));
    categoryTypeSelect = element(by.id('field_categoryType'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setCategoryNameInput(categoryName) {
        await this.categoryNameInput.sendKeys(categoryName);
    }

    async getCategoryNameInput() {
        return this.categoryNameInput.getAttribute('value');
    }

    async setCategoryTypeSelect(categoryType) {
        await this.categoryTypeSelect.sendKeys(categoryType);
    }

    async getCategoryTypeSelect() {
        return this.categoryTypeSelect.element(by.css('option:checked')).getText();
    }

    async categoryTypeSelectLastOption() {
        await this.categoryTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class CategoryDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-category-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-category'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
