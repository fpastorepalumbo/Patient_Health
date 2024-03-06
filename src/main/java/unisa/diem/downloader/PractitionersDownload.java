package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Practitioner;

import java.util.ArrayList;
import java.util.List;

public class PractitionersDownload extends BaseDownloader{

    // Create a client (only needed once)
    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

    List <Practitioner> practitioners;
    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public PractitionersDownload() {
        this.practitioners = new ArrayList<>();
        count = 0;
    }

    public List<Practitioner> getPractitioners() { return practitioners; }

    public void download() {

// Extract organization resources from the bundle
        Bundle bundle = null;

        try {
            bundle = (Bundle) client.search().forResource(Practitioner.class).offset(count).count(20)
                    .encodedXml().execute();
            count=count+20;
        }
        catch (Exception e) {
           new RuntimeException("Error during the download of the Practitioners");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            practitioners.add((Practitioner) entry.getResource());
        }

        if (practitioners.isEmpty()) {
            throw new RuntimeException("No practitioner found");
        }
    }
}
