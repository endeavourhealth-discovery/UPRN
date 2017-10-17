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

  private getKeys(): string[] {
    return Object.keys(this.node);
  }

  private getValue(property: string): any {
    return this.node[property];
  }

  private isObject(value: any): boolean {
    return !Array.isArray(value) && typeof value === 'object';
  }

  private isArray(value: string): boolean {
    return Array.isArray(value);
  }

  private isPrimitive(value: any): boolean {
    return !Array.isArray(value) && typeof value !== 'object';
  }
}
