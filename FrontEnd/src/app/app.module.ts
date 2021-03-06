import { BrowserModule } from '@angular/platform-browser';
import { NgModule} from '@angular/core';
import { RouterModule } from '@angular/router';

import {Http, HttpModule, RequestOptions, XHRBackend} from '@angular/http';
import {AppMenuService} from './app-menu.service';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {KeycloakService} from 'eds-angular4/dist/keycloak/keycloak.service';
import {keycloakHttpFactory} from 'eds-angular4/dist/keycloak/keycloak.http';
import {AbstractMenuProvider, LayoutModule, LoggerModule} from 'eds-angular4';
import {ResourcesModule} from './resources/resources.module';
import {UPRNResourcesModule} from './uprn-resources/uprn-resources.module';
import {LayoutComponent} from 'eds-angular4/dist/layout/layout.component';
import {PersonFindModule} from './person-find/person-find.module';
import {ToastModule} from 'ng2-toastr/ng2-toastr';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {StandardReportsModule} from './standard-reports/standard-reports.module';
import {FolderModule} from 'eds-angular4/dist/folder';
import {CuiControlsModule} from 'eds-angular4/dist/cuicontrols/cuicontrols.module';

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpModule,
    LayoutModule,
    LoggerModule,
    FolderModule,
    CuiControlsModule,

    PersonFindModule,
    ResourcesModule,
    UPRNResourcesModule,
    StandardReportsModule,

    RouterModule.forRoot(AppMenuService.getRoutes(), {useHash: true}),
    NgbModule.forRoot(),
    ToastModule.forRoot()
  ],
  providers: [
    KeycloakService,
    { provide: Http, useFactory: keycloakHttpFactory, deps: [XHRBackend, RequestOptions, KeycloakService] },
    { provide: AbstractMenuProvider, useClass : AppMenuService }
  ],
  entryComponents: [LayoutComponent],
  bootstrap: [LayoutComponent]
})
export class AppModule { }
