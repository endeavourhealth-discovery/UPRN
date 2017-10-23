import { AfterViewInit, Component, ElementRef, Input, ViewChild} from '@angular/core';
import {ServicePatientResource} from '../../models/Resource';
import {ResourcesService} from '../resources.service';
import {isPrimitive} from 'util';

@Component({
  selector: 'app-template-view',
  templateUrl: './template-view.component.html',
  styleUrls: ['./template-view.component.css']
})
export class TemplateViewComponent implements AfterViewInit {
  @Input() resource: ServicePatientResource;
  @ViewChild('dataContainer') dataContainer: ElementRef;

  constructor(private resourceService: ResourcesService) { }

  ngAfterViewInit() {
    this.loadTemplate();
  }

  loadTemplate() {
    const vm = this;
    vm.resourceService.getTemplate(this.resource.resourceJson.resourceType)
      .subscribe(
        (result) => vm.buildView(result),
        (error) => console.log(error)
      );
  }

  private buildView(template: string) {
    if (!template || template === '')
      this.dataContainer.nativeElement.innerHTML = '<h2>Not template configured for resource type ' +
        this.resource.resourceJson.resourceType + '</h2>';
    else {
      let html = this.processTemplate(template, this.resource.resourceJson);
      html = html.replace(new RegExp('{{[^]+}}', 'g'), '');
      this.dataContainer.nativeElement.innerHTML = html;
    }
  }

  private processTemplate(template: string, object: any, prefix: string = ''): string {
    if (isPrimitive(object))
      return template.replace(new RegExp('{{' + prefix + '}}', 'g'), object);

    for (const key of Object.keys(object)) {
      const value = object[key];
      const field = '{{' + prefix + key + '}}';
      if (this.isPrimitive(value))
        template = template.replace(new RegExp(field, 'g'), value);
      if (this.isObject(value))
        template = this.processTemplate(template, value, prefix + key + '.');
      if (this.isArray(value))
        template = this.processArray(template, value, prefix + key);
    }

    return template;
  }

  private processArray(template: string, objects: any[], key: string) {
    // get sub-template string
    const regex = new RegExp('#' + key + ':start[^]+#' + key + ':end', 'g');
    const subTemplate = template.match(regex);

    if (subTemplate == null)
      return template;

    let subTemplateHtml = subTemplate[0]
      .substring(key.length + 7);

    subTemplateHtml = subTemplateHtml.substring(0, subTemplateHtml.length - (key.length + 5));

    let result = '';

    for (const object of objects) {
      result += this.processTemplate(subTemplateHtml, object, key + '.');
    }

    template = template.replace(regex, result);

    return template;
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
}
