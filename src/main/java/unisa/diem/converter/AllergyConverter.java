package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.AllergyIntolerance;

import java.util.List;

public class AllergyConverter extends BaseConverter{

    private List<AllergyIntolerance> boundleAllergies;
    @FXML
    private ObservableList<AllergyClass> listaCampiAllergie;

    public AllergyConverter(List<AllergyIntolerance> boundleAllergies) {
        this.boundleAllergies = boundleAllergies;
        this.listaCampiAllergie = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for (AllergyIntolerance allergyIntolerance : boundleAllergies) {
            AllergyClass ac = new AllergyClass();

            ac.setCode(allergyIntolerance.getCode().getCoding().get(0).getCode());
            ac.setDescription(allergyIntolerance.getCode().getCoding().get(0).getDisplay());
            ac.setStartDate(allergyIntolerance.getOnsetDateTimeType().getValueAsString());
            if (allergyIntolerance.hasLastOccurrence()) {
                String stop = allergyIntolerance.getLastOccurrence().toString();
                String[] parts = stop.split(" ");
                ac.setStopDate(parts[5] + "-" + parts[1] + "-" + parts[2]);
            }
            else
                ac.setStopDate("---");
            String code = allergyIntolerance.getEncounter().getReference();
            String[] parts = code.split("/");
            String encounter = parts[1];
            ac.setEncounter(encounter);

            listaCampiAllergie.add(ac);
        }

        if (listaCampiAllergie.isEmpty()) {
            throw new RuntimeException("listaCampiAllergie is empty");
        }
    }

    public ObservableList<AllergyClass> getListaCampiAllergie() {
        return listaCampiAllergie;
    }
    public class AllergyClass {
        private String code;
        private String description;
        private String startDate;
        private String stopDate;
        private String encounter;

        public AllergyClass() {
            this.code = "";
            this.description = "";
            this.startDate = "";
            this.stopDate = "";
            this.encounter = "";
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

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getStopDate() {
            return stopDate;
        }

        public void setStopDate(String stopDate) {
            this.stopDate = stopDate;
        }

        public String getEncounter() {
            return encounter;
        }

        public void setEncounter(String encounter) {
            this.encounter = encounter;
        }
    }
}
