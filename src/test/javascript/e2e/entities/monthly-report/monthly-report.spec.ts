import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { MonthlyReportComponentsPage, MonthlyReportUpdatePage } from './monthly-report.page-object';

describe('MonthlyReport e2e test', () => {
    let navBarPage: NavBarPage;
    let monthlyReportUpdatePage: MonthlyReportUpdatePage;
    let monthlyReportComponentsPage: MonthlyReportComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load MonthlyReports', () => {
        navBarPage.goToEntity('monthly-report');
        monthlyReportComponentsPage = new MonthlyReportComponentsPage();
        expect(monthlyReportComponentsPage.getTitle()).toMatch(/Monthly Reports/);
    });

    it('should load create MonthlyReport page', () => {
        monthlyReportComponentsPage.clickOnCreateButton();
        monthlyReportUpdatePage = new MonthlyReportUpdatePage();
        expect(monthlyReportUpdatePage.getPageTitle()).toMatch(/Create or edit a Monthly Report/);
        monthlyReportUpdatePage.cancel();
    });

    it('should create and save MonthlyReports', () => {
        monthlyReportComponentsPage.clickOnCreateButton();
        monthlyReportUpdatePage.setMonthInput('2000-12-31');
        expect(monthlyReportUpdatePage.getMonthInput()).toMatch('2000-12-31');
        monthlyReportUpdatePage.setMonthValueInput('5');
        expect(monthlyReportUpdatePage.getMonthValueInput()).toMatch('5');
        monthlyReportUpdatePage.setMonthValueAvg3MonthsInput('5');
        expect(monthlyReportUpdatePage.getMonthValueAvg3MonthsInput()).toMatch('5');
        monthlyReportUpdatePage.setMonthValueAvg12MonthsInput('5');
        expect(monthlyReportUpdatePage.getMonthValueAvg12MonthsInput()).toMatch('5');
        monthlyReportUpdatePage.accountSelectLastOption();
        monthlyReportUpdatePage.categorySelectLastOption();
        monthlyReportUpdatePage.save();
        expect(monthlyReportUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
