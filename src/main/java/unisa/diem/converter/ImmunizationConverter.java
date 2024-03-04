package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.DateType;
import org.hl7.fhir.r4.model.Immunization;

import java.util.Date;
import java.util.List;

public class ImmunizationConverter extends BaseConverter{

    private List<Immunization> boundleImmunizations;
    int i = 0;
    @FXML
    private ObservableList<ImmunizationClass> listaCampiImmunization;

    public ImmunizationConverter(List<Immunization> boundleImmunizations) {
        this.boundleImmunizations = boundleImmunizations;
        this.listaCampiImmunization = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {
        for (Immunization immunization : boundleImmunizations) {
            ImmunizationClass ic = new ImmunizationClass();
            //print immunization code
            ic.setCode(immunization.getVaccineCode().getCoding().get(0).getCode());
            //print immunization description
            ic.setDescription(immunization.getVaccineCode().getCoding().get(0).getDisplay());
            //print immunization date
            ic.setDate(immunization.getRecorded().toString());
            //print immunization encounter
            String code = immunization.getEncounter().getReference();
            String[] parts = code.split("/");
            String encounter = parts[1];
            ic.setEncounter(encounter);

            listaCampiImmunization.add(ic);
        }

        if (listaCampiImmunization.isEmpty()) {
            System.out.println("No Immunization found in ImmunizationConverter");
        }

    }

    public ObservableList<ImmunizationClass> getListaCampiImmunization() {
        return listaCampiImmunization;
    }

    public class ImmunizationClass {
        private String code;
        private String description;
        private String date;
        private String encounter;

        public ImmunizationClass() {
            this.code = "";
            this.description = "";
            this.date = "";
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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getEncounter() {
            return encounter;
        }

        public void setEncounter(String encounter) {
            this.encounter = encounter;
        }
    }
}
