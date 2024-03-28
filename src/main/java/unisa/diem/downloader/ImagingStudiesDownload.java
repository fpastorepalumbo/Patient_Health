package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.ReferenceClientParam;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.ImagingStudy;
import java.util.ArrayList;
import java.util.List;

public class ImagingStudiesDownload {

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);
    @Getter
    List<ImagingStudy> images;
    @Getter
    List<ImagingStudy> imageSearch;
    @Setter
    @Getter
    int count;

    public ImagingStudiesDownload() {
        this.images = new ArrayList<>();
        this.imageSearch = new ArrayList<>();
        count = 0;
    }

    public void download() {
        Bundle  bundle;
        try {
            bundle = (Bundle) client.search().forResource(ImagingStudy.class).offset(count).count(30)
                    .encodedXml()
                    .execute();
            count = count + 30;
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the ImagingStudies");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            images.add((ImagingStudy) entry.getResource());

        if (images.isEmpty())
            throw new RuntimeException("No ImagingStudies found");
    }

    public void downloadImageWithPatientId (String patientId) {
        Bundle  bundle;
        imageSearch.clear();

        try {
            bundle = (Bundle) client.search().forResource(ImagingStudy.class)
                    .where(new ReferenceClientParam("patient").hasId(patientId))
                    .encodedXml()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the ImagingStudies");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            imageSearch.add((ImagingStudy) entry.getResource());

        if (imageSearch.isEmpty())
            throw new RuntimeException("No ImagingStudies found");
    }

    public void downloadImageWithEncounterId (String encounterId) {
        Bundle bundle;
        imageSearch.clear();

        try {
            bundle = (Bundle) client.search().forResource(ImagingStudy.class)
                    .where(new ReferenceClientParam("encounter").hasId(encounterId))
                    .encodedXml()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the ImagingStudies");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            imageSearch.add((ImagingStudy) entry.getResource());

        if (imageSearch.isEmpty())
            throw new RuntimeException("No ImagingStudies found");
    }
}
