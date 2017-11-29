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
    return element(by.id('resultTable'));
  }

  static getResultsTableRows() {
    return PersonFindDialog.getResultTable().all(by.tagName('tbody tr'));
  }

  static getOkButton() {
    return element(by.name('Ok'));
  }

  static getCancelButton() {
    return element(by.name('Cancel'));
  }
}
