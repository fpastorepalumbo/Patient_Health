package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.Procedure;

import java.util.List;

public class ProceduresConverter extends BaseConverter {

    private List<Procedure> boundleProcedures;
    @FXML
    private ObservableList<ProcedureClass> listaCampiProcedure;

    public ProceduresConverter(List<Procedure> boundleProcedures) {
        this.boundleProcedures = boundleProcedures;
        this.listaCampiProcedure = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for(Procedure procedure : boundleProcedures) {
            ProcedureClass pr = new ProcedureClass();

            pr.setCode(procedure.getCode().getCoding().get(0).getCode());
            pr.setDescription(procedure.getCode().getCoding().get(0).getDisplay());
            pr.setDate(procedure.getPerformedDateTimeType().getValueAsString());
            //pr.setCost(procedure.getCost().getValue().toString());
            pr.setPatient(procedure.getSubject().getReference());

            listaCampiProcedure.add(pr);
        }

        if (listaCampiProcedure.isEmpty()) {
            ProcedureClass pr = new ProcedureClass();
            pr.setCode("N/A");
            pr.setDescription("N/A");
            pr.setDate("N/A");
            pr.setCost("N/A");
            pr.setPatient("N/A");
            listaCampiProcedure.add(pr);
        }
    }

    public ObservableList<ProcedureClass> getListaCampiProcedure() {
        return listaCampiProcedure;
    }

    public class ProcedureClass {
        private String code;
        private String description;
        private String date;
        private String cost;
        private String patient;

        public ProcedureClass() {
            this.code = "";
            this.description = "";
            this.date = "";
            this.cost = "";
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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getPatient() {
            return patient;
        }

        public void setPatient(String patient) {
            this.patient = patient;
        }

        @Override
        public String toString() {
            return "ProcedureClass{" +
                    "code='" + code + '\'' +
                    ", description='" + description + '\'' +
                    ", date='" + date + '\'' +
                    ", cost='" + cost + '\'' +
                    ", patient='" + patient + '\'' +
                    '}';
        }
    }
}
