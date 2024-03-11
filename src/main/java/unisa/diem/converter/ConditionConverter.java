package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Condition;

import java.util.List;

public class ConditionConverter extends BaseConverter{

    private final List<Condition> bundleConditions;
    @Getter
    @FXML
    private final ObservableList<ConditionClass> fieldsListCondition;

    public ConditionConverter(List<Condition> bundleConditions) {
        this.bundleConditions = bundleConditions;
        this.fieldsListCondition = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {
        for (Condition condition : bundleConditions) {
            ConditionClass cc = new ConditionClass();
            String[] parts;

            cc.setCode(condition.getCode().getCoding().get(0).getCode());

            cc.setDescription(condition.getCode().getCoding().get(0).getDisplay());

            cc.setStartDate(condition.getOnsetDateTimeType().getValueAsString());

            if (condition.hasAbatementDateTimeType()) {
                cc.setStopDate(condition.getAbatementDateTimeType().getValueAsString());
            }
            else
                cc.setStopDate("---");

            parts = condition.getEncounter().getReference().split("/");
            cc.setEncounter(parts[1]);

            fieldsListCondition.add(cc);
        }

        if (fieldsListCondition.isEmpty()) {
            ConditionClass cc = new ConditionClass();
            cc.setCode("N/A");
            cc.setDescription("N/A");
            cc.setStartDate("N/A");
            cc.setStopDate("N/A");
            cc.setEncounter("N/A");
            fieldsListCondition.add(cc);
        }
    }

    @Setter
    @Getter
    public static class ConditionClass {
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

        @Override
        public String toString() {
            return "ConditionClass{" +
                    "code='" + code + '\'' +
                    ", description='" + description + '\'' +
                    ", startDate='" + startDate + '\'' +
                    ", stopDate='" + stopDate + '\'' +
                    ", encounter='" + encounter + '\'' +
                    '}';
        }
    }
}
