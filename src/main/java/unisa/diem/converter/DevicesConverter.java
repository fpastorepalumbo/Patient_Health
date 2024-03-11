package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.Device;

import java.util.List;

public class DevicesConverter extends BaseConverter {

    private List<Device> boundleDevices;
    @FXML
    private ObservableList<DeviceClass> listaCampiDevices;

    public DevicesConverter(List<Device> boundleDevices) {
        this.boundleDevices = boundleDevices;
        this.listaCampiDevices = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for(Device device : boundleDevices) {
            DeviceClass dv = new DeviceClass();

            dv.setCode(device.getIdentifier().get(0).getValue());
            dv.setDescription(device.getType().getText());
            //dv.setDate(device.getExpirationDate().toString());
            //dv.setCost(device.getDeviceName().get(0).getName());
            dv.setPatient(device.getPatient().getReference());

            listaCampiDevices.add(dv);
        }

        if (listaCampiDevices.isEmpty()) {
            DeviceClass dv = new DeviceClass();
            dv.setCode("N/A");
            dv.setDescription("N/A");
            dv.setDate("N/A");
            dv.setCost("N/A");
            dv.setPatient("N/A");
            listaCampiDevices.add(dv);
        }
    }

    public ObservableList<DeviceClass> getListaCampiDevices() {
        return listaCampiDevices;
    }

    public class DeviceClass {
        private String code;
        private String description;
        private String date;
        private String cost;
        private String patient;
        public DeviceClass() {
            this.code = "";
            this.description = "";
            this.date = "";
            this.cost = "";
            this.patient = "";
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getPatient() {
            return patient;
        }

        public void setPatient(String patient) {
            this.patient = patient;
        }

        @Override
        public String toString() {
            return "DeviceClass{" +
                    "code='" + code + '\'' +
                    ", description='" + description + '\'' +
                    ", date='" + date + '\'' +
                    ", cost='" + cost + '\'' +
                    ", patient='" + patient + '\'' +
                    '}';
        }
    }
}
