import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BudgetItemPeriodComponentsPage, BudgetItemPeriodDeleteDialog, BudgetItemPeriodUpdatePage } from './budget-item-period.page-object';

describe('BudgetItemPeriod e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let budgetItemPeriodUpdatePage: BudgetItemPeriodUpdatePage;
    let budgetItemPeriodComponentsPage: BudgetItemPeriodComponentsPage;
    let budgetItemPeriodDeleteDialog: BudgetItemPeriodDeleteDialog;

    beforeAll(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load BudgetItemPeriods', async () => {
        await navBarPage.goToEntity('budget-item-period');
        budgetItemPeriodComponentsPage = new BudgetItemPeriodComponentsPage();
        expect(await budgetItemPeriodComponentsPage.getTitle()).toMatch(/Budget Item Periods/);
    });

    it('should load create BudgetItemPeriod page', async () => {
        await budgetItemPeriodComponentsPage.clickOnCreateButton();
        budgetItemPeriodUpdatePage = new BudgetItemPeriodUpdatePage();
        expect(await budgetItemPeriodUpdatePage.getPageTitle()).toMatch(/Create or edit a Budget Item Period/);
        await budgetItemPeriodUpdatePage.cancel();
    });

    it('should create and save BudgetItemPeriods', async () => {
        await budgetItemPeriodComponentsPage.clickOnCreateButton();
        await budgetItemPeriodUpdatePage.setDateInput('2000-12-31');
        expect(await budgetItemPeriodUpdatePage.getDateInput()).toMatch('2000-12-31');
        await budgetItemPeriodUpdatePage.setMonthInput('2000-12-31');
        expect(await budgetItemPeriodUpdatePage.getMonthInput()).toMatch('2000-12-31');
        await budgetItemPeriodUpdatePage.setAmountInput('5');
        expect(await budgetItemPeriodUpdatePage.getAmountInput()).toMatch('5');
        const selectedIsSmoothed = budgetItemPeriodUpdatePage.getIsSmoothedInput();
        if (await selectedIsSmoothed.isSelected()) {
            await budgetItemPeriodUpdatePage.getIsSmoothedInput().click();
            expect(await budgetItemPeriodUpdatePage.getIsSmoothedInput().isSelected()).toBeFalsy();
        } else {
            await budgetItemPeriodUpdatePage.getIsSmoothedInput().click();
            expect(await budgetItemPeriodUpdatePage.getIsSmoothedInput().isSelected()).toBeTruthy();
        }
        const selectedIsRecurrent = budgetItemPeriodUpdatePage.getIsRecurrentInput();
        if (await selectedIsRecurrent.isSelected()) {
            await budgetItemPeriodUpdatePage.getIsRecurrentInput().click();
            expect(await budgetItemPeriodUpdatePage.getIsRecurrentInput().isSelected()).toBeFalsy();
        } else {
            await budgetItemPeriodUpdatePage.getIsRecurrentInput().click();
            expect(await budgetItemPeriodUpdatePage.getIsRecurrentInput().isSelected()).toBeTruthy();
        }
        await budgetItemPeriodUpdatePage.budgetItemSelectLastOption();
        await budgetItemPeriodUpdatePage.operationSelectLastOption();
        await budgetItemPeriodUpdatePage.save();
        expect(await budgetItemPeriodUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    it('should delete last BudgetItemPeriod', async () => {
        const nbButtonsBeforeDelete = await budgetItemPeriodComponentsPage.countDeleteButtons();
        await budgetItemPeriodComponentsPage.clickOnLastDeleteButton();

        budgetItemPeriodDeleteDialog = new BudgetItemPeriodDeleteDialog();
        expect(await budgetItemPeriodDeleteDialog.getDialogTitle()).toMatch(/Are you sure you want to delete this Budget Item Period?/);
        await budgetItemPeriodDeleteDialog.clickOnConfirmButton();

        expect(await budgetItemPeriodComponentsPage.countDeleteButtons()).toBe(nbButtonsBeforeDelete - 1);
    });

    afterAll(async () => {
        await navBarPage.autoSignOut();
    });
});
