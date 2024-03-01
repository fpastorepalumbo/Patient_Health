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

    public PatientDownload() {
        this.patients = new ArrayList<>();
    }

    public List<Patient> getPatients() { return patients; }

    public void download() {

// Extract patient resources from the bundle
        Bundle bundle = null;

        try {
           bundle = (Bundle) client.search().forResource(Patient.class).encodedXml().execute();
        }
        catch (Exception e) {
           new RuntimeException("Error during the download of the patient");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            // Check if the entry contains a patient resource
            if (entry.getResource() instanceof Patient) {
                // Cast the resource to Patient
                //Patient patient = (Patient) entry.getResource();
                patients.add((Patient) entry.getResource());

                // Now you can work with the patient resource
               // String patientName = patient.getNameFirstRep().getNameAsSingleString();
                // Do whatever you need with the patient
              //  System.out.println("Patient name: " + patientName);

                // If you only need one patient from the bundle, you can break out of the loop
               // break;
            }
        }

        if (patients.isEmpty()) {
            throw new RuntimeException("No patient found");
        }
    }








    // GET http://localhost:8080/fhir/Patient/a58de3fd-f026-902b-55c1-872dc042e0c5/_history/1?_format=json&_pretty=true
    // "id": "a58de3fd-f026-902b-55c1-872dc042e0c5",
}
