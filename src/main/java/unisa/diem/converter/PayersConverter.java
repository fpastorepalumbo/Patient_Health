package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Organization;

import java.util.List;

public class PayersConverter extends BaseConverter {

    private final List<Organization> bundlePayers;
    @Getter
    @FXML
    private final ObservableList<PayersClass> fieldsListPayer;

    public PayersConverter(List<Organization> bundlePayers) {
        this.bundlePayers = bundlePayers;
        this.fieldsListPayer = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for(Organization payer : bundlePayers) {
            PayersClass py = new PayersClass();

            py.setName(payer.getName());

            if(payer.hasAddress()) {
                py.setCity(payer.getAddress().get(0).getCity());
                py.setState(payer.getAddress().get(0).getState());
            } else {
                py.setCity("---");
                py.setState("---");
            }

            if (payer.getTelecom().get(0).hasValue())
                py.setPhone(payer.getTelecom().get(0).getValue());
            else
                py.setPhone("---");

            fieldsListPayer.add(py);
        }

        if (fieldsListPayer.isEmpty()) {
            PayersClass py = new PayersClass();
            py.setName("N/A");
            py.setCity("N/A");
            py.setState("N/A");
            py.setPhone("N/A");
            fieldsListPayer.add(py);
        }
    }

    @Setter
    @Getter
    public static class PayersClass {
        private String name;
        private String address;
        private String city;
        private String state;
        private String phone;
        private String revenue;

        public PayersClass() {
            this.name = "";
            this.city = "";
            this.state = "";
            this.phone = "";
        }

        @Override
        public String toString() {
            return "OrganizationClass{" +
                    "name='" + name + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }
}
