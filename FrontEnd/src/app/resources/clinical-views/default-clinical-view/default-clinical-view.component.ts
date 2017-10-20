import {AfterContentInit, AfterViewInit, ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {ServicePatientResource} from '../../../models/Resource';

@Component({
  selector: 'app-default-clinical-view',
  templateUrl: './default-clinical-view.component.html',
  styleUrls: ['./default-clinical-view.component.css']
})
export class DefaultClinicalViewComponent implements AfterViewInit {
  @Input() resource: ServicePatientResource;
  protected resourceJson: any = 'hello';

  constructor(private cd: ChangeDetectorRef) { }

  ngAfterViewInit() {
    this.cd.detectChanges();
    // setTimeout(() => this.resourceJson = this.resource, 5000);
  }

}
