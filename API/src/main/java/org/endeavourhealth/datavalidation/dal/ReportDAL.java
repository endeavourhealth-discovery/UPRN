package org.endeavourhealth.datavalidation.dal;

import org.endeavourhealth.core.database.dal.reference.models.Concept;
import org.endeavourhealth.core.xml.QueryDocument.LibraryItem;
import org.endeavourhealth.datavalidation.models.Practitioner;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ReportDAL {
	LibraryItem runReport(UUID userUuid, UUID reportUuid, Map<String, String> reportParams) throws Exception;

	List<List<String>> getNHSExport(UUID userUuid, UUID reportUuid) throws Exception;

	List<List<String>> getDataExport(UUID userUuid, UUID reportUuid) throws Exception;

	List<Concept> getEncounterTypes() throws Exception;

	List<Concept> getReferralTypes() throws Exception;

	List<Concept> getReferralPriorities() throws Exception;

	List<Practitioner> searchPractitioner(String searchData, UUID organisationUuid) throws Exception;
}
