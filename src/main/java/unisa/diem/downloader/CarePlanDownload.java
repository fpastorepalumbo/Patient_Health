package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.CarePlan;

import java.util.ArrayList;
import java.util.List;

public class CarePlanDownload extends BaseDownloader{

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

    List<CarePlan> carePlans;

    String patientId = "";

    public CarePlanDownload(String patientId) {
        this.carePlans = new ArrayList<>();
        this.patientId = patientId;
    }

    public List<CarePlan> getCarePlans() {
        return carePlans;
    }
    @Override
    public void download() {

        Bundle bundle = null;

        try {
            bundle = (Bundle) client.search().forResource(CarePlan.class)
                    .where(CarePlan.PATIENT.hasId(patientId))
                    .encodedXml().execute();
        }
        catch (Exception e) {
            new RuntimeException("Error during the download of the carePlans");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            // Check if the entry contains a patient resource
            //if (entry.getResource() instanceof Patient) {
            carePlans.add((CarePlan) entry.getResource());
            //}
        }

        if (carePlans.isEmpty()) {
            System.out.println("No carePlans found in the patient with id: " + patientId);
        }

    }
}
