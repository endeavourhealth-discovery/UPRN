import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal, NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-viewer',
  templateUrl: './viewer.component.html',
  styleUrls: ['./viewer.component.css']
})
export class ViewerComponent {
  @Input() title;
  @Input() object;
  @Input() okText;
  @Input() cancelText;

  public static open(modalService: NgbModal,
                     title: string,
                     object: any,
                     okText: string,
                     cancelText: string): NgbModalRef {

    const modalRef = modalService.open(ViewerComponent, { backdrop : 'static', size: 'lg'});
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.object = object;
    modalRef.componentInstance.okText = okText;
    modalRef.componentInstance.cancelText = cancelText;

    return modalRef;
  }

  constructor(public activeModal: NgbActiveModal) {}

  private getKeys() {
    return Object.keys(this.object);
  }
}
