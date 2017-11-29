import { Application } from './app.po';
import {browser} from 'protractor';
import {StopPage} from './stop.po';
import {ResourcePage} from './resources.po';
import {PersonFindDialog} from './personFind.po';
import {ResourceViewerDialog} from './resourceViewer.po';
import {LoginPage} from './login.po';

describe('Logon with permissions', () => {
  let app: Application;

  beforeEach(() => {
    app = new Application();
  });
  it ('should initialize', () => {
    app.initialize();
    browser.wait(() => LoginPage.isDisplayed());
  });

  it ('should login', () => {
    LoginPage.login('e2eTestWithPermission', 'e2eTestPass');
    browser.wait(() => app.isLoaded());
  });

  it ('should load the application', () => {
    expect(app.getTitleText()).toBe('Data Validation', 'Application failed to load');
    expect(StopPage.isDisplayed()).toBe(false, 'Stop page should not be displayed');
  });

  it ('should default to the resource page', () => {
    expect(ResourcePage.isDisplayed()).toBe(true, 'Resource page should display as default');
    expect(ResourcePage.getFindPersonButton().isEnabled()).toBe(true, 'Find Person button should be enabled');
    expect(PersonFindDialog.isDisplayed()).toBe(false, 'Find person dialog should be hidden');
    expect(ResourcePage.getResourceCard().isPresent()).toBe(false, 'Main resource display should be hidden');
  });

  describe('Find and select a person', () => {
    it('should display person find dialog', () => {
      ResourcePage.getFindPersonButton().click();
      expect(PersonFindDialog.isDisplayed()).toBe(true, 'Find Person button should display patient find dialog');
    });

    it ('should search by NHS number', () => {
      PersonFindDialog.search('4444444444');
      expect(PersonFindDialog.getResultsTableRows().count()).toBe(2, 'Test NHS number should match 2 patients');
      expect(PersonFindDialog.getOkButton().isEnabled()).toBe(false, 'OK button should be disabled until patient is selected');
    });

    it ('should show matching patients', () => {
      PersonFindDialog.getResultsTableRows().get(0).click();
      expect(PersonFindDialog.getOkButton().isEnabled()).toBe(true, 'OK button should be enabled once a patient is selected');
    });

    it ('should close the dialog', () => {
      PersonFindDialog.getOkButton().click();
      expect(PersonFindDialog.isDisplayed()).toBe(false, 'Person Find dialog should be hidden when Ok clicked');
    });
  });

  describe('Load and display resources', () => {
    it('should populate resource filters', () => {
      expect(ResourcePage.getResourceCard().isPresent()).toBe(true, 'Resource card should be visible once a person is selected');
      expect(ResourcePage.getResourceTypeDropdown().getText()).toBe('Select', 'No resource types should be selected by default');
      expect(ResourcePage.getResourceTypeDropdownOptions().count()).toBe(18, 'All resource types should be available for selection');
      expect(ResourcePage.getPatientDropdownOptions().count()).toBe(4, 'Test person should have 2 patients (plus All/None options == 4)');
      expect(ResourcePage.getPatientDropdown().getText()).toBe('All', 'All patients should be selected by default');
      expect(ResourcePage.getLoadButton().isEnabled()).toBe(false, 'Load button incorrectly enabled');
    });

    it('should load patient resources', () => {
      ResourcePage.getResourceTypeDropdown().click();
      ResourcePage.getResourceTypeDropdownOptionByText('Condition').click();
      ResourcePage.getLoadButton().click();
      browser.wait(() => ResourcePage.getPatientTable().isDisplayed() && ResourcePage.getResourceTable().isDisplayed());
      expect(ResourcePage.getPatientTableRows().count()).toBe(2, 'Patient resource for both patients should load');
      expect(ResourcePage.getResourceTableRows().count()).toBe(9, 'Condition resource for both patients should load');
    });

    describe('Load and display a patient resource', () => {
      it('should display patient resource dialog', () => {
        ResourcePage.getPatientTableRows().get(0).click();
        expect(ResourceViewerDialog.isDisplayed()).toBe(true, 'Resource viewer should be displayed');
        expect(ResourceViewerDialog.getTitle().getText()).toContain('Patient - ', 'Resource viewer should show Patient resource');
      });

      it('should default to raw view', () => {
        expect(ResourceViewerDialog.getActiveTab().getText()).toBe('Raw', 'Raw tab should be active by default');
        expect(ResourceViewerDialog.getRawView().isDisplayed()).toBe(true, 'Raw view should be displayed');
        expect(ResourceViewerDialog.getClinicalView().isPresent()).toBe(false, 'Clinical view should not be present');
      });

      it('should switch to clinical view', () => {
        ResourceViewerDialog.getClinicalTab().click();
        expect(ResourceViewerDialog.getActiveTab().getText()).toBe('Clinical', 'Clinical tab should be active');
        expect(ResourceViewerDialog.getClinicalView().isDisplayed()).toBe(true, 'Clinical view should be displayed');
        expect(ResourceViewerDialog.getRawView().isPresent()).toBe(false, 'Raw view should not be present');
      });

      it('should close patient resource dialog', () => {
        ResourceViewerDialog.getCloseButton().click();
        expect(ResourceViewerDialog.isDisplayed()).toBe(false, 'Resource viewer should be closed');
      });
    });

    describe('Load and display a condition resource', () => {
      it('should display condition resource dialog', () => {
        ResourcePage.getResourceTableRows().get(0).click();
        expect(ResourceViewerDialog.isDisplayed()).toBe(true, 'Resource viewer should be displayed');
        expect(ResourceViewerDialog.getTitle().getText()).toContain('Condition - ', 'Resource viewer should show Condition resource');
      });

      it('should default to raw view', () => {
        expect(ResourceViewerDialog.getActiveTab().getText()).toBe('Raw', 'Raw tab should be active by default');
        expect(ResourceViewerDialog.getRawView().isDisplayed()).toBe(true, 'Raw view should be displayed');
      });

      it('should switch to clinical view', () => {
        ResourceViewerDialog.getClinicalTab().click();
        expect(ResourceViewerDialog.getActiveTab().getText()).toBe('Clinical', 'Clinical tab should be active');
        expect(ResourceViewerDialog.getClinicalView().isDisplayed()).toBe(true, 'Clinical view should be displayed');
        expect(ResourceViewerDialog.getRawView().isPresent()).toBe(false, 'Raw view should not be present');
      });

      it('should close condition resource dialog', () => {
        ResourceViewerDialog.getCloseButton().click();
        expect(ResourceViewerDialog.isDisplayed()).toBe(false, 'Resource viewer should be closed');
      });
    });
  });

  it ('Logout', () => {
    app.logout();
    browser.wait(browser.ExpectedConditions.urlContains('/auth/realms/endeavour/protocol/openid-connect'));
  });
});
