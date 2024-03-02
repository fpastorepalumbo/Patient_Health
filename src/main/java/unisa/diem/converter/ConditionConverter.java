package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.Condition;
import org.hl7.fhir.r4.model.Patient;

import java.util.List;

public class ConditionConverter extends BaseConverter{

    private List<Condition> boundleConditions;
    int i = 0;
    @FXML
    private ObservableList<ConditionClass> listaCampiCondition;

    public ConditionConverter(List<Condition> boundleConditions) {
        this.boundleConditions = boundleConditions;
        this.listaCampiCondition = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {
        for (Condition condition : boundleConditions) {
            ConditionClass cc = new ConditionClass();

            cc.setCode(condition.getCode().getText());
            cc.setDescription(condition.getCode().getCoding().get(0).getDisplay());
            cc.setStartDate(condition.getOnsetDateTimeType().getValueAsString());
            cc.setStopDate(condition.getRecordedDate().toString());
            cc.setEncounter(condition.getEncounter().getReference());

            listaCampiCondition.add(cc);
        }

        if (listaCampiCondition.isEmpty()) {
            System.out.println("No Condition found in ConditionConverter");
        }

    }

    public ObservableList<ConditionClass> getListaCampiCondition() {
        return listaCampiCondition;
    }
    public class ConditionClass {
        private String code;
        private String description;
        private String startDate;
        private String stopDate;
        private String encounter;

        public ConditionClass() {
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
