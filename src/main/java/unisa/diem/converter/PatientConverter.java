package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.*;

import java.util.List;
public class PatientConverter extends BaseConverter {

    private List<Patient> boundlePatients;
    int i = 0;
    @FXML
    private ObservableList<PatientClass> listaCampiPatient;

    public PatientConverter(List<Patient> boundlePatients) {
        this.boundlePatients = boundlePatients;
        this.listaCampiPatient = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for(Patient patient : boundlePatients) {
            PatientClass pc = new PatientClass();

            pc.setId(patient.getIdentifier().get(0).getValue());
            pc.setName(patient.getNameFirstRep().getGivenAsSingleString());
            pc.setSurname(patient.getNameFirstRep().getFamily());
            DateType birthdate = patient.getBirthDateElement();
            String date = birthdate.getDay() + "/" + birthdate.getMonth() + "/" + birthdate.getYear();
            pc.setBirthdate(date);
            String temp ="";
            if ( !patient.hasDeceasedDateTimeType()){
                temp = "---";
            }else {
                DateTimeType d = patient.getDeceasedDateTimeType();
                temp = d.getDay() + "/" + d.getMonth() + "/" + d.getYear();
            }
            pc.setDeathdate(temp);
            pc.setSsn(patient.getIdentifier().get(1).getValue());
            if (patient.hasMaritalStatus())
                pc.setMarital(patient.getMaritalStatus().getCodingFirstRep().getCode());
            else
                pc.setMarital("Unknown");
            /*
            if (patient.getMaritalStatus().fhirType() == "M") {
                pc.setMarital("Married");
            } else if (patient.getMaritalStatus().getText() == "S") {
                pc.setMarital("Never Married");
            } else if (patient.getMaritalStatus().getText() == "W") {
                pc.setMarital("Widowed");
            } else if (patient.getMaritalStatus().getText() == "U") {
                pc.setMarital("Unmarried");
            } else if (patient.getMaritalStatus().getText() == "T") {
                pc.setMarital("Domestic partner");
            } else if (patient.getMaritalStatus().getText() == "P") {
                pc.setMarital("Polygamus");
            } else if (patient.getMaritalStatus().getText() == "C") {
                pc.setMarital("Common law");
            } else if (patient.getMaritalStatus().getText() == "L") {
                pc.setMarital("Legally Separated");
            } else if (patient.getMaritalStatus().getText() == "I") {
                pc.setMarital("Interlocutory");
            } else if (patient.getMaritalStatus().getText() == "D") {
                pc.setMarital("Divorced");
            } else if (patient.getMaritalStatus().getText() == "A") {
                pc.setMarital("Annulled");
            } else {
                pc.setMarital("Unknown");
            }
             */

            pc.setGender(patient.getGender().toString());
            String place = "";
            for (Extension ext : patient.getExtension()) {
                if (ext.getUrl().equals("http://hl7.org/fhir/StructureDefinition/patient-birthPlace")) {
                    Address value = (Address) ext.getValue();
                    place = value.getCity() + " " + value.getState() + " " + value.getCountry();
                }
                pc.setBirthplace(place);
            }
            pc.setAddress(patient.getAddress().get(0).getLine().get(0).toString());
            pc.setCity(patient.getAddress().get(0).getCity());
            pc.setState(patient.getAddress().get(0).getState());

            listaCampiPatient.add(pc);
            System.out.println( i + "\n");
            // System.out.println(listaCampiPatient.toString() +"\n"+ i + "\n\n");
            i++;
        }
        /*
        int k=0;
        for(Patient patient : Boundlepatients) {
            System.out.println(listaCampiPatient.toString() + "\n" + "i= "+ i +" k= "+ k + "\n");
            k++;
        }
        */
        if (listaCampiPatient.isEmpty()) {
            throw new RuntimeException("MANNAGG O CAZZ MANNAGG");
        }
    }

    public ObservableList<PatientClass> getListaCampiPatient() {
        return listaCampiPatient;
    }

    public class PatientClass {
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


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getBirthdate() {
            return birthdate;
        }
        public void setBirthdate(String birthdate) {
            this.birthdate = birthdate;
        }

        public String getDeathdate() {
            return deathdate;
        }

        public void setDeathdate(String deathdate) {
            this.deathdate = deathdate;
        }

        public String getSsn() {
            return ssn;
        }

        public void setSsn(String ssn) {
            this.ssn = ssn;
        }

        public String getMarital() {
            return marital;
        }

        public void setMarital(String marital) {
            this.marital = marital;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBirthplace() {
            return birthplace;
        }

        public void setBirthplace(String birthplace) {
            this.birthplace = birthplace;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
