export class ServicePatientResource {
  serviceId: string;
  systemId: string;
  patientId: string;
  resourceJson: any;

  // UI Cached entries
  recordedDate: Date;
  effectiveDate: Date;
  description: string;
}
