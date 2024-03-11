package unisa.diem.downloader;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.ReferenceClientParam;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Device;
import org.hl7.fhir.r4.model.DeviceRequest;
import org.hl7.fhir.r4.model.Procedure;

import java.util.ArrayList;
import java.util.List;

public class DevicesDownload extends BaseDownloader{

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

    List<Device> devices;

    String encounterId = "";
    String patientId = "";

    public DevicesDownload(String encounterId, String patientId) {
        this.devices = new ArrayList<>();
        this.encounterId = encounterId;
        this.patientId = patientId;
    }

    public List<Device> getDevices() { return devices; }
    @Override
    public void download() {

        Bundle bundle = null;

        try {
           /* bundle = (Bundle) client.search().forResource(Device.class)
            .where(Device.PATIENT.hasId(patientId)).where(DeviceRequest.ENCOUNTER.hasId(encounterId))  // ho un dubbio, vedi DeviceLoader per vedere come fare la query
            .encodedXml().execute();
*/

            bundle = (Bundle) client.search().forResource(Device.class)
                    .where(new ReferenceClientParam("patient").hasId("6f4d77e9-2203-03a3-8966-92a22a21000a"))
                    .encodedXml()
                    .execute();
        }
        catch (Exception e) {
            new RuntimeException("Error during the download of the Devices");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            devices.add((Device) entry.getResource());
        }

        if (devices.isEmpty()) {
            System.out.println("No devices found in the patient with id: " + encounterId);
        }

    }
}
