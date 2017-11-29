import {by, element} from 'protractor';

export class ResourceViewerDialog {
  static isDisplayed() {
    const dialog = element(by.css('app-viewer'));
    return (dialog != null && dialog.isPresent());
  }

  static getTitle() {
    return element(by.css('h4.modal-title'));
  }

  static getCloseButton() {
    return element(by.name('close'));
  }

  static getActiveTab() {
    return element(by.css('a.nav-link.active'));
  }

  static getRawTab() {
    return element(by.cssContainingText('a.nav-link', 'Raw'));
  }

  static getRawView() {
    // Ignore app-raw-view children of app-raw-view (its a nested component)
    // Top level is a child of a div
    return element(by.css('div > app-raw-view'));
  }

  static getClinicalTab() {
    return element(by.cssContainingText('a.nav-link', 'Clinical'));
  }

  static getClinicalView() {
    return element(by.css('app-template-view'));
  }

}
