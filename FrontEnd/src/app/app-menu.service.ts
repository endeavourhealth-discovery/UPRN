import {Injectable} from '@angular/core';
import {MenuOption} from 'eds-angular4/dist/layout/models/MenuOption';
import {AbstractMenuProvider} from 'eds-angular4';
import {Routes} from '@angular/router';
import {ResourcesComponent} from './resources/resources.component';

@Injectable()
export class AppMenuService implements  AbstractMenuProvider {
  static getRoutes(): Routes {
    return [
      { path: '', redirectTo : 'resources', pathMatch: 'full' },
      { path: 'resources', component: ResourcesComponent }
    ]
  }

  getClientId(): string {
    return 'data-validator';
  }
  getApplicationTitle(): string {
    return 'Data Assurance';
  }
  getMenuOptions(): MenuOption[] {
    return [
      {caption: 'Resources', state: 'resources', icon: 'fa fa-archive', role: 'data-validator:resources'},
    ];
  }
}
