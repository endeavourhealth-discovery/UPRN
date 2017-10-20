import {
  AfterContentInit,
  AfterViewInit, Component, ComponentFactoryResolver, Input, OnInit, ViewChild,
  ViewContainerRef
} from '@angular/core';
import {NgbActiveModal, NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {ServicePatientResource} from '../../models/Resource';
import {DefaultClinicalViewComponent} from '../clinical-views/default-clinical-view/default-clinical-view.component';

@Component({
  selector: 'app-viewer',
  templateUrl: './viewer.component.html',
  styleUrls: ['./viewer.component.css']
})
export class ViewerComponent implements AfterViewInit {
  @Input() title: string;
  @Input() resource: ServicePatientResource;
  @Input() okText: string;
  @Input() cancelText: string;
  @ViewChild('clinicalComponent', { read: ViewContainerRef }) clinicalComponent: any;

  public static open(modalService: NgbModal,
                     title: string,
                     object: ServicePatientResource,
                     okText: string,
                     cancelText: string): NgbModalRef {

    const modalRef = modalService.open(ViewerComponent, { backdrop : 'static', size: 'lg'});
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.resource = object;
    modalRef.componentInstance.okText = okText;
    modalRef.componentInstance.cancelText = cancelText;

    return modalRef;
  }

  constructor(private componentResolver: ComponentFactoryResolver, public activeModal: NgbActiveModal) {}

  ngAfterViewInit(): void {
    // const component: any = this.getComponentClass(this.resource.resourceJson.resourceType);
    // const factory = this.componentResolver.resolveComponentFactory(component);
		//
    // const componentRef = this.clinicalComponent.createComponent(factory);
    // componentRef.instance.resource = this.resource;
  }

  private getComponentClass(resourceType: string) {
    switch (resourceType) {
      default: return DefaultClinicalViewComponent;
    }
  }

  private getKeys() {
    return Object.keys(this.resource);
  }
}
