use config;

delete from config where config_id = 'Template-Patient' and app_id = 'eds-data-validation';
insert into config (app_id, config_id, config_data)
values (
  'eds-data-validation',
  'Template-Patient',
  '<div class="container">
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
       <div class="form-group">
         <label for="Address">Address</label>
         <input id="Address" class="form-control" type="text" disabled value="{{resource.resourceJson.address[0].text}}">
       </div>
       <div class="form-group" *ngIf="resource.resourceJson.maritalStatus">
         <label for="Marital">Marital Status</label>
         <input id="Marital" class="form-control" type="text" disabled value="{{resource.resourceJson.maritalStatus.coding[0].display}}">
       </div>
     </div>
      <div class="col-md-6">
       <div class="form-group">
         <label for="Gender">Gender</label>
         <input id="Gender" class="form-control" type="text" disabled value="{{resource.resourceJson.gender}}">
       </div>
       <div class="form-group">
         <label for="DOB">D.O.B.</label>
         <input id="DOB" class="form-control" type="text" disabled value="{{resource.resourceJson.birthDate | date:''dd/MM/y''}}">
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
 </div>');

delete from config where config_id = 'Template-EpisodeOfCare' and app_id = 'eds-data-validation';
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
        <input id="Registered" class="form-control" type="text" disabled value="{{resource.resourceJson.period?.start | date:''dd/MM/y''}}">
      </div>
      <div class="form-group">
        <label for="CareManager">Care Manager</label>
        <input id="CareManager" class="form-control" type="text" disabled value="{{resource.resourceJson.careManager?.display}}">
      </div>
       <div *ngFor="let extension of resource.resourceJson.extension">
         <div class="form-group" *ngIf="extension.url==''http://endeavourhealth.org/fhir/StructureDefinition/primarycare-patient-registration-type-extension''" >
          <label for="RegType">Registration Type</label>
        <input id="RegType" class="form-control" type="text" disabled value="{{extension.valueCoding.display}}">
         </div>
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
        <input id="Discharged" class="form-control" type="text" disabled value="{{resource.resourceJson.period?.end  | date:''dd/MM/y''}}">
      </div>
      <div class="form-group">
        <label for="CareOrg">Organisation</label>
        <input id="CareOrg" class="form-control" type="text" disabled value="{{resource.resourceJson.managingOrganization?.display}}">
      </div>
      <div class="form-group">
        <label for="Status">Status</label>
        <input id="Status" class="form-control" type="text" disabled value="{{resource.resourceJson.status}}">
      </div>
    </div>
  </div>
</form>
</div>');

delete from config where config_id = 'Template-Condition' and app_id = 'eds-data-validation';
insert into config (app_id, config_id, config_data)
values (
  'eds-data-validation',
  'Template-Condition',
  '<div class="container">
<form>
  <div class="row">
    <div class="col-md-6">
      <div class="form-group">
        <label for="Display">Display Term</label>
        <input id="Display" class="form-control" type="text" disabled value="{{resource.resourceJson.code.coding[0].display}}">
      </div>
      <div class="form-group">
        <label for="Date">Effective Date</label>
        <input id="Date" class="form-control" type="text" disabled value="{{resource.resourceJson.onsetDateTime | date:''dd/MM/y''}}">
      </div>
	  <div class="form-group">
        <label for="Recorder">Recorded By</label>
        <input id="Recorder" class="form-control" type="text" disabled value="{{resource.resourceJson.asserter?.display}}">
      </div>
    </div>
    <div class="col-md-6">
	  <div class="form-group">
        <label for="Code">Code</label>
        <input id="Code" class="form-control" type="text" disabled value="{{resource.resourceJson.code.coding[0].code}}">
      </div>
      <div class="form-group">
        <label for="Category">Category</label>
        <input id="Category" class="form-control" type="text" disabled value="{{resource.resourceJson.category.coding[0].code}}">
      </div>
      <div class="form-group">
        <label for="Text">Comments</label>
        <input id="Text" class="form-control" type="text" disabled value="{{resource.resourceJson.code.text}}">
      </div>
    </div>
  </div>
</form>
</div>');

delete from config where config_id = 'Template-Procedure' and app_id = 'eds-data-validation';
insert into config (app_id, config_id, config_data)
values (
  'eds-data-validation',
  'Template-Procedure',
  '<div class="container">
 <form>
   <div class="row">
     <div class="col-md-6">
       <div class="form-group">
         <label for="Display">Display Term</label>
         <input id="Display" class="form-control" type="text" disabled value="{{resource.resourceJson.code.coding[0].display}}">
       </div>
 	  <div class="form-group" *ngIf="resource.resourceJson.performer!=null">
         <label for="Performer">Performed By</label>
         <input id="Performer" class="form-control" type="text" disabled value="{{resource.resourceJson.performer[0].actor.display}}">
       </div>
       <div class="form-group" *ngIf="resource.resourceJson.notes!=null">
         <label for="Notes">Notes</label>
         <input id="Notes" class="form-control" type="text" disabled value="{{resource.resourceJson.notes[0].text}}">
       </div>
     </div>
     <div class="col-md-6">
       <div class="form-group">
         <label for="Code">Code</label>
         <input id="Code" class="form-control" type="text" disabled value="{{resource.resourceJson.code.coding[0].code}}">
       </div>
       <div class="form-group">
         <label for="Date">Date</label>
         <input id="Date" class="form-control" type="text" disabled value="{{resource.resourceJson.performedDateTime | date:''dd/MM/y''}}">
       </div>
     </div>
   </div>
 </form>
 </div>');

delete from config where config_id = 'Template-Observation' and app_id = 'eds-data-validation';
insert into config (app_id, config_id, config_data)
values (
  'eds-data-validation',
  'Template-Observation',
  '<div class="container">
<form>
  <div class="row">
    <div class="col-md-6">
		<div class="form-group">
			<label for="Display">Display Term</label>
			<input id="Display" class="form-control" type="text" disabled value="{{resource.resourceJson.code.coding[0].display}}">
		</div>
        <div class="form-group">
			<label for="Date">Effective Date</label>
			<input id="Date" class="form-control" type="text" disabled value="{{resource.resourceJson.effectiveDateTime | date:''dd/MM/y''}}">
		</div>
        <div class="form-group" *ngIf="resource.resourceJson.valueQuantity!=null">
			<label for="Value">Value</label>
			<input id="Value" class="form-control" type="text" disabled value="{{resource.resourceJson.valueQuantity.value}}">
		</div>
        <div *ngFor="let component of resource.resourceJson.component">
			<div class="form-group">
				<label for="code">Code</label>
				<input id="code" class="form-control" type="text" disabled value="{{component.code.coding[0].code}}">
			</div>
            <div class="form-group">
				<label for="display">Display Term</label>
				<input id="display" class="form-control" type="text" disabled value="{{component.code.coding[0].display}}">
			</div>
            <div class="form-group">
				<label for="codeValue">Value</label>
				<input id="codeValue" class="form-control" type="text" disabled value="{{component.valueQuantity.value}}">
			</div>
        </div>
		<div class="form-group">
			<label for="Comments">Comments</label>
			<input id="Comments" class="form-control" type="text" disabled value="{{resource.resourceJson.comments}}">
		</div>
    </div>
    <div class="col-md-6">
		<div class="form-group">
			<label for="Code">Code</label>
			<input id="Code" class="form-control" type="text" disabled value="{{resource.resourceJson.code.coding[0].code}}">
		</div>
        <div class="form-group">
			<label for="Recorder">Recorded By</label>
			<input id="Recorder" class="form-control" type="text" disabled value="{{resource.resourceJson.performer[0].display}}">
		</div>
		<div class="form-group" *ngIf="resource.resourceJson.valueQuantity!=null">
			<label for="Value1Units">Units</label>
			<input id="Value1Units" class="form-control" type="text" disabled value="{{resource.resourceJson.valueQuantity.unit}}">
		</div>
    </div>
  </div>
</form>
</div>');

delete from config where config_id = 'Template-AllergyIntolerance' and app_id = 'eds-data-validation';
insert into config (app_id, config_id, config_data)
values (
  'eds-data-validation',
  'Template-AllergyIntolerance',
  '<div class="container">
<form>
  <div class="row">
    <div class="col-md-6">
      <div class="form-group">
        <label for="Display">Display Term</label>
        <input id="Display" class="form-control" type="text" disabled value="{{resource.resourceJson.substance.coding[0].display}}">
      </div>
      <div class="form-group">
        <label for="Recorder">Recorded By</label>
        <input id="Recorder" class="form-control" type="text" disabled value="{{resource.resourceJson.recorder?.display}}">
      </div>
    </div>
    <div class="col-md-6">
      <div class="form-group">
        <label for="Code">Code</label>
        <input id="Code" class="form-control" type="text" disabled value="{{resource.resourceJson.substance.coding[0].code}}">
      </div>
      <div class="form-group" >
        <label for="Text">Comments</label>
        <input id="Text" class="form-control" type="text" disabled value="{{resource.resourceJson.note?.text}}">
      </div>
    </div>
  </div>
</form>
</div>');

delete from config where config_id = 'Template-MedicationOrder' and app_id = 'eds-data-validation';
insert into config (app_id, config_id, config_data)
values (
  'eds-data-validation',
  'Template-MedicationOrder',
  '<div class="container">
<form>
  <div class="row">
    <div class="col-md-6">
      <div class="form-group">
        <label for="Display">Display Term</label>
        <input id="Display" class="form-control" type="text" disabled value="{{resource.resourceJson.medicationCodeableConcept.coding[0].display}}">
      </div>
      <div class="form-group">
        <label for="Dosage">Dosage</label>
        <input id="Dosage" class="form-control" type="text" disabled value="{{resource.resourceJson.dosageInstruction[0].text}}">
      </div>
      <div class="form-group">
        <label for="Date">Issued</label>
        <input id="Date" class="form-control" type="text" disabled value="{{resource.resourceJson.dateWritten | date:''dd/MM/y''}}">
      </div>
	  <div class="form-group">
        <label for="Text">Comments</label>
        <input id="Text" class="form-control" type="text" disabled value="{{resource.resourceJson.notes}}">
      </div>
    </div>
    <div class="col-md-6">
	  <div class="form-group">
        <label for="Code">Code</label>
        <input id="Code" class="form-control" type="text" disabled value="{{resource.resourceJson.medicationCodeableConcept.coding[0].code}}">
      </div>
      <div class="form-group">
        <label for="Qty">Quantity</label>
        <input id="Qty" class="form-control" type="text" disabled value="{{resource.resourceJson.dispenseRequest?.quantity.value}} {{resource.resourceJson.dispenseRequest?.quantity.unit}}">
      </div>
       <div class="form-group">
        <label for="Prescriber">Prescriber</label>
        <input id="Prescriber" class="form-control" type="text" disabled value="{{resource.resourceJson.prescriber?.display}}">
      </div>
    </div>
  </div>
</form>
</div>');

delete from config where config_id = 'Template-MedicationStatement' and app_id = 'eds-data-validation';
insert into config (app_id, config_id, config_data)
values (
  'eds-data-validation',
  'Template-MedicationStatement',
  '<div class="container">
<form>
  <div class="row">
    <div class="col-md-6">
      <div class="form-group">
        <label for="Display">Drug</label>
        <input id="Display" class="form-control" type="text" disabled value="{{resource.resourceJson.medicationCodeableConcept.coding[0].display}}">
      </div>
      <div class="form-group">
        <label for="Dosage">Dosage</label>
        <input id="Dosage" class="form-control" type="text" disabled value="{{resource.resourceJson.dosage[0].text}}">
      </div>
      <div class="form-group">
        <label for="Date">Date</label>
        <input id="Date" class="form-control" type="text" disabled value="{{resource.resourceJson.dateAsserted | date:''dd/MM/y''}}">
      </div>
      <div class="form-group">
        <label for="Status">Status</label>
        <input id="Status" class="form-control" type="text" disabled value="{{resource.resourceJson.status}}">
      </div>
	    <div class="form-group">
        <label for="Text">Comments</label>
        <input id="Text" class="form-control" type="text" disabled value="{{resource.resourceJson.notes}}">
      </div>
    </div>
    <div class="col-md-6">
	    <div class="form-group">
			<label for="Code">Code</label>
			<input id="Code" class="form-control" type="text" disabled value="{{resource.resourceJson.medicationCodeableConcept.coding[0].code}}">
		</div>
		<div *ngFor="let extension of resource.resourceJson.extension">
			<div class="form-group" *ngIf="extension.url==''http://endeavourhealth.org/fhir/StructureDefinition/primarycare-medication-authorisation-quantity-extension''" >
				<label for="Qty">Quantity</label>
				<input id="Qty" class="form-control" type="text" disabled value="{{extension.valueQuantity.value}} {{extension.valueQuantity.unit}}">
			</div>
			<div class="form-group" *ngIf="extension.url==''http://endeavourhealth.org/fhir/StructureDefinition/primarycare-medication-authorisation-type-extension''">
				<label for="Type">Type</label>
				<input id="Type" class="form-control" type="text" disabled value="{{extension.valueCoding.display}}">
			</div>
		</div>
		<div class="form-group">
			<label for="Source">Source</label>
			<input id="Source" class="form-control" type="text" disabled value="{{resource.resourceJson.informationSource?.display}}">
		</div>
    </div>
  </div>
</form>
</div>');

delete from config where config_id = 'Template-Encounter' and app_id = 'eds-data-validation';
insert into config (app_id, config_id, config_data)
values (
  'eds-data-validation',
  'Template-Encounter',
  '<div class="container">
  <form>
    <div class="row">
      <div class="col-md-6">
        <div class="form-group">
          <label for="Class">Encounter Class</label>
          <input id="Class" class="form-control" type="text" disabled value="{{resource.resourceJson.class}}">
        </div>
        <div class="form-group">
          <label for="Date">Appointment Date</label>
          <input id="Date" class="form-control" type="text" disabled value="{{resource.resourceJson.period.start | date:''''dd/MM/y''''}}">
        </div>
        <div class="form-group" *ngIf="resource.resourceJson.participant!=null">
          <label for="Practitioner">Practitioner</label>
          <input id="Practitioner" class="form-control" type="text" disabled value="{{resource.resourceJson.participant[0].individual.display}}">
        </div>
        <div class="form-group" *ngIf="resource.resourceJson.contained!=null">
          <label for="Linked">Linked resources</label>
          <div class="form-group" *ngFor="let linkedResource of resource.resourceJson.contained[0].entry">
            <input id="Linked" class="form-control" type="text" disabled value="{{linkedResource.item.display}}">
          </div>
        </div>
      </div>
      <div class="col-md-6">
        <div class="form-group" *ngIf="resource.resourceJson.type!=null">
          <label for="Type">Encounter Type</label>
          <input id="Type" class="form-control" type="text" disabled value="{{resource.resourceJson.type[0].text}}">
        </div>
        <div *ngFor="let extension of resource.resourceJson.extension">
          <div class="form-group" *ngIf="extension.url==''''http://endeavourhealth.org/fhir/StructureDefinition/primarycare-encounter-source''''" >
            <label for="Source">Source</label>
            <input id="Source" class="form-control" type="text" disabled value="{{extension.valueCodeableConcept.text}}">
          </div>
        </div>
        <div class="form-group">
          <label for="Place">Place</label>
          <input id="Place" class="form-control" type="text" disabled value="{{resource.resourceJson.serviceProvider?.display}}">
        </div>
      </div>
    </div>
  </form>
</div>');

delete from config where config_id = 'Template-Immunization' and app_id = 'eds-data-validation';
insert into config (app_id, config_id, config_data)
values (
  'eds-data-validation',
  'Template-Immunization',
  '<div class="container">
<form>
  <div class="row">
    <div class="col-md-6">
      <div class="form-group">
        <label for="Display">Display Term</label>
        <input id="Display" class="form-control" type="text" disabled value="{{resource.resourceJson.vaccineCode.coding[0].display}}">
      </div>
      <div class="form-group" *ngIf="resource.resourceJson.explanation.reason!=null">
        <label for="Reason">Reason</label>
        <input id="Reason" class="form-control" type="text" disabled value="{{resource.resourceJson.explanation.reason[0].text}}">
      </div>
      <div class="form-group" *ngIf="resource.resourceJson.explanation.reason==null">
        <label for="Reason">Reason</label>
        <input id="Reason" class="form-control" type="text" disabled value="">
      </div>
      <div class="form-group">
        <label for="Route">Route</label>
        <input id="Route" class="form-control" type="text" disabled value="{{resource.resourceJson.route?.text}}">
      </div>
      <div class="form-group">
        <label for="Lot">Lot No.</label>
        <input id="Lot" class="form-control" type="text" disabled value="{{resource.resourceJson.lotNumber}}">
      </div>
    </div>
    <div class="col-md-6">
      <div class="form-group">
        <label for="Code">Code</label>
        <input id="Code" class="form-control" type="text" disabled value="{{resource.resourceJson.vaccineCode.coding[0].code}}">
      </div>
      <div class="form-group">
        <label for="Performer">Performer</label>
        <input id="Performer" class="form-control" type="text" disabled value="{{resource.resourceJson.performer?.display}}">
      </div>
      <div class="form-group">
        <label for="Site">Site</label>
        <input id="Site" class="form-control" type="text" disabled value="{{resource.resourceJson.site?.text}}">
      </div>
      <div class="form-group">
        <label for="Status">Status</label>
        <input id="Status" class="form-control" type="text" disabled value="{{resource.resourceJson.status}}">
      </div>
    </div>
  </div>
</form>
</div>');

delete from config where config_id = 'Template-FamilyMemberHistory' and app_id = 'eds-data-validation';
insert into config (app_id, config_id, config_data)
values (
  'eds-data-validation',
  'Template-FamilyMemberHistory',
  '<div class="container">
<form>
  <div class="row">
    <div class="col-md-6">
		<div class="form-group">
			<label for="Display">Display Term</label>
			<input id="Display" class="form-control" type="text" disabled value="{{resource.resourceJson.condition[0].code.coding[0].display}}">
		</div>
        <div class="form-group">
			<label for="Relation">Relation</label>
			<input id="Relation" class="form-control" type="text" disabled value="{{resource.resourceJson.relationship.coding[0].display}}">
		</div>
		<div class="form-group" >
			<label for="Text">Comments</label>
			<input id="Text" class="form-control" type="text" disabled value="{{resource.resourceJson.condition[0].note?.text}}">
		</div>
    </div>
    <div class="col-md-6">
		<div class="form-group">
			<label for="Code">Code</label>
			<input id="Code" class="form-control" type="text" disabled value="{{resource.resourceJson.condition[0].code.coding[0].code}}">
		</div>
        <div *ngFor="let extension of resource.resourceJson.extension">
			<div class="form-group" *ngIf="extension.url==''http://endeavourhealth.org/fhir/StructureDefinition/primarycare-family-member-history-reporter-extension''" >
				<label for="Reporter">Reported By</label>
				<input id="Reporter" class="form-control" type="text" disabled value="{{extension.valueReference.display}}">
			</div>
		</div>
    </div>
  </div>
</form>
</div>');