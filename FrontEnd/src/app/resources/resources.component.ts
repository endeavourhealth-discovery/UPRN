import { Component, OnInit } from '@angular/core';
import {ResourcesService} from "./resources.service";
import {SecurityService} from "eds-angular4";
import {Access} from "eds-angular4/dist/security/models/Access";

@Component({
  selector: 'resources',
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.css']
})
export class ResourcesComponent implements OnInit {


  constructor(protected security : SecurityService, protected service : ResourcesService) {
  }

  ngOnInit() {
  }

  hasPermission(client, role : string) {
    if (role == null || role == '')
      return true;

    let clientAccess : Access = this.security.currentUser.clientAccess[client];

    if (clientAccess && clientAccess.roles)
      return clientAccess.roles.includes(role);

    return false;
  }

  openWindow(url : string) {
    window.open(url);
  }
}
