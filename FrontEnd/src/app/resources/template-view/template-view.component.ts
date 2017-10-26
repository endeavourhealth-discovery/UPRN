import {
  AfterViewInit, Compiler, Component, Injector, Input, NgModule, NgModuleRef,
  ViewChild, ViewContainerRef
} from '@angular/core';
import {ServicePatientResource} from '../../models/Resource';
import {ResourcesService} from '../resources.service';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-template-view',
  templateUrl: './template-view.component.html',
  styleUrls: ['./template-view.component.css']
})
export class TemplateViewComponent implements AfterViewInit {
  @Input() resource: ServicePatientResource;
  @ViewChild('dataContainer', {read: ViewContainerRef}) dataContainer: ViewContainerRef;

  constructor(private resourceService: ResourcesService,
              private _compiler: Compiler,
              private _injector: Injector,
              private _m: NgModuleRef<any>) {
  }

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

    const tmpCmp = Component({template: template})(class {
    });
    const tmpModule = NgModule({imports: [CommonModule], declarations: [tmpCmp]})(class {
    });

    this._compiler.compileModuleAndAllComponentsAsync(tmpModule)
      .then((factories) => {
        const f = factories.componentFactories[0];
        const cmpRef = f.create(this._injector, [], null, this._m);
        cmpRef.instance.resource = this.resource;
        this.dataContainer.insert(cmpRef.hostView);
      });
  }
}
