import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-object-viewer',
  templateUrl: './object-viewer.component.html',
  styleUrls: ['./object-viewer.component.css']
})
export class ObjectViewerComponent implements OnInit {
  @Input() node: any;

  constructor() { }

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
}
