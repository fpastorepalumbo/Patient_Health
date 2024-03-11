package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.CarePlan;

import java.util.List;

public class CarePlanConverter extends BaseConverter{

    private List<CarePlan> boundleCarePlans;
    @FXML
    private ObservableList<CarePlanClass> listaCampiCarePlan;

    public CarePlanConverter(List<CarePlan> boundleCarePlans) {
        this.boundleCarePlans = boundleCarePlans;
        this.listaCampiCarePlan = FXCollections.observableArrayList();
    }
    @Override
    public void convert() {
        for (CarePlan carePlan : boundleCarePlans) {
            CarePlanClass cpc = new CarePlanClass();

            cpc.setCode(carePlan.getIdentifier().get(0).getValue());
            cpc.setDescription(carePlan.getCategory().get(0).getCoding().get(0).getDisplay());
            for (CarePlan.CarePlanActivityComponent activity : carePlan.getActivity()) {
                cpc.setReasonCode(activity.getDetail().getReasonCode().get(0).getCoding().get(0).getCode());
                cpc.setReasonDescription(activity.getDetail().getReasonCode().get(0).getCoding().get(0).getDisplay());
            }

            listaCampiCarePlan.add(cpc);
        }

        if (listaCampiCarePlan.isEmpty()) {
            CarePlanClass cpc = new CarePlanClass();
            cpc.setCode("N/A");
            cpc.setDescription("N/A");
            cpc.setReasonCode("N/A");
            cpc.setReasonDescription("N/A");
            listaCampiCarePlan.add(cpc);
        }

    }

    public ObservableList<CarePlanClass> getListaCampiCarePlan() {
        return listaCampiCarePlan;
    }
    public class CarePlanClass {
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

        public String getReasonCode() {
            return reasonCode;
        }

        public void setReasonCode(String reasonCode) {
            this.reasonCode = reasonCode;
        }

        public String getReasonDescription() {
            return reasonDescription;
        }

        public void setReasonDescription(String reasonDescription) {
            this.reasonDescription = reasonDescription;
        }
    }
}
