import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ResourcesService} from '../resources.service';
import {ServicePatientResource} from '../../models/Resource';
import {ResourceFieldMapping} from '../../models/ResourceFieldMapping';

@Component({
  selector: 'app-raw-view',
  templateUrl: './raw-view.component.html',
  styleUrls: ['./raw-view.component.css']
})
export class RawViewComponent implements OnInit {
  @Input() resource: ServicePatientResource;
  @Input() node: any;
  @Input() path = '';
  @Output() onShowMap = new EventEmitter<any>();
  fieldMaps: ResourceFieldMapping[];

  constructor(private resourceService: ResourcesService) { }

  ngOnInit() {
  }

  public getKeys(): string[] {
    return Object.keys(this.node);
  }

  public getValue(property: string): any {
    return this.node[property];
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

  public getSource(property: string) {
    if (this.path === '')
      return property;
    else
      return this.path + '.' + property;
  }

  showMapping(node: any) {
    if (!this.fieldMaps) {
      this.fieldMaps = [{ value: 'Loading...'} as ResourceFieldMapping];
      const vm = this;
      vm.resourceService.getFieldMappingsForField(vm.resource.serviceId, vm.resource.resourceJson.resourceType, vm.resource.resourceJson.id, vm.path)
        .subscribe(
          (result) => {
            vm.fieldMaps = result;
            vm.showMap({path: vm.path, maps: result});
          }
        );
    } else {
      this.showMap({path: this.path, maps: this.fieldMaps})
    }
  }

  showMap(mapEvent: any) {
    this.onShowMap.emit(mapEvent);
  }
}
