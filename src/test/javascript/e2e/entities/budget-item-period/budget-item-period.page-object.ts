import { element, by, ElementFinder } from 'protractor';

export class BudgetItemPeriodComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-budget-item-period div table .btn-danger'));
    title = element.all(by.css('jhi-budget-item-period div h2#page-heading span')).first();

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

export class BudgetItemPeriodUpdatePage {
    pageTitle = element(by.id('jhi-budget-item-period-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dateInput = element(by.id('field_date'));
    monthInput = element(by.id('field_month'));
    amountInput = element(by.id('field_amount'));
    isSmoothedInput = element(by.id('field_isSmoothed'));
    isRecurrentInput = element(by.id('field_isRecurrent'));
    budgetItemSelect = element(by.id('field_budgetItem'));
    operationSelect = element(by.id('field_operation'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setDateInput(date) {
        await this.dateInput.sendKeys(date);
    }

    async getDateInput() {
        return this.dateInput.getAttribute('value');
    }

    async setMonthInput(month) {
        await this.monthInput.sendKeys(month);
    }

    async getMonthInput() {
        return this.monthInput.getAttribute('value');
    }

    async setAmountInput(amount) {
        await this.amountInput.sendKeys(amount);
    }

    async getAmountInput() {
        return this.amountInput.getAttribute('value');
    }

    getIsSmoothedInput() {
        return this.isSmoothedInput;
    }
    getIsRecurrentInput() {
        return this.isRecurrentInput;
    }

    async budgetItemSelectLastOption() {
        await this.budgetItemSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async budgetItemSelectOption(option) {
        await this.budgetItemSelect.sendKeys(option);
    }

    getBudgetItemSelect(): ElementFinder {
        return this.budgetItemSelect;
    }

    async getBudgetItemSelectedOption() {
        return this.budgetItemSelect.element(by.css('option:checked')).getText();
    }

    async operationSelectLastOption() {
        await this.operationSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async operationSelectOption(option) {
        await this.operationSelect.sendKeys(option);
    }

    getOperationSelect(): ElementFinder {
        return this.operationSelect;
    }

    async getOperationSelectedOption() {
        return this.operationSelect.element(by.css('option:checked')).getText();
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

export class BudgetItemPeriodDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-budgetItemPeriod-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-budgetItemPeriod'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
