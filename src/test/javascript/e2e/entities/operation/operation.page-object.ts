import { element, by, promise, ElementFinder } from 'protractor';

export class OperationComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-operation div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
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

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setLabelInput(label): promise.Promise<void> {
        return this.labelInput.sendKeys(label);
    }

    getLabelInput() {
        return this.labelInput.getAttribute('value');
    }

    setDateInput(date): promise.Promise<void> {
        return this.dateInput.sendKeys(date);
    }

    getDateInput() {
        return this.dateInput.getAttribute('value');
    }

    setAmountInput(amount): promise.Promise<void> {
        return this.amountInput.sendKeys(amount);
    }

    getAmountInput() {
        return this.amountInput.getAttribute('value');
    }

    setNoteInput(note): promise.Promise<void> {
        return this.noteInput.sendKeys(note);
    }

    getNoteInput() {
        return this.noteInput.getAttribute('value');
    }

    setCheckNumberInput(checkNumber): promise.Promise<void> {
        return this.checkNumberInput.sendKeys(checkNumber);
    }

    getCheckNumberInput() {
        return this.checkNumberInput.getAttribute('value');
    }

    getIsUpToDateInput() {
        return this.isUpToDateInput;
    }
    subCategorySelectLastOption(): promise.Promise<void> {
        return this.subCategorySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    subCategorySelectOption(option): promise.Promise<void> {
        return this.subCategorySelect.sendKeys(option);
    }

    getSubCategorySelect(): ElementFinder {
        return this.subCategorySelect;
    }

    getSubCategorySelectedOption() {
        return this.subCategorySelect.element(by.css('option:checked')).getText();
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
