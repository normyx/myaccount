import { element, by, promise, ElementFinder } from 'protractor';

export class BudgetItemPeriodComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-budget-item-period div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
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

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setDateInput(date): promise.Promise<void> {
        return this.dateInput.sendKeys(date);
    }

    getDateInput() {
        return this.dateInput.getAttribute('value');
    }

    setMonthInput(month): promise.Promise<void> {
        return this.monthInput.sendKeys(month);
    }

    getMonthInput() {
        return this.monthInput.getAttribute('value');
    }

    setAmountInput(amount): promise.Promise<void> {
        return this.amountInput.sendKeys(amount);
    }

    getAmountInput() {
        return this.amountInput.getAttribute('value');
    }

    getIsSmoothedInput() {
        return this.isSmoothedInput;
    }
    getIsRecurrentInput() {
        return this.isRecurrentInput;
    }
    budgetItemSelectLastOption(): promise.Promise<void> {
        return this.budgetItemSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    budgetItemSelectOption(option): promise.Promise<void> {
        return this.budgetItemSelect.sendKeys(option);
    }

    getBudgetItemSelect(): ElementFinder {
        return this.budgetItemSelect;
    }

    getBudgetItemSelectedOption() {
        return this.budgetItemSelect.element(by.css('option:checked')).getText();
    }

    operationSelectLastOption(): promise.Promise<void> {
        return this.operationSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    operationSelectOption(option): promise.Promise<void> {
        return this.operationSelect.sendKeys(option);
    }

    getOperationSelect(): ElementFinder {
        return this.operationSelect;
    }

    getOperationSelectedOption() {
        return this.operationSelect.element(by.css('option:checked')).getText();
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
