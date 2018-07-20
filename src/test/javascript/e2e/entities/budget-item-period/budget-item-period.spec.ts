import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { BudgetItemPeriodComponentsPage, BudgetItemPeriodUpdatePage } from './budget-item-period.page-object';

describe('BudgetItemPeriod e2e test', () => {
    let navBarPage: NavBarPage;
    let budgetItemPeriodUpdatePage: BudgetItemPeriodUpdatePage;
    let budgetItemPeriodComponentsPage: BudgetItemPeriodComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load BudgetItemPeriods', () => {
        navBarPage.goToEntity('budget-item-period');
        budgetItemPeriodComponentsPage = new BudgetItemPeriodComponentsPage();
        expect(budgetItemPeriodComponentsPage.getTitle()).toMatch(/Budget Item Periods/);
    });

    it('should load create BudgetItemPeriod page', () => {
        budgetItemPeriodComponentsPage.clickOnCreateButton();
        budgetItemPeriodUpdatePage = new BudgetItemPeriodUpdatePage();
        expect(budgetItemPeriodUpdatePage.getPageTitle()).toMatch(/Create or edit a Budget Item Period/);
        budgetItemPeriodUpdatePage.cancel();
    });

    it('should create and save BudgetItemPeriods', () => {
        budgetItemPeriodComponentsPage.clickOnCreateButton();
        budgetItemPeriodUpdatePage.setDateInput('2000-12-31');
        expect(budgetItemPeriodUpdatePage.getDateInput()).toMatch('2000-12-31');
        budgetItemPeriodUpdatePage.setMonthInput('2000-12-31');
        expect(budgetItemPeriodUpdatePage.getMonthInput()).toMatch('2000-12-31');
        budgetItemPeriodUpdatePage.setAmountInput('5');
        expect(budgetItemPeriodUpdatePage.getAmountInput()).toMatch('5');
        budgetItemPeriodUpdatePage
            .getIsSmoothedInput()
            .isSelected()
            .then(selected => {
                if (selected) {
                    budgetItemPeriodUpdatePage.getIsSmoothedInput().click();
                    expect(budgetItemPeriodUpdatePage.getIsSmoothedInput().isSelected()).toBeFalsy();
                } else {
                    budgetItemPeriodUpdatePage.getIsSmoothedInput().click();
                    expect(budgetItemPeriodUpdatePage.getIsSmoothedInput().isSelected()).toBeTruthy();
                }
            });
        budgetItemPeriodUpdatePage
            .getIsRecurrentInput()
            .isSelected()
            .then(selected => {
                if (selected) {
                    budgetItemPeriodUpdatePage.getIsRecurrentInput().click();
                    expect(budgetItemPeriodUpdatePage.getIsRecurrentInput().isSelected()).toBeFalsy();
                } else {
                    budgetItemPeriodUpdatePage.getIsRecurrentInput().click();
                    expect(budgetItemPeriodUpdatePage.getIsRecurrentInput().isSelected()).toBeTruthy();
                }
            });
        budgetItemPeriodUpdatePage.budgetItemSelectLastOption();
        budgetItemPeriodUpdatePage.operationSelectLastOption();
        budgetItemPeriodUpdatePage.save();
        expect(budgetItemPeriodUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
