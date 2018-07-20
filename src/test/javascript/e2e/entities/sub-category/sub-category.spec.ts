import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { SubCategoryComponentsPage, SubCategoryUpdatePage } from './sub-category.page-object';

describe('SubCategory e2e test', () => {
    let navBarPage: NavBarPage;
    let subCategoryUpdatePage: SubCategoryUpdatePage;
    let subCategoryComponentsPage: SubCategoryComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SubCategories', () => {
        navBarPage.goToEntity('sub-category');
        subCategoryComponentsPage = new SubCategoryComponentsPage();
        expect(subCategoryComponentsPage.getTitle()).toMatch(/Sub Categories/);
    });

    it('should load create SubCategory page', () => {
        subCategoryComponentsPage.clickOnCreateButton();
        subCategoryUpdatePage = new SubCategoryUpdatePage();
        expect(subCategoryUpdatePage.getPageTitle()).toMatch(/Create or edit a Sub Category/);
        subCategoryUpdatePage.cancel();
    });

    it('should create and save SubCategories', () => {
        subCategoryComponentsPage.clickOnCreateButton();
        subCategoryUpdatePage.setSubCategoryNameInput('subCategoryName');
        expect(subCategoryUpdatePage.getSubCategoryNameInput()).toMatch('subCategoryName');
        subCategoryUpdatePage.categorySelectLastOption();
        subCategoryUpdatePage.save();
        expect(subCategoryUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
