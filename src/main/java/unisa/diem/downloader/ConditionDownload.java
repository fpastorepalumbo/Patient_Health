package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Condition;

import java.util.ArrayList;
import java.util.List;

public class ConditionDownload extends BaseDownloader{

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

    List<Condition> conditions;

    String patientId = "";

    public ConditionDownload(String patientId) {
        this.conditions = new ArrayList<>();
        this.patientId = patientId;
    }

    public List<Condition> getConditions() {
        return conditions;
    }
    @Override
    public void download() {
        Bundle bundle = null;

        try {
            bundle = (Bundle) client.search().forResource(Condition.class)
                    .where(Condition.PATIENT.hasId(patientId))
                    .encodedXml().execute();
        }
        catch (Exception e) {
            new RuntimeException("Error during the download of the conditions");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            // Check if the entry contains a patient resource
            //if (entry.getResource() instanceof Patient) {
            conditions.add((Condition) entry.getResource());
            //}
        }

        if (conditions.isEmpty()) {
            System.out.println("No conditions found in the patient with id: " + patientId);
        }

    }
}
