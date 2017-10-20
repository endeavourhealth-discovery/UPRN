import {ResourceId} from './ResourceId';

export class Patient {
  id: ResourceId;
  patientName: string;
  dob: string;

  serviceName = 'Loading...';
  systemName = 'Loading...';
  name: string;
}
