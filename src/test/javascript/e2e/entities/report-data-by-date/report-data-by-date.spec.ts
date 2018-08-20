import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { ReportDataByDateComponentsPage, ReportDataByDateUpdatePage } from './report-data-by-date.page-object';

describe('ReportDataByDate e2e test', () => {
    let navBarPage: NavBarPage;
    let reportDataByDateUpdatePage: ReportDataByDateUpdatePage;
    let reportDataByDateComponentsPage: ReportDataByDateComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ReportDataByDates', () => {
        navBarPage.goToEntity('report-data-by-date');
        reportDataByDateComponentsPage = new ReportDataByDateComponentsPage();
        expect(reportDataByDateComponentsPage.getTitle()).toMatch(/Report Data By Dates/);
    });

    it('should load create ReportDataByDate page', () => {
        reportDataByDateComponentsPage.clickOnCreateButton();
        reportDataByDateUpdatePage = new ReportDataByDateUpdatePage();
        expect(reportDataByDateUpdatePage.getPageTitle()).toMatch(/Create or edit a Report Data By Date/);
        reportDataByDateUpdatePage.cancel();
    });

    /* it('should create and save ReportDataByDates', () => {
        reportDataByDateComponentsPage.clickOnCreateButton();
        reportDataByDateUpdatePage.setDateInput('2000-12-31');
        expect(reportDataByDateUpdatePage.getDateInput()).toMatch('2000-12-31');
        reportDataByDateUpdatePage.setMonthInput('2000-12-31');
        expect(reportDataByDateUpdatePage.getMonthInput()).toMatch('2000-12-31');
        reportDataByDateUpdatePage.getHasOperationInput().isSelected().then((selected) => {
            if (selected) {
                reportDataByDateUpdatePage.getHasOperationInput().click();
                expect(reportDataByDateUpdatePage.getHasOperationInput().isSelected()).toBeFalsy();
            } else {
                reportDataByDateUpdatePage.getHasOperationInput().click();
                expect(reportDataByDateUpdatePage.getHasOperationInput().isSelected()).toBeTruthy();
            }
        });
        reportDataByDateUpdatePage.setOperationAmountInput('5');
        expect(reportDataByDateUpdatePage.getOperationAmountInput()).toMatch('5');
        reportDataByDateUpdatePage.setBudgetSmoothedAmountInput('5');
        expect(reportDataByDateUpdatePage.getBudgetSmoothedAmountInput()).toMatch('5');
        reportDataByDateUpdatePage.setBudgetUnsmoothedMarkedAmountInput('5');
        expect(reportDataByDateUpdatePage.getBudgetUnsmoothedMarkedAmountInput()).toMatch('5');
        reportDataByDateUpdatePage.setBudgetUnsmoothedUnmarkedAmountInput('5');
        expect(reportDataByDateUpdatePage.getBudgetUnsmoothedUnmarkedAmountInput()).toMatch('5');
        reportDataByDateUpdatePage.categorySelectLastOption();
        reportDataByDateUpdatePage.accountSelectLastOption();
        reportDataByDateUpdatePage.save();
        expect(reportDataByDateUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
