import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from '@angular/router';
import {ControlsModule, DialogsModule, SecurityModule} from 'eds-angular4';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ResourcesComponent } from './resources.component';
import {ResourcesService} from './resources.service';
import {PipesModule} from '../pipes/pipes.module';
import { ViewerComponent } from './viewer/viewer.component';

@NgModule({
  imports : [
    BrowserModule,
    FormsModule,
    CommonModule,
    PipesModule,
    SecurityModule,
    RouterModule,
    NgbModule,
    ControlsModule,
    DialogsModule
  ],
  declarations : [
    ResourcesComponent,
    ViewerComponent
  ],
  providers : [
    ResourcesService,
  ],
  entryComponents: [
    ViewerComponent
  ]
})
export class ResourcesModule { }
