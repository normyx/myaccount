import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BudgetItemComponentsPage, BudgetItemDeleteDialog, BudgetItemUpdatePage } from './budget-item.page-object';

describe('BudgetItem e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let budgetItemUpdatePage: BudgetItemUpdatePage;
    let budgetItemComponentsPage: BudgetItemComponentsPage;
    let budgetItemDeleteDialog: BudgetItemDeleteDialog;

    beforeAll(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load BudgetItems', async () => {
        await navBarPage.goToEntity('budget-item');
        budgetItemComponentsPage = new BudgetItemComponentsPage();
        expect(await budgetItemComponentsPage.getTitle()).toMatch(/Budget Items/);
    });

    it('should load create BudgetItem page', async () => {
        await budgetItemComponentsPage.clickOnCreateButton();
        budgetItemUpdatePage = new BudgetItemUpdatePage();
        expect(await budgetItemUpdatePage.getPageTitle()).toMatch(/Create or edit a Budget Item/);
        await budgetItemUpdatePage.cancel();
    });

    it('should create and save BudgetItems', async () => {
        await budgetItemComponentsPage.clickOnCreateButton();
        await budgetItemUpdatePage.setNameInput('name');
        expect(await budgetItemUpdatePage.getNameInput()).toMatch('name');
        await budgetItemUpdatePage.setOrderInput('5');
        expect(await budgetItemUpdatePage.getOrderInput()).toMatch('5');
        await budgetItemUpdatePage.categorySelectLastOption();
        await budgetItemUpdatePage.accountSelectLastOption();
        await budgetItemUpdatePage.save();
        expect(await budgetItemUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    it('should delete last BudgetItem', async () => {
        const nbButtonsBeforeDelete = await budgetItemComponentsPage.countDeleteButtons();
        await budgetItemComponentsPage.clickOnLastDeleteButton();

        budgetItemDeleteDialog = new BudgetItemDeleteDialog();
        expect(await budgetItemDeleteDialog.getDialogTitle()).toMatch(/Are you sure you want to delete this Budget Item?/);
        await budgetItemDeleteDialog.clickOnConfirmButton();

        expect(await budgetItemComponentsPage.countDeleteButtons()).toBe(nbButtonsBeforeDelete - 1);
    });

    afterAll(async () => {
        await navBarPage.autoSignOut();
    });
});
