import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { BudgetItemComponentsPage, BudgetItemUpdatePage } from './budget-item.page-object';

describe('BudgetItem e2e test', () => {
    let navBarPage: NavBarPage;
    let budgetItemUpdatePage: BudgetItemUpdatePage;
    let budgetItemComponentsPage: BudgetItemComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load BudgetItems', () => {
        navBarPage.goToEntity('budget-item');
        budgetItemComponentsPage = new BudgetItemComponentsPage();
        expect(budgetItemComponentsPage.getTitle()).toMatch(/Budget Items/);
    });

    it('should load create BudgetItem page', () => {
        budgetItemComponentsPage.clickOnCreateButton();
        budgetItemUpdatePage = new BudgetItemUpdatePage();
        expect(budgetItemUpdatePage.getPageTitle()).toMatch(/Create or edit a Budget Item/);
        budgetItemUpdatePage.cancel();
    });

    it('should create and save BudgetItems', () => {
        budgetItemComponentsPage.clickOnCreateButton();
        budgetItemUpdatePage.setNameInput('name');
        expect(budgetItemUpdatePage.getNameInput()).toMatch('name');
        budgetItemUpdatePage.setOrderInput('5');
        expect(budgetItemUpdatePage.getOrderInput()).toMatch('5');
        budgetItemUpdatePage.categorySelectLastOption();
        budgetItemUpdatePage.accountSelectLastOption();
        budgetItemUpdatePage.save();
        expect(budgetItemUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
