import {AfterViewInit, Component, Input, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {NgbActiveModal, NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {ServicePatientResource} from '../../models/Resource';
import {ResourcesService} from '../resources.service';

@Component({
  selector: 'app-viewer',
  templateUrl: './viewer.component.html',
  styleUrls: ['./viewer.component.css']
})
export class ViewerComponent implements OnInit {
  @Input() title: string;
  @Input() resource: ServicePatientResource;
  mapDetail: any;

  public static open(modalService: NgbModal,
                     title: string,
                     object: ServicePatientResource): NgbModalRef {

    const modalRef = modalService.open(ViewerComponent, { backdrop : 'static', size: 'lg'});
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.resource = object;

    return modalRef;
  }

  constructor(public activeModal: NgbActiveModal, private resourcesService: ResourcesService) {}

  ngOnInit() {
    this.resolveReferences(this.resource.resourceJson)
  }

  private resolveReferences(object: any) {
    if (this.isPrimitive(object))
      return;

    if (this.isUnresolvedReference(object)) {
      this.loadReferenceDisplay(object);
    } else if (this.isObject(object)) {
      for (const key of Object.keys(object)) {
        const value = object[key];
        this.resolveReferences(value);
      }
    } else if (this.isArray(object)) {
      for (const arrayItem of object) {
        this.resolveReferences(arrayItem);
      }
    }
  }

  private loadReferenceDisplay(value: any) {
    if (value.display)
      return;

    const vm = this;
    value.display = 'Resolving...';

    vm.resourcesService.getReferenceDescription(vm.resource.serviceId, value.reference)
      .subscribe(
        (result) => value.display = result,
        (error) => value.display = 'Not found'
      );
  }

  public isUnresolvedReference(value: any): boolean {
    return value['reference'] && !value['display'];
  }

  public isObject(value: any): boolean {
    return !Array.isArray(value) && typeof value === 'object';
  }

  public isArray(value: string): boolean {
    return Array.isArray(value);
  }

  public isPrimitive(value: any): boolean {
    return !Array.isArray(value) && typeof value !== 'object';
  }

  public showMap(mapDetail: any) {
    this.mapDetail = mapDetail;
  }
}
