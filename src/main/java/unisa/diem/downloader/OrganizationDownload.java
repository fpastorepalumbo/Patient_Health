package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Organization;

import java.util.ArrayList;
import java.util.List;

public class OrganizationDownload extends BaseDownloader{

    // Create a client (only needed once)
    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

    List <Organization> organizations;
    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public OrganizationDownload() {
        this.organizations = new ArrayList<>();
        count = 0;
    }

    public List<Organization> getOrganizations() { return organizations; }

    public void download() {

// Extract organization resources from the bundle
        Bundle bundle = null;

        try {
            bundle = (Bundle) client.search().forResource(Organization.class).offset(count).count(20)
                    .encodedXml().execute();
            count=count+20;
        }
        catch (Exception e) {
           new RuntimeException("Error during the download of the Organizations");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            // Check if the entry contains a organization resource
            //if (entry.getResource() instanceof Patient) {
                organizations.add((Organization) entry.getResource());
            //}
        }

        if (organizations.isEmpty()) {
            throw new RuntimeException("No patient found");
        }
    }
}
