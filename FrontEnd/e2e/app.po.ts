import {browser, by, element, protractor} from 'protractor';

export class AngularPage {
  navigateTo() {
    browser.waitForAngularEnabled(false);
    browser.get('/');
  }

  expandSidebar() {
    const sidebar = element(by.css('nav.sidebar'));
    browser.actions().mouseMove(sidebar).perform();
  }

  logout() {
    this.expandSidebar();
    element(by.cssContainingText('span.nav-text', 'Logout')).click();
  }

  getTitleText() {
    return element(by.className('title-text')).getText();
  }
}

