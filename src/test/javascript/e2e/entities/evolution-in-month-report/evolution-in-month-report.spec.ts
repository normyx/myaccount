import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { EvolutionInMonthReportComponentsPage, EvolutionInMonthReportUpdatePage } from './evolution-in-month-report.page-object';

describe('EvolutionInMonthReport e2e test', () => {
    let navBarPage: NavBarPage;
    let evolutionInMonthReportUpdatePage: EvolutionInMonthReportUpdatePage;
    let evolutionInMonthReportComponentsPage: EvolutionInMonthReportComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load EvolutionInMonthReports', () => {
        navBarPage.goToEntity('evolution-in-month-report');
        evolutionInMonthReportComponentsPage = new EvolutionInMonthReportComponentsPage();
        expect(evolutionInMonthReportComponentsPage.getTitle()).toMatch(/Evolution In Month Reports/);
    });

    it('should load create EvolutionInMonthReport page', () => {
        evolutionInMonthReportComponentsPage.clickOnCreateButton();
        evolutionInMonthReportUpdatePage = new EvolutionInMonthReportUpdatePage();
        expect(evolutionInMonthReportUpdatePage.getPageTitle()).toMatch(/Create or edit a Evolution In Month Report/);
        evolutionInMonthReportUpdatePage.cancel();
    });

    it('should create and save EvolutionInMonthReports', () => {
        evolutionInMonthReportComponentsPage.clickOnCreateButton();
        evolutionInMonthReportUpdatePage.setMonthInput('2000-12-31');
        expect(evolutionInMonthReportUpdatePage.getMonthInput()).toMatch('2000-12-31');
        evolutionInMonthReportUpdatePage.setOperationInput('5');
        expect(evolutionInMonthReportUpdatePage.getOperationInput()).toMatch('5');
        evolutionInMonthReportUpdatePage.setBudgetInput('5');
        expect(evolutionInMonthReportUpdatePage.getBudgetInput()).toMatch('5');
        evolutionInMonthReportUpdatePage.accountSelectLastOption();
        evolutionInMonthReportUpdatePage.save();
        expect(evolutionInMonthReportUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
