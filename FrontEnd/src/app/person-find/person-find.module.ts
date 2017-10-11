import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {PersonFindService} from "./person-find.service";
import { PersonFindDialogComponent } from './person-find-dialog/person-find-dialog.component';
import {FormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    FormsModule
  ],
  declarations: [
    PersonFindDialogComponent
  ],
  entryComponents: [
    PersonFindDialogComponent
  ],
  providers: [
    PersonFindService
  ]
})
export class PersonFindModule { }
