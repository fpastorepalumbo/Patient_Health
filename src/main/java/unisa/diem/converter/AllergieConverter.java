package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.Patient;

import java.util.List;

public class AllergieConverter extends BaseConverter{

    private List<AllergyIntolerance> boundleAllergies;
    int i = 0;
    @FXML
    private ObservableList<AllergieClass> listaCampiAllergie;

    public AllergieConverter(List<AllergyIntolerance> boundleAllergies) {
        this.boundleAllergies = boundleAllergies;
        this.listaCampiAllergie = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for (AllergyIntolerance allergyIntolerance : boundleAllergies) {
            AllergieClass ac = new AllergieClass();

            ac.setCode(allergyIntolerance.getCode().getText());
            ac.setDescription(allergyIntolerance.getCode().getCoding().get(0).getDisplay());
            ac.setStartDate(allergyIntolerance.getOnsetDateTimeType().getValueAsString());
            ac.setStopDate(allergyIntolerance.getRecordedDate().toString());
            ac.setEncounter(allergyIntolerance.getEncounter().getReference());

            listaCampiAllergie.add(ac);
        }

        if (listaCampiAllergie.isEmpty()) {
            System.out.println("No Allergie found in AllergieConverter");
        }

    }

    public ObservableList<AllergieClass> getListaCampiAllergie() {
        return listaCampiAllergie;
    }
    public class AllergieClass {
        private String code;
        private String description;
        private String startDate;
        private String stopDate;
        private String encounter;

        public AllergieClass() {
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
