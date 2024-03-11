package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.Organization;

import java.util.List;

public class PayersConverter extends BaseConverter {

    private List<Organization> boundlePayers;
    @FXML
    private ObservableList<PayersClass> listaCampiPayer;

    public PayersConverter(List<Organization> boundlePayers) {
        this.boundlePayers = boundlePayers;
        this.listaCampiPayer = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for(Organization payer : boundlePayers) {
            PayersClass py = new PayersClass();

            py.setName(payer.getName());
            py.setAddress(payer.getAddress().get(0).getLine().get(0).toString());
            py.setCity(payer.getAddress().get(0).getCity());
            py.setState(payer.getAddress().get(0).getState());
            py.setPhone(payer.getTelecom().get(0).getValue());
            py.setRevenue(payer.getExtension().get(0).getValue().toString());

            listaCampiPayer.add(py);
        }

        if (listaCampiPayer.isEmpty()) {
            PayersClass py = new PayersClass();
            py.setName("N/A");
            py.setAddress("N/A");
            py.setCity("N/A");
            py.setState("N/A");
            py.setPhone("N/A");
            py.setRevenue("N/A");
            listaCampiPayer.add(py);
        }
    }

    public ObservableList<PayersClass> getListaCampiPayer() {
        return listaCampiPayer;
    }

    public class PayersClass {
        private String name;
        private String address;
        private String city;
        private String state;

        private String phone;

        private String revenue;



        public PayersClass() {
            this.name = "";
            this.address = "";
            this.city = "";
            this.state = "";
            this.phone = "";
            this.revenue = "";
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRevenue() {
            return revenue;
        }

        public void setRevenue(String revenue) {
            this.revenue = revenue;
        }

        @Override
        public String toString() {
            return "OrganizationClass{" +
                    "name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    ", phone='" + phone + '\'' +
                    ", revenue='" + revenue + '\'' +
                    '}';
        }
    }
}
