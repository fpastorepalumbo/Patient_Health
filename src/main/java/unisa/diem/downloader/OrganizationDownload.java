package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.TokenClientParam;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Organization;

import java.util.ArrayList;
import java.util.List;

public class OrganizationDownload extends BaseDownloader {

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);
    @Getter
    List <Organization> organizations;

    @Getter
    List <Organization> organizationsSearch;


    @Setter
    @Getter
    int count;

    public OrganizationDownload() {
        this.organizations = new ArrayList<>();
        this.organizationsSearch = new ArrayList<>();
        count = 0;
    }

    public void download() {

        Bundle bundle;

        try {
            bundle = (Bundle) client.search().forResource(Organization.class).offset(count).count(20)
                    .encodedXml().execute();
            count = count + 20;
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the organizations");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
                organizations.add((Organization) entry.getResource());

        if (organizations.isEmpty())
            throw new RuntimeException("No organization found");
    }

    public void downloadOrganizationWithName(String name){
        Bundle bundle;
        organizationsSearch.clear();

        try {
            bundle = (Bundle) client.search().forResource(Organization.class)
                    .where(Organization.NAME.matches().value(name))
                    .encodedXml()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the organization");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            organizationsSearch.add((Organization) entry.getResource());

        if (organizationsSearch.isEmpty())
            throw new RuntimeException("No organization found");
    }

    public void downloadOrganizationWithId(String id){
        Bundle bundle;
        organizationsSearch.clear();

        try {
            bundle = (Bundle) client.search().forResource(Organization.class)
                    .where(new TokenClientParam("identifier").exactly().code(id))
                    .encodedXml()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the organization");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            organizationsSearch.add((Organization) entry.getResource());

        if (organizationsSearch.isEmpty())
            throw new RuntimeException("No organization found");
    }
}
