import { AngularPage } from './app.po';
import {$, browser, by, element} from 'protractor';
import {StopPage} from './stop.po';
import {ResourcePage} from './resources.po';
import {PatientFindDialog} from './patientFind.po';

describe('Logon with permissions', () => {
  let page: AngularPage;

  beforeEach(() => {
    page = new AngularPage();
  });

  it ('Initialize', () => {
    page.navigateTo();

    // Wait for login.
    browser.wait(browser.ExpectedConditions.urlContains('/auth/realms/endeavour/protocol/openid-connect'));
  });

  it ('Perform with-access login', () => {
    element(by.name('username')).sendKeys('e2eTestWithPermission');
    element(by.name('password')).sendKeys('e2eTestPass');
    element(by.name('login')).click();

    // Wait for main app page
    browser.wait(() => $('#content').isPresent());
  });

  it ('Check app loaded & defaulted to resource page', () => {
    expect(page.getTitleText()).toEqual('Data Validation');
    expect(StopPage.isDisplayed()).toEqual(false);
    expect(ResourcePage.isDisplayed()).toEqual(true);
    expect(ResourcePage.getFindPersonButton().isEnabled()).toEqual(true);
    expect(PatientFindDialog.isDisplayed()).toEqual(false);
    expect(ResourcePage.getResourceCard().isPresent()).toEqual(false);
  });

  it ('Find & select a patient', () => {
    ResourcePage.getFindPersonButton().click();
    expect(PatientFindDialog.isDisplayed()).toEqual(true);
    PatientFindDialog.getSearchField().sendKeys('4444444444');
    PatientFindDialog.getSearchButton().click();
    browser.wait(() => $('#results').isPresent());

    const resultTable = PatientFindDialog.getResultsTableRows();
    expect(resultTable.count()).toEqual(2);
    expect(PatientFindDialog.getOkButton().isEnabled()).toEqual(false);
    resultTable.get(0).click();
    expect(PatientFindDialog.getOkButton().isEnabled()).toEqual(true);
    PatientFindDialog.getOkButton().click();
    expect(PatientFindDialog.isDisplayed()).toEqual(false);
  });

  it ('Resource card filters populated', () => {
    expect(ResourcePage.isDisplayed()).toEqual(true);
    expect(ResourcePage.getResourceCard().isPresent()).toEqual(true);
    expect(ResourcePage.getResourceTypeDropdownOptions().count()).toEqual(18);
    expect(ResourcePage.getPatientDropdownOptions().count()).toEqual(4);
  });

  it ('Logout', () => {
    page.logout();
    browser.wait(browser.ExpectedConditions.urlContains('/auth/realms/endeavour/protocol/openid-connect'));
  });
});
