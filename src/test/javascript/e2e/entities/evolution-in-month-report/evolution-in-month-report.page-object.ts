import { element, by, promise, ElementFinder } from 'protractor';

export class EvolutionInMonthReportComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-evolution-in-month-report div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class EvolutionInMonthReportUpdatePage {
    pageTitle = element(by.id('jhi-evolution-in-month-report-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    monthInput = element(by.id('field_month'));
    operationInput = element(by.id('field_operation'));
    budgetInput = element(by.id('field_budget'));
    accountSelect = element(by.id('field_account'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setMonthInput(month): promise.Promise<void> {
        return this.monthInput.sendKeys(month);
    }

    getMonthInput() {
        return this.monthInput.getAttribute('value');
    }

    setOperationInput(operation): promise.Promise<void> {
        return this.operationInput.sendKeys(operation);
    }

    getOperationInput() {
        return this.operationInput.getAttribute('value');
    }

    setBudgetInput(budget): promise.Promise<void> {
        return this.budgetInput.sendKeys(budget);
    }

    getBudgetInput() {
        return this.budgetInput.getAttribute('value');
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
