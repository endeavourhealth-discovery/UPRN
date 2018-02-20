import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from '@angular/router';
import {DialogsModule, SecurityModule} from 'eds-angular4';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ResourcesComponent } from './resources.component';
import {ResourcesService} from './resources.service';
import { ViewerComponent } from './viewer/viewer.component';
import { RawViewComponent } from './raw-view/raw-view.component';
import {TemplateViewComponent} from './template-view/template-view.component';
import {ControlsModule} from 'eds-angular4/dist/controls';
import {MapViewComponent} from './map-view/map-view.component';
import { MapDetailComponent } from './map-detail/map-detail.component';

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
    RawViewComponent,
    TemplateViewComponent,
    MapViewComponent,
    MapDetailComponent
  ],
  providers : [
    ResourcesService,
  ],
  entryComponents: [
    ViewerComponent
  ]
})
export class ResourcesModule { }
