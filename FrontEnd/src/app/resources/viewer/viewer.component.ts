import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal, NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-viewer',
  templateUrl: './viewer.component.html',
  styleUrls: ['./viewer.component.css']
})
export class ViewerComponent {
  @Input() title;
  @Input() message;
  @Input() okText;
  @Input() cancelText;

  public static open(modalService: NgbModal,
                     title: string,
                     message: string,
                     okText: string,
                     cancelText: string): NgbModalRef {

    const modalRef = modalService.open(ViewerComponent, { backdrop : 'static', size: 'lg'});
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.okText = okText;
    modalRef.componentInstance.cancelText = cancelText;

    return modalRef;
  }

  constructor(public activeModal: NgbActiveModal) {}
}
