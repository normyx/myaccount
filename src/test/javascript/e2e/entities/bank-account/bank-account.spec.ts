import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BankAccountComponentsPage, BankAccountDeleteDialog, BankAccountUpdatePage } from './bank-account.page-object';

describe('BankAccount e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let bankAccountUpdatePage: BankAccountUpdatePage;
    let bankAccountComponentsPage: BankAccountComponentsPage;
    /*let bankAccountDeleteDialog: BankAccountDeleteDialog;*/

    beforeAll(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load BankAccounts', async () => {
        await navBarPage.goToEntity('bank-account');
        bankAccountComponentsPage = new BankAccountComponentsPage();
        expect(await bankAccountComponentsPage.getTitle()).toMatch(/Bank Accounts/);
    });

    it('should load create BankAccount page', async () => {
        await bankAccountComponentsPage.clickOnCreateButton();
        bankAccountUpdatePage = new BankAccountUpdatePage();
        expect(await bankAccountUpdatePage.getPageTitle()).toMatch(/Create or edit a Bank Account/);
        await bankAccountUpdatePage.cancel();
    });

    /* it('should create and save BankAccounts', async () => {
        await bankAccountComponentsPage.clickOnCreateButton();
        await bankAccountUpdatePage.setAccountNameInput('accountName');
        expect(await bankAccountUpdatePage.getAccountNameInput()).toMatch('accountName');
        await bankAccountUpdatePage.setAccountBankInput('accountBank');
        expect(await bankAccountUpdatePage.getAccountBankInput()).toMatch('accountBank');
        await bankAccountUpdatePage.setInitialAmountInput('5');
        expect(await bankAccountUpdatePage.getInitialAmountInput()).toMatch('5');
        await bankAccountUpdatePage.accountSelectLastOption();
        await bankAccountUpdatePage.save();
        expect(await bankAccountUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    /* it('should delete last BankAccount', async () => {
        const nbButtonsBeforeDelete = await bankAccountComponentsPage.countDeleteButtons();
        await bankAccountComponentsPage.clickOnLastDeleteButton();

        bankAccountDeleteDialog = new BankAccountDeleteDialog();
        expect(await bankAccountDeleteDialog.getDialogTitle())
            .toMatch(/Are you sure you want to delete this Bank Account?/);
        await bankAccountDeleteDialog.clickOnConfirmButton();

        expect(await bankAccountComponentsPage.countDeleteButtons()).toBe(nbButtonsBeforeDelete - 1);
    });*/

    afterAll(async () => {
        await navBarPage.autoSignOut();
    });
});
