import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CuiDate, CuiDateTime } from './cui';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    CuiDate,
    CuiDateTime
  ],
  exports : [
    CuiDate,
    CuiDateTime
  ]
})
export class PipesModule { }
