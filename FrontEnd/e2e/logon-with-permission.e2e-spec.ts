import { Application } from './app.po';
import {$, browser, by, element} from 'protractor';
import {StopPage} from './stop.po';
import {ResourcePage} from './resources.po';
import {PersonFindDialog} from './personFind.po';
import {ResourceViewerDialog} from './resourceViewer.po';

describe('Logon with permissions', () => {
  let page: Application;

  beforeEach(() => {
    page = new Application();
  });

  it ('Initialize', () => {
    page.initialize();

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
    expect(page.getTitleText()).toBe('Data Validation', 'Application failed to load');
    expect(StopPage.isDisplayed()).toBe(false, 'Stop page should not be displayed');
    expect(ResourcePage.isDisplayed()).toBe(true, 'Resource page should display as default');
    expect(ResourcePage.getFindPersonButton().isEnabled()).toBe(true, 'Find Person button should be enabled');
    expect(PersonFindDialog.isDisplayed()).toBe(false, 'Find person dialog should be hidden');
    expect(ResourcePage.getResourceCard().isPresent()).toBe(false, 'Main resource display should be hidden');
  });

  it ('Find & select a patient', () => {
    ResourcePage.getFindPersonButton().click();
    expect(PersonFindDialog.isDisplayed()).toBe(true, 'Find Person button should display patient find dialog');

    PersonFindDialog.getSearchField().sendKeys('4444444444');
    PersonFindDialog.getSearchButton().click();
    browser.wait(() => PersonFindDialog.getResultTable().isPresent());
    expect(PersonFindDialog.getResultsTableRows().count()).toBe(2, 'Test NHS number should match 2 patients');
    expect(PersonFindDialog.getOkButton().isEnabled()).toBe(false, 'OK button should be disabled until patient is selected');

    PersonFindDialog.getResultsTableRows().get(0).click();
    expect(PersonFindDialog.getOkButton().isEnabled()).toBe(true, 'OK button should be enabled once a patient is selected');

    PersonFindDialog.getOkButton().click();
    expect(PersonFindDialog.isDisplayed()).toBe(false, 'Person Find dialog should be hidden when Ok clicked');
  });

  it ('Resource card filters populated', () => {
    expect(ResourcePage.getResourceCard().isPresent()).toBe(true, 'Resource card should be visible once a person is selected');
    expect(ResourcePage.getResourceTypeDropdown().getText()).toBe('Select', 'No resource types should be selected by default');
    expect(ResourcePage.getResourceTypeDropdownOptions().count()).toBe(18, 'All resource types should be available for selection');
    expect(ResourcePage.getPatientDropdownOptions().count()).toBe(4, 'Test person should have 2 patients (plus All/None options == 4)');
    expect(ResourcePage.getPatientDropdown().getText()).toBe('All', 'All patients should be selected by default');
    expect(ResourcePage.getLoadButton().isEnabled()).toBe(false, 'Load button incorrectly enabled');
  });

  it('Load patient resources', () => {
    ResourcePage.getResourceTypeDropdown().click();
    ResourcePage.getResourceTypeDropdownOptionByText('Condition').click();
    ResourcePage.getLoadButton().click();
    browser.wait(() => ResourcePage.getPatientTable().isDisplayed() && ResourcePage.getResourceTable().isDisplayed());
    expect(ResourcePage.getPatientTableRows().count()).toBe(2, 'Patient resource for both patients should load');
    expect(ResourcePage.getResourceTableRows().count()).toBe(9, 'Condition resource for both patients should load');
  });

  it('Display patient resource dialog', () => {
    ResourcePage.getPatientTableRows().get(0).click();
    expect(ResourceViewerDialog.isDisplayed()).toBe(true, 'Resource viewer should be displayed');
    expect(ResourceViewerDialog.getTitle().getText()).toContain('Patient - ', 'Resource viewer should show Patient resource');
    expect(ResourceViewerDialog.getActiveTab().getText()).toBe('Raw', 'Raw tab should be active by default');
    expect(ResourceViewerDialog.getRawView().isDisplayed()).toBe(true, 'Raw view should be displayed');
    expect(ResourceViewerDialog.getClinicalView().isPresent()).toBe(false, 'Clinical view should not be present');

    ResourceViewerDialog.getClinicalTab().click();
    expect(ResourceViewerDialog.getActiveTab().getText()).toBe('Clinical', 'Clinical tab should be active');
    expect(ResourceViewerDialog.getClinicalView().isDisplayed()).toBe(true, 'Clinical view should be displayed');
    expect(ResourceViewerDialog.getRawView().isPresent()).toBe(false, 'Raw view should not be present');

    ResourceViewerDialog.getCloseButton().click();
    expect(ResourceViewerDialog.isDisplayed()).toBe(false, 'Resource viewer should be closed');
  });

  it('Display condition resource dialog', () => {
    ResourcePage.getResourceTableRows().get(0).click();
    expect(ResourceViewerDialog.isDisplayed()).toBe(true, 'Resource viewer should be displayed');
    expect(ResourceViewerDialog.getTitle().getText()).toContain('Condition - ', 'Resource viewer should show Condition resource');
    expect(ResourceViewerDialog.getActiveTab().getText()).toBe('Raw', 'Raw tab should be active by default');
    expect(ResourceViewerDialog.getRawView().isDisplayed()).toBe(true, 'Raw view should be displayed');

    ResourceViewerDialog.getClinicalTab().click();
    expect(ResourceViewerDialog.getActiveTab().getText()).toBe('Clinical', 'Clinical tab should be active');
    expect(ResourceViewerDialog.getClinicalView().isDisplayed()).toBe(true, 'Clinical view should be displayed');
    expect(ResourceViewerDialog.getRawView().isPresent()).toBe(false, 'Raw view should not be present');

    ResourceViewerDialog.getCloseButton().click();
    expect(ResourceViewerDialog.isDisplayed()).toBe(false, 'Resource viewer should be closed');
  });

  it ('Logout', () => {
    page.logout();
    browser.wait(browser.ExpectedConditions.urlContains('/auth/realms/endeavour/protocol/openid-connect'));
  });
});
