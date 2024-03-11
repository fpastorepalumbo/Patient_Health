package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import lombok.Getter;
import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AllergyDownload extends BaseDownloader {

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);
    @Getter
    List<AllergyIntolerance> allergies;
    String patientId;

    public AllergyDownload(String patientId) {
        this.allergies = new ArrayList<>();
        this.patientId = patientId;
    }

    @Override
    public void download() {
        Bundle bundle;

        try {
            bundle = (Bundle) client.search().forResource(AllergyIntolerance.class)
            .where(AllergyIntolerance.PATIENT.hasId(patientId))
            .encodedXml().execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the allergies");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            allergies.add((AllergyIntolerance) entry.getResource());

        if (allergies.isEmpty())
            System.out.println("No allergies found in the patient with id: " + patientId);
    }
}
