import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { OperationComponentsPage, OperationUpdatePage } from './operation.page-object';

describe('Operation e2e test', () => {
    let navBarPage: NavBarPage;
    let operationUpdatePage: OperationUpdatePage;
    let operationComponentsPage: OperationComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Operations', () => {
        navBarPage.goToEntity('operation');
        operationComponentsPage = new OperationComponentsPage();
        expect(operationComponentsPage.getTitle()).toMatch(/Operations/);
    });

    it('should load create Operation page', () => {
        operationComponentsPage.clickOnCreateButton();
        operationUpdatePage = new OperationUpdatePage();
        expect(operationUpdatePage.getPageTitle()).toMatch(/Create or edit a Operation/);
        operationUpdatePage.cancel();
    });

    it('should create and save Operations', () => {
        operationComponentsPage.clickOnCreateButton();
        operationUpdatePage.setLabelInput('label');
        expect(operationUpdatePage.getLabelInput()).toMatch('label');
        operationUpdatePage.setDateInput('2000-12-31');
        expect(operationUpdatePage.getDateInput()).toMatch('2000-12-31');
        operationUpdatePage.setAmountInput('5');
        expect(operationUpdatePage.getAmountInput()).toMatch('5');
        operationUpdatePage.setNoteInput('note');
        expect(operationUpdatePage.getNoteInput()).toMatch('note');
        operationUpdatePage.setCheckNumberInput('checkNumber');
        expect(operationUpdatePage.getCheckNumberInput()).toMatch('checkNumber');
        operationUpdatePage
            .getIsUpToDateInput()
            .isSelected()
            .then(selected => {
                if (selected) {
                    operationUpdatePage.getIsUpToDateInput().click();
                    expect(operationUpdatePage.getIsUpToDateInput().isSelected()).toBeFalsy();
                } else {
                    operationUpdatePage.getIsUpToDateInput().click();
                    expect(operationUpdatePage.getIsUpToDateInput().isSelected()).toBeTruthy();
                }
            });
        operationUpdatePage.subCategorySelectLastOption();
        operationUpdatePage.accountSelectLastOption();
        operationUpdatePage.save();
        expect(operationUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
