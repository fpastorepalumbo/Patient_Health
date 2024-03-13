package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Organization;

import java.util.ArrayList;
import java.util.List;

public class PayersDownload extends BaseDownloader {

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

    @Getter
    List <Organization> payers;
    @Setter
    @Getter
    int count;

    public PayersDownload() {
        this.payers = new ArrayList<>();
    }

    public void download() {
        Bundle bundle;

        try {
            bundle = (Bundle) client.search().forResource(Organization.class).offset(2535).count(10)
                    .encodedXml().execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the payers");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            payers.add((Organization) entry.getResource());

        if (payers.isEmpty())
            throw new RuntimeException("No payer found");
    }
}
