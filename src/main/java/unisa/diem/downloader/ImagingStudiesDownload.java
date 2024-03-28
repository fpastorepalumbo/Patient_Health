package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.ImagingStudy;
import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class ImagingStudiesDownload {

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

    @Getter
    List<ImagingStudy> images;

    @Setter
    @Getter
    int count;

    public ImagingStudiesDownload() {
        this.images = new ArrayList<>();
        count = 0;
    }

    public void download() {
        Bundle  bundle;
        try {
            bundle = (Bundle) client.search().forResource(ImagingStudy.class).offset(count).count(20)
                    .prettyPrint()
                    .execute();
            count = count + 20;
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the ImagingStudies");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            images.add((ImagingStudy) entry.getResource());

        if (images.isEmpty())
            throw new RuntimeException("No ImagingStudies found");
    }
}
