import { BrowserModule } from '@angular/platform-browser';
import { NgModule} from '@angular/core';
import { RouterModule } from '@angular/router';
import { LinqService } from 'ng2-linq';

import {Http, HttpModule, RequestOptions, XHRBackend} from "@angular/http";
import {AppMenuService} from "./app-menu.service";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {KeycloakService} from "eds-angular4/dist/keycloak/keycloak.service";
import {keycloakHttpFactory} from "eds-angular4/dist/keycloak/keycloak.http";
import {AbstractMenuProvider, LayoutModule} from "eds-angular4";
import {ResourcesModule} from "./resources/resources.module";
import {LayoutComponent} from "eds-angular4/dist/layout/layout.component";
import {PersonFindModule} from "./person-find/person-find.module";

@NgModule({
  declarations: [],
  imports: [
    BrowserModule,
    HttpModule,
    LayoutModule,
    PersonFindModule,
    ResourcesModule,
    RouterModule.forRoot(AppMenuService.getRoutes()),
    NgbModule.forRoot()
  ],
  providers: [
    KeycloakService,
    LinqService,
    { provide: Http, useFactory: keycloakHttpFactory, deps: [XHRBackend, RequestOptions, KeycloakService] },
    { provide: AbstractMenuProvider, useClass : AppMenuService }
  ],
  bootstrap: [LayoutComponent]
})
export class AppModule { }
