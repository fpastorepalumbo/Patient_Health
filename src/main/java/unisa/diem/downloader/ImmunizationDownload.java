package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import lombok.Getter;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Immunization;

import java.util.ArrayList;
import java.util.List;

public class ImmunizationDownload extends BaseDownloader{

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);
    @Getter
    List<Immunization> immunizations;
    String patientId;

    public ImmunizationDownload(String patientId) {
        this.immunizations =  new ArrayList<>();
        this.patientId = patientId;
    }

    @Override
    public void download() {
        Bundle bundle;

        try {
            bundle = (Bundle) client.search().forResource(Immunization.class)
                    .where(Immunization.PATIENT.hasId(patientId))
                    .encodedXml().execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the immunizations");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            immunizations.add((Immunization) entry.getResource());

        if (immunizations.isEmpty())
            System.out.println("No Immunization found in the patient with id: " + patientId);
    }
}
