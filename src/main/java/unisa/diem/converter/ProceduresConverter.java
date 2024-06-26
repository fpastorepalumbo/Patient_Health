package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Procedure;

import java.util.List;

public class ProceduresConverter extends BaseConverter {

    private final List<Procedure> bundleProcedures;
    @Getter
    @FXML
    private final ObservableList<ProcedureClass> fieldsListProcedure;

    public ProceduresConverter(List<Procedure> bundleProcedures) {
        this.bundleProcedures = bundleProcedures;
        this.fieldsListProcedure = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for(Procedure procedure : bundleProcedures) {
            ProcedureClass pr = new ProcedureClass();
            String[] parts;

            pr.setCode(procedure.getCode().getCoding().get(0).getCode());

            pr.setDescription(procedure.getCode().getCoding().get(0).getDisplay());

            parts = procedure.getPerformedDateTimeType().getValueAsString().split("T");
            pr.setDate(parts[0]);

            parts = procedure.getSubject().getReference().split("/");
            pr.setPatient(parts[1]);

            fieldsListProcedure.add(pr);
        }

        if (fieldsListProcedure.isEmpty()) {
            ProcedureClass pr = new ProcedureClass();
            pr.setCode("N/A");
            pr.setDescription("N/A");
            pr.setDate("N/A");
            pr.setPatient("N/A");
            pr.setVuoto(true);
            fieldsListProcedure.add(pr);
        }
    }

    @Setter
    @Getter
    public static class ProcedureClass {
        private String code;
        private String description;
        private String date;
        private String patient;
        private Boolean vuoto;

        public ProcedureClass() {
            this.code = "";
            this.description = "";
            this.date = "";
            this.patient = "";
            this.vuoto = false;
        }

        @Override
        public String toString() {
            return "ProcedureClass{" +
                    "code='" + code + '\'' +
                    ", description='" + description + '\'' +
                    ", date='" + date + '\'' +
                    ", patient='" + patient + '\'' +
                    ", vuoto=" + vuoto +
                    '}';
        }
    }
}
