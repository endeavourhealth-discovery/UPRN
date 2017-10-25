import { Injectable } from '@angular/core';
import {AbstractMockObservable} from './mock.observable';

@Injectable()
export class MockResourcesService extends AbstractMockObservable {

  getConfig() {
    this._fakeContent = [{
      'name': 'Utilities',
      'icon': 'fa fa-wrench',
      'description': 'Summary description of what Utilities are...',
      'client': 'eds-compass',
      'items': [{
        'name': 'Prevalence and Incidence',
        'icon': 'fa fa-bar-chart',
        'url': '/compass/app/utilities/prev-inc',
        'role': 'eds-compass:utility-prev-inc'
      }
      ]
    },
      {
        'name': 'Compass',
        'icon': 'fa fa-compass',
        'description': 'Summary description of what Compass is...',
        'client': 'eds-compass',
        'items': [{
          'name': 'Dashboard',
          'icon': 'fa fa-dashboard',
          'url': '/compass/app/dashboard',
          'role': 'eds-compass:dashboard'
        }, {
          'name': 'Library',
          'icon': 'fa fa-book',
          'url': '/compass/app/library',
          'role': 'eds-compass:library'
        }, {
          'name': 'Utilities',
          'icon': 'fa fa-wrench',
          'url': '/compass/app/utilities',
          'role': 'eds-compass:utility'
        }
        ]
      },
      {
        'name': 'Data Checker',
        'icon': 'fa fa-search',
        'description': 'Data integrity checker application',
        'client': 'eds-data-checker',
        'items': [{
          'name': 'Patient Explorer',
          'icon': 'fa fa-user',
          'url': '/dataChecker/app/patientExplorer',
          'role': 'eds-data-checker:patient-explorer'
        }, {
          'name': 'Validation Reports',
          'icon': 'fa fa-file',
          'url': '/dataChecker/app/validationReports',
          'role': 'eds-data-checker:validation-reports'
        }
        ]
      }
    ];

    return this;
  }

  getResourceTypes() {
    this._fakeContent = ['Patient', 'Condition'];

    return this;
  }

  getTemplate(resourceType: string) {
    this._fakeContent = '<h1>Test Template</h1>';
    return this;
  }
}
