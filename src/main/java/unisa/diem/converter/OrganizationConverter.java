package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.*;

import java.util.List;

public class OrganizationConverter extends BaseConverter {

    private List<Organization> boundleOrganizations;
    int i = 0;
    @FXML
    private ObservableList<OrganizationClass> listaCampiOrganization;

    public OrganizationConverter(List<Organization> boundleOrganizations) {
        this.boundleOrganizations = boundleOrganizations;
        this.listaCampiOrganization = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for(Organization organization : boundleOrganizations) {
            OrganizationClass oc = new OrganizationClass();

            oc.setName(organization.getName());
            oc.setAddress(organization.getAddress().get(0).toString());
            oc.setCity(organization.getAddress().get(0).getCity());
            oc.setState(organization.getAddress().get(0).getState());
            oc.setPhone(organization.getTelecom().get(0).getValue());
            oc.setRevenue(organization.getExtension().get(0).getValue().toString());

            listaCampiOrganization.add(oc);
            System.out.println("Organization:"+ i + "\n");
            i++;
        }

        if (listaCampiOrganization.isEmpty()) {
            throw new RuntimeException("listaCampiOrganization is empty");
        }
    }

    public ObservableList<OrganizationClass> getListaCampiOrganization() {
        return listaCampiOrganization;
    }

    public class OrganizationClass {
        private String name;
        private String address;
        private String city;
        private String state;

        private String phone;

        private String revenue;



        public OrganizationClass() {
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
