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

  static getResourceTypeDropdownOptions() {
    return element(by.name('resourceFilter')).all(by.css('div.dropdown-item'));
  }

  static getPatientDropdownOptions() {
    return element(by.name('patientFilter')).all(by.css('div.dropdown-item'));
  }
}

