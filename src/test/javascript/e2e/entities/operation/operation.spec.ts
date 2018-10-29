import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OperationComponentsPage, OperationDeleteDialog, OperationUpdatePage } from './operation.page-object';

describe('Operation e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let operationUpdatePage: OperationUpdatePage;
    let operationComponentsPage: OperationComponentsPage;
    /*let operationDeleteDialog: OperationDeleteDialog;*/

    beforeAll(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Operations', async () => {
        await navBarPage.goToEntity('operation');
        operationComponentsPage = new OperationComponentsPage();
        expect(await operationComponentsPage.getTitle()).toMatch(/Operations/);
    });

    it('should load create Operation page', async () => {
        await operationComponentsPage.clickOnCreateButton();
        operationUpdatePage = new OperationUpdatePage();
        expect(await operationUpdatePage.getPageTitle()).toMatch(/Create or edit a Operation/);
        await operationUpdatePage.cancel();
    });

    /* it('should create and save Operations', async () => {
        await operationComponentsPage.clickOnCreateButton();
        await operationUpdatePage.setLabelInput('label');
        expect(await operationUpdatePage.getLabelInput()).toMatch('label');
        await operationUpdatePage.setDateInput('2000-12-31');
        expect(await operationUpdatePage.getDateInput()).toMatch('2000-12-31');
        await operationUpdatePage.setAmountInput('5');
        expect(await operationUpdatePage.getAmountInput()).toMatch('5');
        await operationUpdatePage.setNoteInput('note');
        expect(await operationUpdatePage.getNoteInput()).toMatch('note');
        await operationUpdatePage.setCheckNumberInput('checkNumber');
        expect(await operationUpdatePage.getCheckNumberInput()).toMatch('checkNumber');
        const selectedIsUpToDate = operationUpdatePage.getIsUpToDateInput();
        if (await selectedIsUpToDate.isSelected()) {
            await operationUpdatePage.getIsUpToDateInput().click();
            expect(await operationUpdatePage.getIsUpToDateInput().isSelected()).toBeFalsy();
        } else {
            await operationUpdatePage.getIsUpToDateInput().click();
            expect(await operationUpdatePage.getIsUpToDateInput().isSelected()).toBeTruthy();
        }
        await operationUpdatePage.subCategorySelectLastOption();
        await operationUpdatePage.accountSelectLastOption();
        await operationUpdatePage.bankAccountSelectLastOption();
        await operationUpdatePage.save();
        expect(await operationUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    /* it('should delete last Operation', async () => {
        const nbButtonsBeforeDelete = await operationComponentsPage.countDeleteButtons();
        await operationComponentsPage.clickOnLastDeleteButton();

        operationDeleteDialog = new OperationDeleteDialog();
        expect(await operationDeleteDialog.getDialogTitle())
            .toMatch(/Are you sure you want to delete this Operation?/);
        await operationDeleteDialog.clickOnConfirmButton();

        expect(await operationComponentsPage.countDeleteButtons()).toBe(nbButtonsBeforeDelete - 1);
    });*/

    afterAll(async () => {
        await navBarPage.autoSignOut();
    });
});
