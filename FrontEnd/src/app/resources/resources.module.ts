import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from '@angular/router';
import {ControlsModule, DialogsModule, SecurityModule} from 'eds-angular4';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ResourcesComponent } from './resources.component';
import {ResourcesService} from './resources.service';
import { ViewerComponent } from './viewer/viewer.component';
import { ObjectViewerComponent } from './object-viewer/object-viewer.component';
import { DefaultClinicalViewComponent } from './clinical-views/default-clinical-view/default-clinical-view.component';

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
    ResourcesComponent,
    ViewerComponent,
    ObjectViewerComponent,
    DefaultClinicalViewComponent
  ],
  providers : [
    ResourcesService,
  ],
  entryComponents: [
    ViewerComponent,
    DefaultClinicalViewComponent
  ]
})
export class ResourcesModule { }
