package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.Practitioner;

import java.util.List;

public class PractitionersConverter extends BaseConverter {

    private List<Practitioner> boundlePractitioners;
    @FXML
    private ObservableList<PractitionerClass> listaCampiPractitioner;

    public PractitionersConverter(List<Practitioner> boundlePractitioners) {
        this.boundlePractitioners = boundlePractitioners;
        this.listaCampiPractitioner = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for(Practitioner practitioner : boundlePractitioners) {
            PractitionerClass pr = new PractitionerClass();

            pr.setName(practitioner.getNameFirstRep().getGivenAsSingleString() + " " + practitioner.getNameFirstRep().getFamily());
            pr.setGender(practitioner.getGender().getDisplay());
            pr.setSpecialty(practitioner.getQualification().get(0).getCode().getText());
            pr.setOrganization(practitioner.getQualification().get(0).getIssuer().getDisplay());
            pr.setAddress(practitioner.getAddress().get(0).getLine().get(0).toString());
            pr.setCity(practitioner.getAddress().get(0).getCity());
            pr.setState(practitioner.getAddress().get(0).getState());

            listaCampiPractitioner.add(pr);
        }

        if (listaCampiPractitioner.isEmpty()) {
            throw new RuntimeException("listaCampiPractitioner is empty");
        }
    }

    public ObservableList<PractitionerClass> getListaCampiPractitioner() {
        return listaCampiPractitioner;
    }

    public class PractitionerClass {
        private String name;
        private String gender;
        private String specialty;
        private String organization;
        private String address;
        private String city;
        private String state;



        public PractitionerClass() {
            this.name = "";
            this.gender = "";
            this.specialty = "";
            this.organization = "";
            this.address = "";
            this.city = "";
            this.state = "";
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public String getSpecialty() {
            return specialty;
        }

        public String getOrganization() {
            return organization;
        }

        public String getAddress() {
            return address;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setState(String state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return "PractitionerClass{" +
                    "name='" + name + '\'' +
                    ", gender='" + gender + '\'' +
                    ", specialty='" + specialty + '\'' +
                    ", organization='" + organization + '\'' +
                    ", address='" + address + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    '}';
        }
    }
}
