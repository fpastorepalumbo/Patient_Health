package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.AllergyIntolerance;

import java.util.List;

public class AllergyConverter extends BaseConverter{

    private final List<AllergyIntolerance> bundleAllergies;
    @Getter
    @FXML
    private final ObservableList<AllergyClass> fieldsListAllergy;

    public AllergyConverter(List<AllergyIntolerance> bundleAllergies) {
        this.bundleAllergies = bundleAllergies;
        this.fieldsListAllergy = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for (AllergyIntolerance allergyIntolerance : bundleAllergies) {
            AllergyClass ac = new AllergyClass();
            String[] parts;

            ac.setCode(allergyIntolerance.getCode().getCoding().get(0).getCode());

            ac.setDescription(allergyIntolerance.getCode().getCoding().get(0).getDisplay());

            ac.setStartDate(allergyIntolerance.getOnsetDateTimeType().getValueAsString());

            if (allergyIntolerance.hasLastOccurrence()) {
                parts = allergyIntolerance.getLastOccurrence().toString().split(" ");
                ac.setStopDate(parts[5] + "-" + parts[1] + "-" + parts[2]);
            } else
                ac.setStopDate("---");

            parts = allergyIntolerance.getEncounter().getReference().split("/");
            ac.setEncounter(parts[1]);

            fieldsListAllergy.add(ac);
        }

        if (fieldsListAllergy.isEmpty()) {
            AllergyClass ac = new AllergyClass();
            ac.setCode("N/A");
            ac.setDescription("N/A");
            ac.setStartDate("N/A");
            ac.setStopDate("N/A");
            ac.setEncounter("N/A");
            ac.setVuoto(true);
            fieldsListAllergy.add(ac);
        }
    }

    @Setter
    @Getter
    public static class AllergyClass {
        private String code;
        private String description;
        private String startDate;
        private String stopDate;
        private String encounter;

        private Boolean vuoto;

        public AllergyClass() {
            this.code = "";
            this.description = "";
            this.startDate = "";
            this.stopDate = "";
            this.encounter = "";
            this.vuoto = false;
        }

        @Override
        public String toString() {
            return "AllergyClass{" +
                    "code='" + code + '\'' +
                    ", description='" + description + '\'' +
                    ", startDate='" + startDate + '\'' +
                    ", stopDate='" + stopDate + '\'' +
                    ", encounter='" + encounter + '\'' +
                    ", vuoto=" + vuoto +
                    '}';
        }
    }
}
