package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.ReferenceClientParam;
import lombok.Getter;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.DeviceRequest;

import java.util.ArrayList;
import java.util.List;

public class DevicesDownload extends BaseDownloader{

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);
    @Getter
    List<DeviceRequest> devices;
    String encounterId;

    public DevicesDownload(String encounterId) {
        this.devices = new ArrayList<>();
        this.encounterId = encounterId;
    }

    @Override
    public void download() {
        Bundle bundle;

        try {
           /*
           bundle = (Bundle) client.search().forResource(Device.class)
            .where(Device.PATIENT.hasId(patientId)).where(DeviceRequest.ENCOUNTER.hasId(encounterId))
            .encodedXml().execute();
            */
            bundle = (Bundle) client.search().forResource(DeviceRequest.class)
                    .where(new ReferenceClientParam("encounter").hasId("0c62aae4-c10b-4d30-0091-4cb1f3422b55"))
                    .encodedXml()
                    .execute();
        }
        catch (Exception e) {
            throw new RuntimeException("Error during the download of the Devices");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            devices.add((DeviceRequest) entry.getResource());

        if (devices.isEmpty())
            System.out.println("No devices found in the encounter with id: " + encounterId);
    }
}
