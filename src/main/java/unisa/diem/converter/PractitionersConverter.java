package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Practitioner;

import java.util.List;

public class PractitionersConverter extends BaseConverter {

    private final List<Practitioner> bundlePractitioners;
    @Getter
    @FXML
    private final ObservableList<PractitionerClass> fieldsListPractitioner;

    public PractitionersConverter(List<Practitioner> bundlePractitioners) {
        this.bundlePractitioners = bundlePractitioners;
        this.fieldsListPractitioner = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for(Practitioner practitioner : bundlePractitioners) {
            PractitionerClass pr = new PractitionerClass();
            String[] parts;

            parts = practitioner.getId().split("/");
            pr.setId(parts[5]);

            pr.setName(practitioner.getName().get(0).getText());

            pr.setGender(practitioner.getGender().getDisplay());

            pr.setSpecialty(practitioner.getQualification().get(0).getCode().getCoding().get(0).getDisplay());

            parts = practitioner.getQualification().get(0).getIssuer().getReference().split("/");
            pr.setOrganization(parts[1]);

            pr.setAddress(practitioner.getAddress().get(0).getLine().get(0).toString());

            pr.setCity(practitioner.getAddress().get(0).getCity());

            pr.setState(practitioner.getAddress().get(0).getState());

            fieldsListPractitioner.add(pr);
        }

        if (fieldsListPractitioner.isEmpty()) {
            PractitionerClass pr = new PractitionerClass();
            pr.setName("N/A");
            pr.setGender("N/A");
            pr.setSpecialty("N/A");
            pr.setOrganization("N/A");
            pr.setAddress("N/A");
            pr.setCity("N/A");
            pr.setState("N/A");
            pr.setVuoto(true);
            fieldsListPractitioner.add(pr);
        }
    }

    @Setter
    @Getter
    public static class PractitionerClass {
        private String id;
        private String name;
        private String gender;
        private String specialty;
        private String organization;
        private String address;
        private String city;
        private String state;
        private Boolean vuoto;

        public PractitionerClass() {
            this.id = "";
            this.name = "";
            this.gender = "";
            this.specialty = "";
            this.organization = "";
            this.address = "";
            this.city = "";
            this.state = "";
            this.vuoto = false;
        }

        @Override
        public String toString() {
            return "PractitionerClass{" +
                    "id ='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", gender='" + gender + '\'' +
                    ", specialty='" + specialty + '\'' +
                    ", organization='" + organization + '\'' +
                    ", address='" + address + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    ", vuoto=" + vuoto +
                    '}';
        }
    }
}
