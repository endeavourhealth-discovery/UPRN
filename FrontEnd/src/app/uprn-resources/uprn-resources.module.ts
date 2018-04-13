import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from '@angular/router';
import {DialogsModule, SecurityModule} from 'eds-angular4';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { UPRNResourcesComponent } from './uprn-resources.component';
import {UPRNResourcesService} from './uprn-resources.service';
import {ControlsModule} from 'eds-angular4/dist/controls';

@NgModule({
  imports : [
    BrowserModule,
    FormsModule,
    CommonModule,
    SecurityModule,
    RouterModule,
    NgbModule,
    ControlsModule,
    DialogsModule
  ],
  declarations : [
    UPRNResourcesComponent,
  ],
  providers : [
    UPRNResourcesService,
  ],
  entryComponents: [
  ]
})
export class UPRNResourcesModule { }
