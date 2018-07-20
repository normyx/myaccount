import { element, by, promise, ElementFinder } from 'protractor';

export class BudgetItemComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-budget-item div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class BudgetItemUpdatePage {
    pageTitle = element(by.id('jhi-budget-item-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    orderInput = element(by.id('field_order'));
    categorySelect = element(by.id('field_category'));
    accountSelect = element(by.id('field_account'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setNameInput(name): promise.Promise<void> {
        return this.nameInput.sendKeys(name);
    }

    getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    setOrderInput(order): promise.Promise<void> {
        return this.orderInput.sendKeys(order);
    }

    getOrderInput() {
        return this.orderInput.getAttribute('value');
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
