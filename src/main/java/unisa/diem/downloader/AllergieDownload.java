package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AllergieDownload extends BaseDownloader{

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

    List<AllergyIntolerance> allergies;

    String patientId = "";

    public AllergieDownload(String patientId) {
        this.allergies = new ArrayList<>();
        this.patientId = patientId;
    }

    public List<AllergyIntolerance> getAllergies() { return allergies; }
    @Override
    public void download() {

        Bundle bundle = null;

        try {
            bundle = (Bundle) client.search().forResource(AllergyIntolerance.class)
            .where(AllergyIntolerance.PATIENT.hasId(patientId))
            .encodedXml().execute();
        }
        catch (Exception e) {
            new RuntimeException("Error during the download of the allergies");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            // Check if the entry contains a patient resource
            //if (entry.getResource() instanceof Patient) {
            allergies.add((AllergyIntolerance) entry.getResource());
            //}
        }

        if (allergies.isEmpty()) {
            System.out.println("No allergies found in the patient with id: " + patientId);
        }

    }
}
