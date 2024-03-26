package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.StringClientParam;
import ca.uhn.fhir.rest.gclient.TokenClientParam;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Practitioner;

import java.util.ArrayList;
import java.util.List;

public class PractitionersDownload extends BaseDownloader {

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);
    @Getter
    List <Practitioner> practitioners;

    @Getter
    List <Practitioner> practitionerSearch;
    @Setter
    @Getter
    int count;

    public PractitionersDownload() {
        this.practitioners = new ArrayList<>();
        this.practitionerSearch = new ArrayList<>();
        count = 0;
    }

    public void download() {
        Bundle bundle;

        try {
            bundle = (Bundle) client.search().forResource(Practitioner.class).offset(count).count(20)
                    .encodedXml().execute();
            count = count + 20;
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the practitioners");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            practitioners.add((Practitioner) entry.getResource());

        if (practitioners.isEmpty())
            throw new RuntimeException("No practitioner found");
    }

    public void downloadPractitionerWithName(String name){
        Bundle bundle;
        practitionerSearch.clear();

        try {
            bundle = (Bundle) client.search().forResource(Practitioner.class)
                    .where(new StringClientParam("name").matches().value(name))
                    .encodedXml()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the practitioner");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            practitionerSearch.add((Practitioner) entry.getResource());

        if (practitionerSearch.isEmpty())
            throw new RuntimeException("No practitioner found");
    }

    public void downloadPractitionerWithId(String id){
        Bundle bundle;
        practitionerSearch.clear();

        try {
            bundle = (Bundle) client.search().forResource(Practitioner.class)
                    .where(new TokenClientParam("identifier").exactly().code(id))
                    .encodedXml()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the practitioner");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            practitionerSearch.add((Practitioner) entry.getResource());

        if (practitionerSearch.isEmpty())
            throw new RuntimeException("No practitioner found with id: " + id);
    }
}
