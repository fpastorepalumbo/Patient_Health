package unisa.diem.converter;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.ReferenceClientParam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Claim;
import org.hl7.fhir.r4.model.ExplanationOfBenefit;
import org.hl7.fhir.r4.model.MedicationRequest;

import java.util.ArrayList;
import java.util.List;

public class MedicationRequestsConverter extends BaseConverter{

    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);
    private final List<MedicationRequest> bundleMedRequests;
    @Getter
    @FXML
    private final ObservableList<MedicationRequestsClass> fieldsListMedRequest;

    public MedicationRequestsConverter(List<MedicationRequest> bundleMedRequests) {
        this.bundleMedRequests = bundleMedRequests;
        this.fieldsListMedRequest = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {
        for (MedicationRequest medReq : bundleMedRequests) {
            MedicationRequestsClass mr = new MedicationRequestsClass();
            String[] parts;
            String encID = medReq.getEncounter().getReference().split("/")[1];
            Claim claim = getClaim(encID);
            ExplanationOfBenefit eob = getExplanationofBenefit(encID);

            mr.setCode(medReq.getMedicationCodeableConcept().getCoding().get(0).getCode());

            mr.setDescription(medReq.getMedicationCodeableConcept().getCoding().get(0).getDisplay());

            mr.setBaseCost(claim.getItem().get(0).getUnitPrice().getValue().toString());

            mr.setCoverage(eob.getItem().get(0).getAdjudication().get(0).getAmount().getValue().toString());

            mr.setDispenses(claim.getItem().get(0).getQuantity().getValue().toString());

            mr.setTotCost(claim.getItem().get(0).getNet().getValue().toString());

            parts = medReq.getDispenseRequest().getValidityPeriod().getStart().toString().split(" ");
            mr.setStartDate(parts[5] + "-" + parts[1] + "-" + parts[2]);

            if (medReq.getDispenseRequest().getValidityPeriod().hasEnd()) {
                parts = medReq.getDispenseRequest().getValidityPeriod().getEnd().toString().split(" ");
                mr.setStopDate(parts[5] + "-" + parts[1] + "-" + parts[2]);
            } else
                mr.setStopDate("---");

            parts = medReq.getSubject().getReference().split("/");
            mr.setPatient(parts[1]);

            parts = eob.getInsurer().getReference().split("/");
            mr.setPayer(parts[1]);

            fieldsListMedRequest.add(mr);
        }

        if (fieldsListMedRequest.isEmpty()) {
            MedicationRequestsClass mr = new MedicationRequestsClass();
            mr.setCode("N/A");
            mr.setDescription("N/A");
            mr.setBaseCost("N/A");
            mr.setCoverage("N/A");
            mr.setDispenses("N/A");
            mr.setTotCost("N/A");
            mr.setStartDate("N/A");
            mr.setStopDate("N/A");
            mr.setPatient("N/A");
            mr.setPayer("N/A");
            mr.setVuoto(true);
            fieldsListMedRequest.add(mr);
        }
    }

    public Claim getClaim(String encID) {
        Bundle bundle;

        try {
            bundle = (Bundle) client.search().forResource(Claim.class)
                    .where(new ReferenceClientParam("encounter").hasId(encID))
                    .encodedXml()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the Claim");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            if (((Claim) entry.getResource()).hasPrescription())
                return (Claim) entry.getResource();
        }
        return new Claim();
    }

    public ExplanationOfBenefit getExplanationofBenefit(String encID) {
        Bundle bundle;

        try {
            bundle = (Bundle) client.search().forResource(ExplanationOfBenefit.class)
                    .where(new ReferenceClientParam("encounter").hasId(encID))
                    .encodedXml()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the ExplanationOfBenefit");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            if ((entry.getResource()).getId().contains("EM"))
                return (ExplanationOfBenefit) entry.getResource();
        }
        return new ExplanationOfBenefit();
    }

    @Setter
    @Getter
    public static class MedicationRequestsClass {
        private String code;
        private String description;
        private String baseCost;
        private String coverage;
        private String dispenses;
        private String totCost;
        private String startDate;
        private String stopDate;
        private String patient;
        private String payer;
        private Boolean vuoto;

        public MedicationRequestsClass() {
            this.code = "";
            this.description = "";
            this.baseCost = "";
            this.coverage = "";
            this.dispenses = "";
            this.totCost = "";
            this.startDate = "";
            this.stopDate = "";
            this.patient = "";
            this.payer = "";
            this.vuoto = false;
        }

        @Override
        public String toString() {
            return "MedicationRequestsClass{" +
                    "code='" + code + '\'' +
                    ", description='" + description + '\'' +
                    ", baseCost='" + baseCost + '\'' +
                    ", coverage='" + coverage + '\'' +
                    ", dispenses='" + dispenses + '\'' +
                    ", totCost='" + totCost + '\'' +
                    ", startDate='" + startDate + '\'' +
                    ", stopDate='" + stopDate + '\'' +
                    ", patient='" + patient + '\'' +
                    ", payer='" + payer + '\'' +
                    ", vuoto=" + vuoto +
                    '}';
        }
    }
}
