package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.ReferenceClientParam;
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
            /*bundle = (Bundle) client.search().forResource(Observation.class)
            .where(Observation.ENCOUNTER.hasId(encounterId))
            .encodedXml().execute();*/
            bundle = (Bundle) client.search().forResource(Observation.class)
                    .where(new ReferenceClientParam("encounter").hasId("3cb4d689-187e-3231-df3c-05b7f1b77bb5"))
                    .encodedXml()
                    .execute();
        }
        catch (Exception e) {
            new RuntimeException("Error during the download of the observations.");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            observations.add((Observation) entry.getResource());
        }

        if (observations.isEmpty()) {
            System.out.println("No observation found in the patient with id: " + encounterId);
        }

    }
}
