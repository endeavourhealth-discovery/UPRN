import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {ResourceFieldMapping} from '../../models/ResourceFieldMapping';
import {ResourcesService} from '../resources.service';
import {ServicePatientResource} from '../../models/Resource';
import {LoggerService} from 'eds-angular4';

@Component({
  selector: 'app-map-view',
  templateUrl: './map-view.component.html',
  styleUrls: ['./map-view.component.css']
})
export class MapViewComponent implements AfterViewInit {
  @Input() resource: ServicePatientResource;
  fieldMappings: ResourceFieldMapping[];

  constructor(protected logger: LoggerService, private resourcesService: ResourcesService) { }

  ngAfterViewInit(): void {
    this.loadFieldMappings();
  }

  loadFieldMappings() {
    const vm = this;
    vm.resourcesService.getFieldMappings(vm.resource.serviceId, vm.resource.resourceJson.resourceType, vm.resource.resourceJson.id)
      .subscribe(
        (result) => vm.fieldMappings = result,
        (error) => vm.logger.error(error)
      );
  }

  getFilename(fullPath: string) {
    let lastSlash = fullPath.lastIndexOf('\\');
    if (lastSlash >= 0)
      return fullPath.substr(lastSlash + 1);

    lastSlash = fullPath.lastIndexOf('/');
    if (lastSlash >= 0)
      return fullPath.substr(lastSlash + 1);

    return fullPath;
  }

}
