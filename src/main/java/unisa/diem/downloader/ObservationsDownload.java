package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;

import java.util.ArrayList;
import java.util.List;

public class ObservationsDownload extends BaseDownloader{

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

    List<Observation> observations;

    String encounterId = "";

    public ObservationsDownload(String encounterId) {
        this.observations = new ArrayList<>();
        this.encounterId = encounterId;
    }

    public List<Observation> getObservations() { return observations; }
    @Override
    public void download() {

        Bundle bundle = null;

        try {
            bundle = (Bundle) client.search().forResource(Observation.class)
            .where(Observation.ENCOUNTER.hasId(encounterId))
            .encodedXml().execute();
        }
        catch (Exception e) {
            new RuntimeException("Error during the download of the allergies");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            observations.add((Observation) entry.getResource());
        }

        if (observations.isEmpty()) {
            System.out.println("No allergies found in the patient with id: " + encounterId);
        }

    }
}
