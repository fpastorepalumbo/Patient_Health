package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.*;

import java.util.List;
public class PatientConverter extends BaseConverter {

    private final List<Patient> bundlePatients;
    @Getter
    @FXML
    private final ObservableList<PatientClass> fieldListPatient;

    public PatientConverter(List<Patient> bundlePatients) {
        this.bundlePatients = bundlePatients;
        this.fieldListPatient = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {
        for(Patient patient : bundlePatients) {
            PatientClass pc = new PatientClass();
            String place = "";
            String[] parts;

            pc.setId(patient.getIdentifier().get(0).getValue());

            pc.setName(patient.getNameFirstRep().getGivenAsSingleString());

            pc.setSurname(patient.getNameFirstRep().getFamily());

            parts = patient.getBirthDate().toString().split(" ");
            pc.setBirthdate(parts[5] + "-" + parts[1] + "-" + parts[2]);

            if (patient.hasDeceasedDateTimeType()){
                parts = patient.getDeceasedDateTimeType().getValueAsString().split("T");
                pc.setDeathdate(parts[0]);
            }
            else
                pc.setDeathdate("---");

            pc.setSsn(patient.getIdentifier().get(1).getValue());

            if (patient.hasMaritalStatus())
                pc.setMarital(patient.getMaritalStatus().getCodingFirstRep().getCode());
            else
                pc.setMarital("Unknown");

            pc.setGender(patient.getGender().toString());

            for (Extension ext : patient.getExtension()) {
                if (ext.getUrl().equals("http://hl7.org/fhir/StructureDefinition/patient-birthPlace")) {
                    Address value = (Address) ext.getValue();
                    place = value.getText();
                }
                pc.setBirthplace(place);
            }

            pc.setAddress(patient.getAddress().get(0).getLine().get(0).toString());

            pc.setCity(patient.getAddress().get(0).getCity());

            pc.setState(patient.getAddress().get(0).getState());

            fieldListPatient.add(pc);
        }
        if (fieldListPatient.isEmpty()) {
            PatientClass pc = new PatientClass();
            pc.setId("N/A");
            pc.setName("N/A");
            pc.setSurname("N/A");
            pc.setBirthdate("N/A");
            pc.setDeathdate("N/A");
            pc.setSsn("N/A");
            pc.setMarital("N/A");
            pc.setGender("N/A");
            pc.setBirthplace("N/A");
            pc.setAddress("N/A");
            pc.setCity("N/A");
            pc.setState("N/A");
            fieldListPatient.add(pc);
        }
    }

    @Setter
    @Getter
    public static class PatientClass {
        private String name;
        private String surname;
        private String birthdate;
        private String deathdate;
        private String ssn;
        private String marital;
        private String gender;
        private String birthplace;
        private String address;
        private String city;
        private String state;
        private String id;

        public PatientClass() {
            this.name = "";
            this.surname = "";
            this.birthdate = "";
            this.deathdate = "";
            this.ssn = "";
            this.marital = "";
            this.gender = "";
            this.birthplace = "";
            this.address = "";
            this.city = "";
            this.state = "";
            this.id = "";
        }

        @Override
        public String toString() {
            return "PatientClass{" +
                    "name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    ", birthdate='" + birthdate + '\'' +
                    ", deathdate='" + deathdate + '\'' +
                    ", ssn='" + ssn + '\'' +
                    ", marital='" + marital + '\'' +
                    ", gender='" + gender + '\'' +
                    ", birthplace='" + birthplace + '\'' +
                    ", address='" + address + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }
}
