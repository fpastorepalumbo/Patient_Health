package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.MedicationRequest;

import java.util.List;

public class MedicationRequestsConverter extends BaseConverter{

    private List<MedicationRequest> boundleMedRequests;
    @FXML
    private ObservableList<MedicationRequestsClass> listaCampiMedRequest;

    public MedicationRequestsConverter(List<MedicationRequest> boundleMedRequests) {
        this.boundleMedRequests = boundleMedRequests;
        this.listaCampiMedRequest = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {
        for (MedicationRequest medReq : boundleMedRequests) {
            MedicationRequestsClass mr = new MedicationRequestsClass();

            mr.setCode(medReq.getMedicationCodeableConcept().getCoding().get(0).getCode());
            mr.setDescription(medReq.getMedicationCodeableConcept().getText());
            //mr.setBaseCost(medReq.getDispenseRequest().getInitialFill().getCost().getValue().toString());
            //mr.setCoverage(medReq.getInsurance().get(0).getCoverage().getReference());
            //mr.setTotCost(medReq.getDispenseRequest().getInitialFill().getCost().getValue().toString());
            mr.setStartDate(medReq.getAuthoredOn().toString());
            mr.setStopDate(medReq.getAuthoredOn().toString());
            mr.setPatient(medReq.getSubject().getReference());
            //mr.setPayer(medReq.getInsurance().get(0).getCoverage().getReference());

            listaCampiMedRequest.add(mr);
        }

        if (listaCampiMedRequest.isEmpty()) {
            throw new RuntimeException("listaCampiMedRequest is empty");
        }

    }

    public ObservableList<MedicationRequestsClass> getListaCampiMedRequest() {
        return listaCampiMedRequest;
    }
    public class MedicationRequestsClass {

        private String code;
        private String description;
        private String baseCost;
        private String coverage;
        private String totCost;

        private String startDate;
        private String stopDate;
        private String patient;
        private String payer;

        public MedicationRequestsClass() {
            this.code = "";
            this.description = "";
            this.baseCost = "";
            this.coverage = "";
            this.totCost = "";
            this.startDate = "";
            this.stopDate = "";
            this.patient = "";
            this.payer = "";
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

        public String getBaseCost() {
            return baseCost;
        }

        public void setBaseCost(String baseCost) {
            this.baseCost = baseCost;
        }

        public String getCoverage() {
            return coverage;
        }

        public void setCoverage(String coverage) {
            this.coverage = coverage;
        }

        public String getTotCost() {
            return totCost;
        }

        public void setTotCost(String totCost) {
            this.totCost = totCost;
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

        public String getPatient() {
            return patient;
        }

        public void setPatient(String patient) {
            this.patient = patient;
        }

        public String getPayer() {
            return payer;
        }

        public void setPayer(String payer) {
            this.payer = payer;
        }

        @Override
        public String toString() {
            return "ConditionClass{" +
                    "code='" + code + '\'' +
                    ", description='" + description + '\'' +
                    ", baseCost='" + baseCost + '\'' +
                    ", coverage='" + coverage + '\'' +
                    ", totCost='" + totCost + '\'' +
                    ", startDate='" + startDate + '\'' +
                    ", stopDate='" + stopDate + '\'' +
                    ", patient='" + patient + '\'' +
                    ", payer='" + payer + '\'' +
                    '}';
        }
    }
}
