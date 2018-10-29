import { element, by, ElementFinder } from 'protractor';

export class BankAccountComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-bank-account div table .btn-danger'));
    title = element.all(by.css('jhi-bank-account div h2#page-heading span')).first();

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

export class BankAccountUpdatePage {
    pageTitle = element(by.id('jhi-bank-account-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    accountNameInput = element(by.id('field_accountName'));
    accountBankInput = element(by.id('field_accountBank'));
    initialAmountInput = element(by.id('field_initialAmount'));
    accountSelect = element(by.id('field_account'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setAccountNameInput(accountName) {
        await this.accountNameInput.sendKeys(accountName);
    }

    async getAccountNameInput() {
        return this.accountNameInput.getAttribute('value');
    }

    async setAccountBankInput(accountBank) {
        await this.accountBankInput.sendKeys(accountBank);
    }

    async getAccountBankInput() {
        return this.accountBankInput.getAttribute('value');
    }

    async setInitialAmountInput(initialAmount) {
        await this.initialAmountInput.sendKeys(initialAmount);
    }

    async getInitialAmountInput() {
        return this.initialAmountInput.getAttribute('value');
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

export class BankAccountDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-bankAccount-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-bankAccount'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
