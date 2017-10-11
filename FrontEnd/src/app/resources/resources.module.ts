import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from "@angular/router";
import {SecurityModule} from "eds-angular4";
import {FormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { ResourcesComponent } from './resources.component';
import {ResourcesService} from "./resources.service";

@NgModule({
  imports : [
    BrowserModule,
    FormsModule,
    CommonModule,
    SecurityModule,
    RouterModule,
    NgbModule
  ],
  declarations : [
    ResourcesComponent
  ],
  providers : [
    ResourcesService,
  ]
})
export class ResourcesModule { }
