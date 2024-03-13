package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.ReferenceClientParam;
import lombok.Getter;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Procedure;

import java.util.ArrayList;
import java.util.List;

public class ProceduresDownload extends BaseDownloader {

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);
    @Getter
    List<Procedure> procedures;
    String encounterId;

    public ProceduresDownload(String encounterId) {
        this.procedures = new ArrayList<>();
        this.encounterId = encounterId;
    }

    @Override
    public void download() {
        Bundle bundle;

        try {
            /*
            bundle = (Bundle) client.search().forResource(Procedure.class)
            .where(Procedure.ENCOUNTER.hasId(encounterId))
            .encodedXml().execute();
            */
            bundle = (Bundle) client.search().forResource(Procedure.class)
                    .where(new ReferenceClientParam("encounter").hasId("efe28b57-e201-a19c-5008-1f178258b681"))
                    .encodedXml()
                    .execute();
        }
        catch (Exception e) {
            throw new RuntimeException("Error during the download of the procedures");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            procedures.add((Procedure) entry.getResource());

        if (procedures.isEmpty())
            System.out.println("No procedures found in the encounter with id: " + encounterId);
    }
}
