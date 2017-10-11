import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MockSecurityService} from "./mock.security.service";
import {MockResourcesService} from "./mock.resources.service";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [],
  providers : [
    MockSecurityService,
    MockResourcesService
  ]
})
export class MocksModule { }
