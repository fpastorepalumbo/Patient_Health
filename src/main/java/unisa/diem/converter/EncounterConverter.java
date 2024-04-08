package unisa.diem.converter;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Claim;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.ExplanationOfBenefit;

import java.util.List;

public class EncounterConverter extends BaseConverter {


    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

    private final List<Encounter> bundleEncounters;

    @Getter
    @FXML
    private final ObservableList<EncounterClass> fieldsListEncounter;

    public EncounterConverter(List<Encounter> bundleEncounters) {
        this.bundleEncounters = bundleEncounters;
        this.fieldsListEncounter = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for(Encounter encounter : bundleEncounters) {
            EncounterClass ec = new EncounterClass();
            String[] parts;

            ec.setCompleteId(encounter.getId());

            parts = encounter.getId().split("/");
            String encID = parts[5];

            ExplanationOfBenefit eob = getExplanationOfBenefit(encID);
            Claim claim = getClaim(encID);

            ec.setId(encID);

            ec.setCode(encounter.getType().get(0).getCoding().get(0).getCode());

            ec.setDescription(encounter.getType().get(0).getCoding().get(0).getDisplay());

            parts = encounter.getPeriod().getStart().toString().split(" ");
            ec.setStartDate(parts[5] + "-" + parts[1] + "-" + parts[2]);

            parts = encounter.getPeriod().getEnd().toString().split(" ");
            ec.setStopDate(parts[5] + "-" + parts[1] + "-" + parts[2]);

            parts = encounter.getSubject().getReference().split("/");
            ec.setPatient(parts[1]);

            parts = encounter.getServiceProvider().getReference().split("/");
            ec.setOrganization(parts[1]);

            parts = encounter.getParticipant().get(0).getIndividual().getReference().split("/");
            ec.setPractitioner(parts[1]);

            parts = eob.getInsurer().getReference().split("/");
            ec.setPayer(parts[1]);

            ec.setCost(claim.getItem().get(0).getNet().getValue().toString());

            ec.setCoverage(eob.getItem().get(0).getAdjudication().get(0).getAmount().getValue().toString());

            fieldsListEncounter.add(ec);
        }

        if (fieldsListEncounter.isEmpty()) {
            EncounterClass ec = new EncounterClass();
            ec.setCompleteId("N/A");
            ec.setId("N/A");
            ec.setCode("N/A");
            ec.setDescription("N/A");
            ec.setStartDate("N/A");
            ec.setStopDate("N/A");
            ec.setPatient("N/A");
            ec.setOrganization("N/A");
            ec.setPractitioner("N/A");
            ec.setPayer("N/A");
            ec.setCost("N/A");
            ec.setCoverage("N/A");
            ec.setVuoto(true);
            fieldsListEncounter.add(ec);
        }
    }

    private Claim getClaim(String encID) {
        Bundle bundle;

        try {
            bundle = (Bundle) client.search().forResource(Claim.class)
                    .where(Claim.ENCOUNTER.hasId(encID))
                    .encodedXml()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the Claim");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            if (!((Claim) entry.getResource()).hasPrescription())
                return (Claim) entry.getResource();
        }
        return new Claim();
    }

    private ExplanationOfBenefit getExplanationOfBenefit(String encID) {
        Bundle bundle;

        try {
            bundle = (Bundle) client.search().forResource(ExplanationOfBenefit.class)
                    .where(ExplanationOfBenefit.ENCOUNTER.hasId(encID))
                    .encodedXml()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the ExplanationOfBenefit");
        }

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            if ((entry.getResource()).getId().contains("EE"))
                return (ExplanationOfBenefit) entry.getResource();
        }
        return new ExplanationOfBenefit();
    }

    @Setter
    @Getter
    public static class EncounterClass {
        private String completeId;
        private String id;
        private String code;
        private String description;
        private String startDate;
        private String stopDate;
        private String patient;
        private String organization;
        private String practitioner;
        private String payer;
        private String cost;
        private String coverage;
        private Boolean vuoto;

        public EncounterClass() {
            this.completeId = "";
            this.id = "";
            this.code = "";
            this.description = "";
            this.startDate = "";
            this.stopDate = "";
            this.patient = "";
            this.organization = "";
            this.practitioner = "";
            this.payer = "";
            this.cost = "";
            this.coverage = "";
            this.vuoto = false;
        }

        @Override
        public String toString() {
            return "EncounterClass{" +
                    "completeId='" + completeId + '\'' +
                    ", id='" + id + '\'' +
                    ", code='" + code + '\'' +
                    ", description='" + description + '\'' +
                    ", startDate='" + startDate + '\'' +
                    ", stopDate='" + stopDate + '\'' +
                    ", patient='" + patient + '\'' +
                    ", organization='" + organization + '\'' +
                    ", practitioner='" + practitioner + '\'' +
                    ", payer='" + payer + '\'' +
                    ", cost='" + cost + '\'' +
                    ", coverage='" + coverage + '\'' +
                    ", vuoto=" + vuoto +
                    '}';
        }
    }
}
