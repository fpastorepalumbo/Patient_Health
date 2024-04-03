package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Immunization;

import java.util.List;

public class ImmunizationConverter extends BaseConverter{

    private final List<Immunization> bundleImmunizations;
    @Getter
    @FXML
    private final ObservableList<ImmunizationClass> fieldsListImmunization;

    public ImmunizationConverter(List<Immunization> bundleImmunizations) {
        this.bundleImmunizations = bundleImmunizations;
        this.fieldsListImmunization = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {
        for (Immunization immunization : bundleImmunizations) {
            ImmunizationClass ic = new ImmunizationClass();
            String[] parts;

            ic.setCode(immunization.getVaccineCode().getCoding().get(0).getCode());

            ic.setDescription(immunization.getVaccineCode().getCoding().get(0).getDisplay());

            parts = immunization.getRecorded().toString().split(" ");
            ic.setDate(parts[5] + "-" + parts[1] + "-" + parts[2]);

            parts = immunization.getEncounter().getReference().split("/");
            String encounter = parts[1];

            ic.setEncounter(encounter);

            fieldsListImmunization.add(ic);
        }

        if (fieldsListImmunization.isEmpty()) {
            ImmunizationClass ic = new ImmunizationClass();
            ic.setCode("N/A");
            ic.setDescription("N/A");
            ic.setDate("N/A");
            ic.setEncounter("N/A");
            ic.setVuoto(true);
            fieldsListImmunization.add(ic);
        }
    }

    @Setter
    @Getter
    public static class ImmunizationClass {
        private String code;
        private String description;
        private String date;
        private String encounter;
        private Boolean vuoto;

        public ImmunizationClass() {
            this.code = "";
            this.description = "";
            this.date = "";
            this.encounter = "";
            this.vuoto = false;
        }

        @Override
        public String toString() {
            return "ImmunizationClass{" +
                    "code='" + code + '\'' +
                    ", description='" + description + '\'' +
                    ", date='" + date + '\'' +
                    ", encounter='" + encounter + '\'' +
                    ", vuoto=" + vuoto +
                    '}';
        }
    }
}
