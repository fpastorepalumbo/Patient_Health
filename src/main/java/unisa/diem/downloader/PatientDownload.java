package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientDownload extends BaseDownloader{

    // Create a client (only needed once)
    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

    List <Patient> patients;
    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public PatientDownload() {
        this.patients = new ArrayList<>();
        count = 0;
    }

    public List<Patient> getPatients() { return patients; }

    public void download() {

// Extract patient resources from the bundle
        Bundle bundle = null;

        try {
            bundle = (Bundle) client.search().forResource(Patient.class).offset(count).count(20)
                    .encodedXml().execute();
            count=count+20;
        }
        catch (Exception e) {
           new RuntimeException("Error during the download of the patient");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            // Check if the entry contains a patient resource
            //if (entry.getResource() instanceof Patient) {
                patients.add((Patient) entry.getResource());
            //}
        }

        if (patients.isEmpty()) {
            throw new RuntimeException("No patient found");
        }
    }
}
