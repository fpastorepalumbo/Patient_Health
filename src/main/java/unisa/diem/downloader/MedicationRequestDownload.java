package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.ReferenceClientParam;
import lombok.Getter;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Claim;
import org.hl7.fhir.r4.model.ExplanationOfBenefit;
import org.hl7.fhir.r4.model.MedicationRequest;

import java.util.ArrayList;
import java.util.List;

public class MedicationRequestDownload extends BaseDownloader {

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

    @Getter
    List<MedicationRequest> medicationRequests;
    @Getter
    List<Claim> claims;
    @Getter
    List<ExplanationOfBenefit> eobs;

    String encounterId;

    public MedicationRequestDownload(String encounterId) {
        this.medicationRequests = new ArrayList<>();
        this.encounterId = encounterId;
    }

    @Override
    public void download() {
        Bundle bundle;

        try {
            /*
            bundle = (Bundle) client.search().forResource(MedicationRequest.class)
            .where(MedicationRequest.ENCOUNTER.hasId(encounterId))
            .encodedXml().execute();
            */
            bundle = (Bundle) client.search().forResource(MedicationRequest.class)
                    .where(new ReferenceClientParam("encounter").hasId("155aa73b-46da-5808-c218-80a5ed671009"))
                    .encodedXml()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the medication requests");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            medicationRequests.add((MedicationRequest) entry.getResource());

        if (medicationRequests.isEmpty())
            System.out.println("No medication request found in the encounter with id: " + encounterId);
    }
}
