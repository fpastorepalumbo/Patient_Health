package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.*;

import java.util.List;

public class OrganizationConverter extends BaseConverter {

    private final List<Organization> bundleOrganizations;
    @Getter
    @FXML
    private final ObservableList<OrganizationClass> fieldsListOrganization;

    public OrganizationConverter(List<Organization> bundleOrganizations) {
        this.bundleOrganizations = bundleOrganizations;
        this.fieldsListOrganization = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for(Organization organization : bundleOrganizations) {
            OrganizationClass oc = new OrganizationClass();
            String[] parts;

            parts = organization.getId().split("/");
            oc.setId(parts[5]);

            oc.setName(organization.getName());

            oc.setCity(organization.getAddress().get(0).getCity());

            oc.setState(organization.getAddress().get(0).getState());

            oc.setPhone(organization.getTelecom().get(0).getValue());

            fieldsListOrganization.add(oc);
        }

        if (fieldsListOrganization.isEmpty()) {
            OrganizationClass oc = new OrganizationClass();
            oc.setId("N/A");
            oc.setName("N/A");
            oc.setCity("N/A");
            oc.setState("N/A");
            oc.setPhone("N/A");
            oc.setId("N/A");
            oc.setVuoto(true);
            fieldsListOrganization.add(oc);
        }
    }

    @Setter
    @Getter
    public static class OrganizationClass {
        private String id;
        private String name;
        private String city;
        private String state;
        private String phone;
        private Boolean vuoto;

        public OrganizationClass() {
            this.id = "";
            this.name = "";
            this.city = "";
            this.state = "";
            this.phone = "";
            this.vuoto = false;
        }

        @Override
        public String toString() {
            return "OrganizationClass{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    ", phone='" + phone + '\'' +
                    ", vuoto=" + vuoto +
                    '}';
        }

    }
}
