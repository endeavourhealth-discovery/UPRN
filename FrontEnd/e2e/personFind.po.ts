import {by, element} from 'protractor';

export class PersonFindDialog {
  static isDisplayed() {
    const dialog = element(by.css('app-person-find-dialog'));
    return (dialog != null && dialog.isPresent());
  }

  static getSearchField() {
    return element(by.name('searchData'));
  }

  static getSearchButton() {
    return element(by.id('searchButton'));
  }

  static getResultTable() {
    return element(by.id('results'));
  }

  static getResultsTableRows() {
    return element(by.id('results')).all(by.css('tr'));
  }

  static getOkButton() {
    return element(by.name('Ok'));
  }

  static getCancelButton() {
    return element(by.name('Cancel'));
  }
}
