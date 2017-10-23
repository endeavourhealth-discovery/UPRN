import {Component, Input} from '@angular/core';
import {NgbActiveModal, NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {ServicePatientResource} from '../../models/Resource';

@Component({
  selector: 'app-viewer',
  templateUrl: './viewer.component.html',
  styleUrls: ['./viewer.component.css']
})
export class ViewerComponent {
  @Input() title: string;
  @Input() resource: ServicePatientResource;
  @Input() okText: string;
  @Input() cancelText: string;

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

  constructor(public activeModal: NgbActiveModal) {}
}
