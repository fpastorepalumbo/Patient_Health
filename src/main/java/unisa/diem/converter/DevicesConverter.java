package unisa.diem.converter;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.ReferenceClientParam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Device;
import org.hl7.fhir.r4.model.DeviceRequest;

import java.util.List;

public class DevicesConverter extends BaseConverter {

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);
    private final List<DeviceRequest> bundleDevices;
    @Getter
    @FXML
    private final ObservableList<DeviceClass> fieldsListDevices;

    public DevicesConverter(List<DeviceRequest> bundleDevices) {
        this.bundleDevices = bundleDevices;
        this.fieldsListDevices = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {
        for(DeviceRequest deviceRequest : bundleDevices) {
            DeviceClass dv = new DeviceClass();
            Device dev = getDevice(deviceRequest.getSubject().getReference().split("/")[1]);
            String[] parts;

            dv.setCode(dev.getIdentifier().get(0).getValue());

            dv.setDescription(dev.getType().getCoding().get(0).getDisplay());

            parts = deviceRequest.getOccurrencePeriod().getStart().toString().split(" ");
            dv.setStartDate(parts[5] + "-" + parts[1] + "-" + parts[2]);

            if (deviceRequest.getOccurrencePeriod().hasEnd()) {
                parts = deviceRequest.getOccurrencePeriod().getEnd().toString().split(" ");
                dv.setStopDate(parts[5] + "-" + parts[1] + "-" + parts[2]);
            }
            else
                dv.setStopDate("---");

            parts = dev.getPatient().getReference().split("/");
            dv.setPatient(parts[1]);

            fieldsListDevices.add(dv);
        }

        if (fieldsListDevices.isEmpty()) {
            DeviceClass dv = new DeviceClass();
            dv.setCode("N/A");
            dv.setDescription("N/A");
            dv.setStartDate("N/A");
            dv.setStopDate("N/A");
            dv.setPatient("N/A");
            fieldsListDevices.add(dv);
        }
    }

    private Device getDevice(String patId) {
        Bundle bundle;

        try {
            bundle = (Bundle) client.search().forResource(Device.class)
                    .where(new ReferenceClientParam("patient").hasId(patId))
                    .encodedXml()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the Devices");
        }
        for (Bundle.BundleEntryComponent entry : bundle.getEntry())
            return (Device) entry.getResource();
        return new Device();
    }

    @Setter
    @Getter
    public static class DeviceClass {
        private String code;
        private String description;
        private String startDate;
        private String stopDate;
        private String patient;

        public DeviceClass() {
            this.code = "";
            this.description = "";
            this.startDate = "";
            this.stopDate = "";
            this.patient = "";
        }

        @Override
        public String toString() {
            return "DeviceClass{" +
                    "code='" + code + '\'' +
                    ", description='" + description + '\'' +
                    ", startDate='" + startDate + '\'' +
                    ", stopDate='" + stopDate + '\'' +
                    ", patient='" + patient + '\'' +
                    '}';
        }
    }
}
