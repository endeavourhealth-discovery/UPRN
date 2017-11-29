import {browser, by, element} from 'protractor';

export class ResourcePage {
  static isDisplayed() {
    return browser.getCurrentUrl().then(function(actualUrl) {
      return actualUrl.endsWith('/#/resources');
    });
  }

  static getFindPersonButton() {
    return element(by.id('findPerson'));
  }

  static getResourceCard() {
    return element(by.id('resourceCard'));
  }

  static getResourceTypeDropdown() {
    return element(by.name('resourceFilter'));
  }

  static getResourceTypeDropdownOptions() {
    return ResourcePage.getResourceTypeDropdown().all(by.css('div.dropdown-item'));
  }

  static getResourceTypeDropdownOptionByText(text: string) {
    return ResourcePage.getResourceTypeDropdown().element(by.cssContainingText('div.dropdown-item', text));
  }

  static getPatientDropdown() {
    return element(by.name('patientFilter'));
  }

  static getPatientDropdownOptions() {
    return ResourcePage.getPatientDropdown().all(by.css('div.dropdown-item'));
  }

  static getLoadButton() {
    return element(by.id('load'));
  }
}

