package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import lombok.Getter;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.CarePlan;

import java.util.ArrayList;
import java.util.List;

public class CarePlanDownload extends BaseDownloader {

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);
    @Getter
    List<CarePlan> carePlans;
    String patientId;

    public CarePlanDownload(String patientId) {
        this.carePlans = new ArrayList<>();
        this.patientId = patientId;
    }

    @Override
    public void download() {
        Bundle bundle;

        try {
            bundle = (Bundle) client.search().forResource(CarePlan.class)
                    .where(CarePlan.PATIENT.hasId(patientId))
                    .encodedXml().execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the care plans");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            carePlans.add((CarePlan) entry.getResource());
    }
}
