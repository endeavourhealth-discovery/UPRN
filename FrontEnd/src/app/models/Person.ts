import {ResourceId} from './ResourceId';

export class Person {
  nhsNumber: string;
  name: string;
  patientCount: number;

  // Person may be single/specific patient (localId search result with no NhsNumber)
  patientId: ResourceId;
}
