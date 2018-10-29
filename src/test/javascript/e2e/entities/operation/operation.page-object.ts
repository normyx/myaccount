import { element, by, ElementFinder } from 'protractor';

export class OperationComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-operation div table .btn-danger'));
    title = element.all(by.css('jhi-operation div h2#page-heading span')).first();

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

export class OperationUpdatePage {
    pageTitle = element(by.id('jhi-operation-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    labelInput = element(by.id('field_label'));
    dateInput = element(by.id('field_date'));
    amountInput = element(by.id('field_amount'));
    noteInput = element(by.id('field_note'));
    checkNumberInput = element(by.id('field_checkNumber'));
    isUpToDateInput = element(by.id('field_isUpToDate'));
    subCategorySelect = element(by.id('field_subCategory'));
    accountSelect = element(by.id('field_account'));
    bankAccountSelect = element(by.id('field_bankAccount'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setLabelInput(label) {
        await this.labelInput.sendKeys(label);
    }

    async getLabelInput() {
        return this.labelInput.getAttribute('value');
    }

    async setDateInput(date) {
        await this.dateInput.sendKeys(date);
    }

    async getDateInput() {
        return this.dateInput.getAttribute('value');
    }

    async setAmountInput(amount) {
        await this.amountInput.sendKeys(amount);
    }

    async getAmountInput() {
        return this.amountInput.getAttribute('value');
    }

    async setNoteInput(note) {
        await this.noteInput.sendKeys(note);
    }

    async getNoteInput() {
        return this.noteInput.getAttribute('value');
    }

    async setCheckNumberInput(checkNumber) {
        await this.checkNumberInput.sendKeys(checkNumber);
    }

    async getCheckNumberInput() {
        return this.checkNumberInput.getAttribute('value');
    }

    getIsUpToDateInput() {
        return this.isUpToDateInput;
    }

    async subCategorySelectLastOption() {
        await this.subCategorySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async subCategorySelectOption(option) {
        await this.subCategorySelect.sendKeys(option);
    }

    getSubCategorySelect(): ElementFinder {
        return this.subCategorySelect;
    }

    async getSubCategorySelectedOption() {
        return this.subCategorySelect.element(by.css('option:checked')).getText();
    }

    async accountSelectLastOption() {
        await this.accountSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async accountSelectOption(option) {
        await this.accountSelect.sendKeys(option);
    }

    getAccountSelect(): ElementFinder {
        return this.accountSelect;
    }

    async getAccountSelectedOption() {
        return this.accountSelect.element(by.css('option:checked')).getText();
    }

    async bankAccountSelectLastOption() {
        await this.bankAccountSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async bankAccountSelectOption(option) {
        await this.bankAccountSelect.sendKeys(option);
    }

    getBankAccountSelect(): ElementFinder {
        return this.bankAccountSelect;
    }

    async getBankAccountSelectedOption() {
        return this.bankAccountSelect.element(by.css('option:checked')).getText();
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

export class OperationDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-operation-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-operation'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
