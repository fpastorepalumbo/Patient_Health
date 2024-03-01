package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class PatientConverter extends BaseConverter {

    private List<Patient> Boundlepatients;
@FXML
    private ObservableList<PatientClass> listaCampiPatient;

    public PatientConverter(List<Patient> Boundlepatients) {
        this.Boundlepatients = Boundlepatients;
        this.listaCampiPatient = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {
        int i = 0;
        for(Patient patient : Boundlepatients) {
            PatientClass pc = new PatientClass();

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


            if (patient.getMaritalStatus().getCoding().isEmpty()) {
                pc.setMarital("---");
            } else {
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
                // pc.setMarital(patient.getMaritalStatus().getText()); //qua non stampa bene
            }
            pc.setGender(patient.getGender().toString());
            pc.setBirthplace(patient.getGender().toString()); //ADDO CAZZ STA BIRTHPLACE
            pc.setAddress(patient.getAddress().get(0).getLine().get(0).toString());
            pc.setCity(patient.getAddress().get(0).getCity());
            pc.setState(patient.getAddress().get(0).getState());
            pc.setExpenses(patient.getExtension().get(0).getValue().toString()); //luigi non lo carica mai
            pc.setCoverage(patient.getExtension().get(1).getValue().toString()); //luigi non lo carica mai

            listaCampiPatient.add(pc);
            //System.out.println( i + "\n");

            //System.out.println(listaCampiPatient.toString() +"\n"+ i + "\n\n");

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
        private String expenses;
        private String coverage;

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
            this.expenses = "";
            this.coverage = "";
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

        public String getExpenses() {
            return expenses;
        }

        public void setExpenses(String expenses) {
            this.expenses = expenses;
        }

        public String getCoverage() {
            return coverage;
        }

        public void setCoverage(String coverage) {
            this.coverage = coverage;
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
                    ", expenses='" + expenses + '\'' +
                    ", coverage='" + coverage + '\'' +
                    '}';
        }
    }

}
