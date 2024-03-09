package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.Immunization;

import java.util.List;

public class ImmunizationConverter extends BaseConverter{

    private List<Immunization> boundleImmunizations;
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
            ic.setCode(immunization.getVaccineCode().getCoding().get(0).getCode());
            ic.setDescription(immunization.getVaccineCode().getCoding().get(0).getDisplay());
            String date = immunization.getRecorded().toString();
            String[] parts = date.split(" ");
            ic.setDate(parts[5] + "-" + parts[1] + "-" + parts[2]);
            String code = immunization.getEncounter().getReference();
            String[] parts1 = code.split("/");
            String encounter = parts1[1];
            ic.setEncounter(encounter);

            listaCampiImmunization.add(ic);
        }

        if (listaCampiImmunization.isEmpty()) {
            throw new RuntimeException("listaCampiImmunization is empty");
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
