import { Application } from './app.po';
import {$, browser, by, element} from 'protractor';
import {StopPage} from './stop.po';

describe('Logon with no permissions', () => {
  let page: Application;

  beforeEach(() => {
    page = new Application();
  });

  it ('Initialize', () => {
    page.initialize();

    // Wait for login.
    browser.wait(browser.ExpectedConditions.urlContains('/auth/realms/endeavour/protocol/openid-connect'));
  });

  it ('Perform no-access login', () => {
    element(by.name('username')).sendKeys('e2etest');
    element(by.name('password')).sendKeys('e2eTestPass');
    element(by.name('login')).click();

    // Wait for main app page
    browser.wait(() => $('#content').isPresent());
  });

  it ('Check app loaded', () => {
    expect(page.getTitleText()).toBe('Data Validation', 'Application failed to load');
  });

  it ('Check permission denied', () => {
    expect(StopPage.isDisplayed()).toBe(true, 'Stop page should be displayed for user with no permissions');
  });

  it ('Logout', () => {
    page.logout();
    browser.wait(browser.ExpectedConditions.urlContains('/auth/realms/endeavour/protocol/openid-connect'));
  });
});
