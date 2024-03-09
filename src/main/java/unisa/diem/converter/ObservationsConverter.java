package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.Observation;

import java.util.List;

public class ObservationsConverter extends BaseConverter {

    private List<Observation> boundleObservations;
    @FXML
    private ObservableList<ObservationClass> listaCampiObservation;

    public ObservationsConverter(List<Observation> boundleObservations) {
        this.boundleObservations = boundleObservations;
        this.listaCampiObservation = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for(Observation observation : boundleObservations) {
            ObservationClass ob = new ObservationClass();

            ob.setCode(observation.getCode().getCodingFirstRep().getCode());
            ob.setDescription(observation.getCode().getCodingFirstRep().getDisplay());
            ob.setValue(observation.getValue().toString());
            ob.setDate(observation.getIssued().toString());
            ob.setPatient(observation.getSubject().getReference());

            listaCampiObservation.add(ob);
        }

        if (listaCampiObservation.isEmpty()) {
            throw new RuntimeException("listaCampiObservation is empty");
        }
    }

    public ObservableList<ObservationClass> getListaCampiObservation() {
        return listaCampiObservation;
    }

    public class ObservationClass {
        private String code;
        private String description;
        private String value;
        private String date;
        private String patient;

        public ObservationClass() {
            this.code = "";
            this.description = "";
            this.value = "";
            this.date = "";
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

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPatient() {
            return patient;
        }

        public void setPatient(String patient) {
            this.patient = patient;
        }

        @Override
        public String toString() {
            return "ObservationClass{" +
                    "code='" + code + '\'' +
                    ", description='" + description + '\'' +
                    ", value='" + value + '\'' +
                    ", date='" + date + '\'' +
                    ", patient='" + patient + '\'' +
                    '}';
        }
    }
}
