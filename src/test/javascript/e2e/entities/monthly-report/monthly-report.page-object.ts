import { element, by, promise, ElementFinder } from 'protractor';

export class MonthlyReportComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-monthly-report div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class MonthlyReportUpdatePage {
    pageTitle = element(by.id('jhi-monthly-report-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    monthInput = element(by.id('field_month'));
    monthValueInput = element(by.id('field_monthValue'));
    monthValueAvg3MonthsInput = element(by.id('field_monthValueAvg3Months'));
    monthValueAvg12MonthsInput = element(by.id('field_monthValueAvg12Months'));
    accountSelect = element(by.id('field_account'));
    categorySelect = element(by.id('field_category'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setMonthInput(month): promise.Promise<void> {
        return this.monthInput.sendKeys(month);
    }

    getMonthInput() {
        return this.monthInput.getAttribute('value');
    }

    setMonthValueInput(monthValue): promise.Promise<void> {
        return this.monthValueInput.sendKeys(monthValue);
    }

    getMonthValueInput() {
        return this.monthValueInput.getAttribute('value');
    }

    setMonthValueAvg3MonthsInput(monthValueAvg3Months): promise.Promise<void> {
        return this.monthValueAvg3MonthsInput.sendKeys(monthValueAvg3Months);
    }

    getMonthValueAvg3MonthsInput() {
        return this.monthValueAvg3MonthsInput.getAttribute('value');
    }

    setMonthValueAvg12MonthsInput(monthValueAvg12Months): promise.Promise<void> {
        return this.monthValueAvg12MonthsInput.sendKeys(monthValueAvg12Months);
    }

    getMonthValueAvg12MonthsInput() {
        return this.monthValueAvg12MonthsInput.getAttribute('value');
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
