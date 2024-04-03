package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.Observation;

import java.util.List;

public class ObservationsConverter extends BaseConverter {

    private final List<Observation> bundleObservations;
    @Getter
    @FXML
    private final ObservableList<ObservationClass> fieldsListObservation;

    public ObservationsConverter(List<Observation> bundleObservations) {
        this.bundleObservations = bundleObservations;
        this.fieldsListObservation = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for(Observation observation : bundleObservations) {
            ObservationClass ob = new ObservationClass();
            String[] parts;

            ob.setCode(observation.getCode().getCodingFirstRep().getCode());

            ob.setDescription(observation.getCode().getCodingFirstRep().getDisplay());

            if (observation.getValue() instanceof org.hl7.fhir.r4.model.Quantity) {
                ob.setValue(String.valueOf(((org.hl7.fhir.r4.model.Quantity) observation.getValue()).getValue()));
            } else if (observation.getValue() instanceof org.hl7.fhir.r4.model.IntegerType) {
                ob.setValue(String.valueOf(((org.hl7.fhir.r4.model.IntegerType) observation.getValue()).getValue()));
            } else if (observation.getValue() instanceof org.hl7.fhir.r4.model.StringType) {
                ob.setValue(((org.hl7.fhir.r4.model.StringType) observation.getValue()).getValue());
            } else {
                ob.setValue("---");
            }

            parts = observation.getEffectiveDateTimeType().getValueAsString().split("T");
            ob.setDate(parts[0]);

            parts = observation.getSubject().getReference().split("/");
            ob.setPatient(parts[1]);

            fieldsListObservation.add(ob);
        }

        if (fieldsListObservation.isEmpty()) {
            ObservationClass ob = new ObservationClass();
            ob.setCode("N/A");
            ob.setDescription("N/A");
            ob.setValue("N/A");
            ob.setDate("N/A");
            ob.setPatient("N/A");
            ob.setVuoto(true);
            fieldsListObservation.add(ob);
        }
    }

    @Setter
    @Getter
    public static class ObservationClass {
        private String code;
        private String description;
        private String value;
        private String date;
        private String patient;
        private Boolean vuoto;

        public ObservationClass() {
            this.code = "";
            this.description = "";
            this.value = "";
            this.date = "";
            this.patient = "";
            this.vuoto = false;
        }

        @Override
        public String toString() {
            return "ObservationClass{" +
                    "code='" + code + '\'' +
                    ", description='" + description + '\'' +
                    ", value='" + value + '\'' +
                    ", date='" + date + '\'' +
                    ", patient='" + patient + '\'' +
                    ", vuoto=" + vuoto +
                    '}';
        }
    }
}
