insert into config (app_id, config_id, config_data)
values (
    'eds-data-validation',
  'Template-Patient',
  '
<div class="container">
<form>
  <div class="row">
    <div class="col-md-6">
      <div *ngFor="let identifier of resource.resourceJson.identifier">
        <div class="form-group" *ngIf="identifier.system==''http://endeavourhealth.org/fhir/id/v2-local-patient-id/barts-mrn''" >
          <label for="MRN">MRN Number</label>
          <input id="MRN" class="form-control" type="text" disabled value="{{identifier.value}}">
        </div>
        <div class="form-group" *ngIf="identifier.system==''http://fhir.nhs.net/Id/nhs-number''" >
          <label for="NHS">NHS Number</label>
          <input id="NHS" class="form-control" type="text" disabled value="{{identifier.value}}">
        </div>
      </div>
      <div class="form-group">
        <label for="Title">Title</label>
        <input id="Title" class="form-control" type="text" disabled value="{{resource.resourceJson.name[0].prefix}}">
      </div>
      <div class="form-group">
        <label for="Given">Given Name</label>
        <input id="Given" class="form-control" type="text" disabled value="{{resource.resourceJson.name[0].given}}">
      </div>
      <div class="form-group">
        <label for="Family">Family Name</label>
        <input id="Family" class="form-control" type="text" disabled value="{{resource.resourceJson.name[0].family}}">
      </div>
      <div class="form-group" *ngIf="resource.resourceJson.maritalStatus">
        <label for="Marital">Marital Status</label>
        <input id="Marital" class="form-control" type="text" disabled value="{{resource.resourceJson.maritalStatus.text}}">
      </div>
    </div>

    <div class="col-md-6">
      <div class="form-group">
        <label for="Gender">Gender</label>
        <input id="Gender" class="form-control" type="text" disabled value="{{resource.resourceJson.gender}}">
      </div>
      <div class="form-group">
        <label for="DOB">D.O.B.</label>
        <input id="DOB" class="form-control" type="text" disabled value="{{resource.resourceJson.birthDate}}">
      </div>
      <div *ngFor="let extension of resource.resourceJson.extension">
        <div class="form-group" *ngIf="extension.url==''http://endeavourhealth.org/fhir/StructureDefinition/primarycare-ethnic-category-extension''" >
          <label for="Ethnicity">Ethnicity</label>
          <input id="Ethnicity" class="form-control" type="text" disabled value="{{extension.valueCodeableConcept.coding[0].display}}">
        </div>
      </div>
      <div *ngFor="let careProvider of resource.resourceJson.careProvider">
        <div class="form-group" *ngIf="careProvider.reference.startsWith(''Organization'')" >
          <label for="CarerOrg">Caring organisation</label>
          <input id="CarerOrg" class="form-control" type="text" disabled value="{{careProvider.display}}">
        </div>
        <div class="form-group" *ngIf="careProvider.reference.startsWith(''Practitioner'')" >
          <label for="CarerPrac">Caring practitioner</label>
          <input id="CarerPrac" class="form-control" type="text" disabled value="{{careProvider.display}}">
        </div>
      </div>
    </div>
  </div>
</form>
</div>
');

insert into config (app_id, config_id, config_data)
values (
  'eds-data-validation',
  'Template-Encounter',
  '<div class="container">
<form>
  <div class="row">
    <div class="col-md-6">
      <div class="form-group">
        <label for="PatientType">Patient type</label>
        <input id="PatientType" class="form-control" type="text" disabled value="{{resource.resourceJson.class}}">
      </div>
    </div>

    <div class="col-md-6">
    </div>
  </div>
</form>
</div>');

insert into config (app_id, config_id, config_data)
values (
  'eds-data-validation',
  'Template-EpisodeOfCare',
  '<div class="container">
<form>
  <div class="row">
    <div class="col-md-6">
      <div class="form-group">
        <label for="Registered">Registered date</label>
        <input id="Registered" class="form-control" type="text" disabled value="{{resource.resourceJson.period?.start}}">
      </div>
      <div *ngFor="let identifier of resource.resourceJson.identifier">
        <div class="form-group" *ngIf="identifier.system==''http://endeavourhealth.org/fhir/id/v2-local-episode-id/barts-fin''" >
          <label for="FIN">Financial No.</label>
          <input id="FIN" class="form-control" type="text" disabled value="{{identifier.value}}">
        </div>
      </div>
    </div>

    <div class="col-md-6">
      <div class="form-group">
        <label for="Discharged">Discharged date</label>
        <input id="Discharged" class="form-control" type="text" disabled value="{{resource.resourceJson.period?.end}}">
      </div>
    </div>
  </div>
</form>
</div>');

insert into config (app_id, config_id, config_data)
values (
  'eds-data-validation',
  'Template-Condition',
  '<div class="container">
<form>
  <div class="row">
    <div class="col-md-6">
      <div class="form-group">
        <label for="Code">Coding Code</label>
        <input id="Code" class="form-control" type="text" disabled value="{{resource.resourceJson.code.coding[0].code}}">
      </div>
      <div class="form-group">
        <label for="Display">Coding Display</label>
        <input id="Display" class="form-control" type="text" disabled value="{{resource.resourceJson.code.coding[0].display}}">
      </div>
    </div>

    <div class="col-md-6">
      <div class="form-group">
        <label for="Date">Effective Date</label>
        <input id="Date" class="form-control" type="text" disabled value="{{resource.resourceJson.onsetDateTime | date:''dd/MM/y''}}">
      </div>
      <div class="form-group">
        <label for="Text">Code Text</label>
        <input id="Text" class="form-control" type="text" disabled value="{{resource.resourceJson.code.text}}">
      </div>
      <div class="form-group">
        <label for="Category">Category</label>
        <input id="Category" class="form-control" type="text" disabled value="{{resource.resourceJson.category.coding[0].code}}">
      </div>
    </div>
  </div>
</form>
</div>');

insert into config (app_id, config_id, config_data)
values (
  'eds-data-validation',
  'Template-Procedure',
  '<div class="container">
<form>
  <div class="row">
    <div class="col-md-6">
      <div class="form-group">
        <label for="Code">Coding Code</label>
        <input id="Code" class="form-control" type="text" disabled value="{{resource.resourceJson.code.coding[0].code}}">
      </div>
      <div class="form-group">
        <label for="Display">Coding Display</label>
        <input id="Display" class="form-control" type="text" disabled value="{{resource.resourceJson.code.coding[0].display}}">
      </div>
    </div>

    <div class="col-md-6">
      <div class="form-group">
        <label for="Text">Code Text</label>
        <input id="Text" class="form-control" type="text" disabled value="{{resource.resourceJson.code.text}}">
      </div>
    </div>
  </div>
</form>
</div>');