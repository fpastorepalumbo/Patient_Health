package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientDownload extends BaseDownloader {

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

    @Getter
    List <Patient> patients;
    @Setter
    @Getter
    int count;

    public PatientDownload() {
        this.patients = new ArrayList<>();
        count = 0;
    }

    public void download() {
        Bundle bundle = null;

        try {
            bundle = (Bundle) client.search().forResource(Patient.class).offset(count).count(20)
                    .encodedXml().execute();
            count = count + 20;
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the patient");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
                patients.add((Patient) entry.getResource());

        if (patients.isEmpty())
            throw new RuntimeException("No patient found");
    }
}
