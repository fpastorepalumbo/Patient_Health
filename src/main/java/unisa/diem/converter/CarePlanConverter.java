package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.CarePlan;

import java.util.List;

public class CarePlanConverter extends BaseConverter{

    private final List<CarePlan> bundleCarePlans;
    @Getter
    @FXML
    private final ObservableList<CarePlanClass> fieldListCarePlan;

    public CarePlanConverter(List<CarePlan> bundleCarePlans) {
        this.bundleCarePlans = bundleCarePlans;
        this.fieldListCarePlan = FXCollections.observableArrayList();
    }
    @Override
    public void convert() {
        for (CarePlan carePlan : bundleCarePlans) {
            CarePlanClass cpc = new CarePlanClass();

            cpc.setCode(carePlan.getIdentifier().get(0).getValue());

            cpc.setDescription(carePlan.getCategory().get(0).getCoding().get(0).getDisplay());

            for (CarePlan.CarePlanActivityComponent activity : carePlan.getActivity()) {
                cpc.setReasonCode(activity.getDetail().getReasonCode().get(0).getCoding().get(0).getCode());
                cpc.setReasonDescription(activity.getDetail().getReasonCode().get(0).getCoding().get(0).getDisplay());
            }

            fieldListCarePlan.add(cpc);
        }

        if (fieldListCarePlan.isEmpty()) {
            CarePlanClass cpc = new CarePlanClass();
            cpc.setCode("N/A");
            cpc.setDescription("N/A");
            cpc.setReasonCode("N/A");
            cpc.setReasonDescription("N/A");
            fieldListCarePlan.add(cpc);
        }

    }

    @Setter
    @Getter
    public static class CarePlanClass {
        private String code;
        private String description;
        private String reasonCode;
        private String reasonDescription;

        public CarePlanClass() {
            this.code = "";
            this.description = "";
            this.reasonCode = "";
            this.reasonDescription = "";
        }
    }
}
