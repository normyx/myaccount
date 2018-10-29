/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BudgetItemPeriodComponentsPage, BudgetItemPeriodDeleteDialog, BudgetItemPeriodUpdatePage } from './budget-item-period.page-object';

const expect = chai.expect;

describe('BudgetItemPeriod e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let budgetItemPeriodUpdatePage: BudgetItemPeriodUpdatePage;
    let budgetItemPeriodComponentsPage: BudgetItemPeriodComponentsPage;
    let budgetItemPeriodDeleteDialog: BudgetItemPeriodDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load BudgetItemPeriods', async () => {
        await navBarPage.goToEntity('budget-item-period');
        budgetItemPeriodComponentsPage = new BudgetItemPeriodComponentsPage();
        expect(await budgetItemPeriodComponentsPage.getTitle()).to.eq('Budget Item Periods');
    });

    it('should load create BudgetItemPeriod page', async () => {
        await budgetItemPeriodComponentsPage.clickOnCreateButton();
        budgetItemPeriodUpdatePage = new BudgetItemPeriodUpdatePage();
        expect(await budgetItemPeriodUpdatePage.getPageTitle()).to.eq('Create or edit a Budget Item Period');
        await budgetItemPeriodUpdatePage.cancel();
    });

    it('should create and save BudgetItemPeriods', async () => {
        const nbButtonsBeforeCreate = await budgetItemPeriodComponentsPage.countDeleteButtons();

        await budgetItemPeriodComponentsPage.clickOnCreateButton();
        await promise.all([
            budgetItemPeriodUpdatePage.setDateInput('2000-12-31'),
            budgetItemPeriodUpdatePage.setMonthInput('2000-12-31'),
            budgetItemPeriodUpdatePage.setAmountInput('5'),
            budgetItemPeriodUpdatePage.budgetItemSelectLastOption(),
            budgetItemPeriodUpdatePage.operationSelectLastOption()
        ]);
        expect(await budgetItemPeriodUpdatePage.getDateInput()).to.eq('2000-12-31');
        expect(await budgetItemPeriodUpdatePage.getMonthInput()).to.eq('2000-12-31');
        expect(await budgetItemPeriodUpdatePage.getAmountInput()).to.eq('5');
        const selectedIsSmoothed = budgetItemPeriodUpdatePage.getIsSmoothedInput();
        if (await selectedIsSmoothed.isSelected()) {
            await budgetItemPeriodUpdatePage.getIsSmoothedInput().click();
            expect(await budgetItemPeriodUpdatePage.getIsSmoothedInput().isSelected()).to.be.false;
        } else {
            await budgetItemPeriodUpdatePage.getIsSmoothedInput().click();
            expect(await budgetItemPeriodUpdatePage.getIsSmoothedInput().isSelected()).to.be.true;
        }
        const selectedIsRecurrent = budgetItemPeriodUpdatePage.getIsRecurrentInput();
        if (await selectedIsRecurrent.isSelected()) {
            await budgetItemPeriodUpdatePage.getIsRecurrentInput().click();
            expect(await budgetItemPeriodUpdatePage.getIsRecurrentInput().isSelected()).to.be.false;
        } else {
            await budgetItemPeriodUpdatePage.getIsRecurrentInput().click();
            expect(await budgetItemPeriodUpdatePage.getIsRecurrentInput().isSelected()).to.be.true;
        }
        await budgetItemPeriodUpdatePage.save();
        expect(await budgetItemPeriodUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await budgetItemPeriodComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last BudgetItemPeriod', async () => {
        const nbButtonsBeforeDelete = await budgetItemPeriodComponentsPage.countDeleteButtons();
        await budgetItemPeriodComponentsPage.clickOnLastDeleteButton();

        budgetItemPeriodDeleteDialog = new BudgetItemPeriodDeleteDialog();
        expect(await budgetItemPeriodDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Budget Item Period?');
        await budgetItemPeriodDeleteDialog.clickOnConfirmButton();

        expect(await budgetItemPeriodComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
