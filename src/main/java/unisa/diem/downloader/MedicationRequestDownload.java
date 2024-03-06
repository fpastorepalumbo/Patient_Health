package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.MedicationRequest;

import java.util.ArrayList;
import java.util.List;

public class MedicationRequestDownload extends BaseDownloader{

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

    List<MedicationRequest> medicationRequests;

    String encounterId = "";

    public MedicationRequestDownload(String encounterId) {
        this.medicationRequests = new ArrayList<>();
        this.encounterId = encounterId;
    }

    public List<MedicationRequest> getMedicationRequests() { return medicationRequests; }
    @Override
    public void download() {

        Bundle bundle = null;

        try {
            bundle = (Bundle) client.search().forResource(MedicationRequest.class)
            .where(MedicationRequest.ENCOUNTER.hasId(encounterId))
            .encodedXml().execute();
        }
        catch (Exception e) {
            new RuntimeException("Error during the download of the MedicationRequest");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            medicationRequests.add((MedicationRequest) entry.getResource());
        }

        if (medicationRequests.isEmpty()) {
            System.out.println("No medication request found in the patient with id: " + encounterId);
        }

    }
}
