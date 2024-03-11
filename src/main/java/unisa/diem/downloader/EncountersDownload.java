package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Encounter;

import java.util.ArrayList;
import java.util.List;

public class EncountersDownload extends BaseDownloader {

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);
    @Getter
    List <Encounter> encounters;
    @Setter
    @Getter
    int count;

    public EncountersDownload() {
        this.encounters = new ArrayList<>();
        count = 0;
    }

    public void download() {

        Bundle bundle;

        try {
            bundle = (Bundle) client.search().forResource(Encounter.class).offset(count).count(20)
                    .encodedXml().execute();
            count = count + 20;
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the Encounter");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            encounters.add((Encounter) entry.getResource());

        if (encounters.isEmpty())
            throw new RuntimeException("No encounter found");
    }
}
