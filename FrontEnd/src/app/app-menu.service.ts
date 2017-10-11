import {Injectable} from '@angular/core';
import {MenuOption} from 'eds-angular4/dist/layout/models/MenuOption';
import {AbstractMenuProvider} from 'eds-angular4';
import {Routes} from '@angular/router';
import {ResourcesComponent} from './resources/resources.component';

@Injectable()
export class AppMenuService implements  AbstractMenuProvider {
  static getRoutes(): Routes {
    return [
      { path: '', redirectTo : 'main', pathMatch: 'full' },
      { path: 'main', component: ResourcesComponent }
    ]
  }

  getClientId(): string {
    return 'eds-data-checker';
  }
  getApplicationTitle(): string {
    return 'Data Validation';
  }
  getMenuOptions(): MenuOption[] {
    return [
      {caption: 'Main', state: 'main', icon: 'fa fa-hospital-o', role: 'eds-data-checker:patient-explorer'},
    ];
  }
}
