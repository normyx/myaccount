import { element, by, promise, ElementFinder } from 'protractor';

export class ReportDataByDateComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-report-data-by-date div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class ReportDataByDateUpdatePage {
    pageTitle = element(by.id('jhi-report-data-by-date-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dateInput = element(by.id('field_date'));
    monthInput = element(by.id('field_month'));
    hasOperationInput = element(by.id('field_hasOperation'));
    operationAmountInput = element(by.id('field_operationAmount'));
    budgetSmoothedAmountInput = element(by.id('field_budgetSmoothedAmount'));
    budgetUnsmoothedMarkedAmountInput = element(by.id('field_budgetUnsmoothedMarkedAmount'));
    budgetUnsmoothedUnmarkedAmountInput = element(by.id('field_budgetUnsmoothedUnmarkedAmount'));
    categorySelect = element(by.id('field_category'));
    accountSelect = element(by.id('field_account'));

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

    getHasOperationInput() {
        return this.hasOperationInput;
    }
    setOperationAmountInput(operationAmount): promise.Promise<void> {
        return this.operationAmountInput.sendKeys(operationAmount);
    }

    getOperationAmountInput() {
        return this.operationAmountInput.getAttribute('value');
    }

    setBudgetSmoothedAmountInput(budgetSmoothedAmount): promise.Promise<void> {
        return this.budgetSmoothedAmountInput.sendKeys(budgetSmoothedAmount);
    }

    getBudgetSmoothedAmountInput() {
        return this.budgetSmoothedAmountInput.getAttribute('value');
    }

    setBudgetUnsmoothedMarkedAmountInput(budgetUnsmoothedMarkedAmount): promise.Promise<void> {
        return this.budgetUnsmoothedMarkedAmountInput.sendKeys(budgetUnsmoothedMarkedAmount);
    }

    getBudgetUnsmoothedMarkedAmountInput() {
        return this.budgetUnsmoothedMarkedAmountInput.getAttribute('value');
    }

    setBudgetUnsmoothedUnmarkedAmountInput(budgetUnsmoothedUnmarkedAmount): promise.Promise<void> {
        return this.budgetUnsmoothedUnmarkedAmountInput.sendKeys(budgetUnsmoothedUnmarkedAmount);
    }

    getBudgetUnsmoothedUnmarkedAmountInput() {
        return this.budgetUnsmoothedUnmarkedAmountInput.getAttribute('value');
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

    accountSelectLastOption(): promise.Promise<void> {
        return this.accountSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    accountSelectOption(option): promise.Promise<void> {
        return this.accountSelect.sendKeys(option);
    }

    getAccountSelect(): ElementFinder {
        return this.accountSelect;
    }

    getAccountSelectedOption() {
        return this.accountSelect.element(by.css('option:checked')).getText();
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
