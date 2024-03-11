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

import java.util.ArrayList;
import java.util.List;

// TODO: Rinominare code in id e classe in code, prendere claim e eob per payer cost e coverage

public class EncounterConverter extends BaseConverter {


    FhirContext ctx = FhirContext.forR4();
    String serverBaseUrl = "http://localhost:8080/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

    private final List<Encounter> bundleEncounters;

   // List <Claim> claims; //costo
    //List <ExplanationOfBenefit> eobs; //coverage

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

            parts = encounter.getId().split("/");
            ec.setCode(parts[5]);

            ec.setClasse(encounter.getType().get(0).getCoding().get(0).getCode());

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

            // ec.setPayer(encounter.getInsurer().getReference());
            String cost = getClaimCost(encounter);
            ec.setCost(cost);

            String coverage = getCoverageExplanationOfBenefit(encounter);
            ec.setCoverage(coverage);

            fieldsListEncounter.add(ec);
        }

        if (fieldsListEncounter.isEmpty()) {
            EncounterClass ec = new EncounterClass();
            ec.setCode("N/A");
            ec.setClasse("N/A");
            ec.setDescription("N/A");
            ec.setStartDate("N/A");
            ec.setStopDate("N/A");
            ec.setPatient("N/A");
            ec.setOrganization("N/A");
            ec.setPractitioner("N/A");
            // ec.setPayer("N/A");
            ec.setCost("N/A");
            ec.setCoverage("N/A");
            fieldsListEncounter.add(ec);
        }
    }

    private String getCoverageExplanationOfBenefit(Encounter encounter) {
        Bundle bundle;
        ExplanationOfBenefit eob;
        String coverage ="";

        try {
            bundle = (Bundle) client.search().forResource(ExplanationOfBenefit.class)
                    .where(ExplanationOfBenefit.ENCOUNTER.hasId(encounter.getId()))
                    .encodedXml()
                    .execute();
            eob = (ExplanationOfBenefit) bundle.getEntry().get(0).getResource();
            coverage = eob.getInsurance().get(0).getCoverage().getReference();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the ExplanationOfBenefit");
        }
        return coverage;
    }

    private String getClaimCost(Encounter encounter) { //del claim il costo, claim.getCost()
        Bundle bundle;
        Claim claim;
        String costo="";

        try {
            bundle = (Bundle) client.search().forResource(Claim.class)
                    .where(Claim.ENCOUNTER.hasId(encounter.getId()))
                    .encodedXml()
                    .execute();
            claim = (Claim) bundle.getEntry().get(0).getResource();
            costo = claim.getTotal().getValue().toString();
        } catch (Exception e) {
            throw new RuntimeException("Error during the download of the Claim");
        }
        return costo;
    }

    @Setter
    @Getter
    public static class EncounterClass {
        private String code;
        private String classe;
        private String description;
        private String startDate;
        private String stopDate;
        private String patient;
        private String organization;
        private String practitioner;
        // private String payer;
        private String cost;
        private String coverage;

        public EncounterClass() {
            this.code = "";
            this.classe = "";
            this.description = "";
            this.startDate = "";
            this.stopDate = "";
            this.patient = "";
            this.organization = "";
            this.practitioner = "";
            // this.payer = "";
            this.cost = "";
            this.coverage = "";
        }

        @Override
        public String toString() {
            return "EncounterClass{" +
                    "code='" + code + '\'' +
                    ", classe='" + classe + '\'' +
                    ", description='" + description + '\'' +
                    ", startDate='" + startDate + '\'' +
                    ", stopDate='" + stopDate + '\'' +
                    ", patient='" + patient + '\'' +
                    ", organization='" + organization + '\'' +
                    ", practitioner='" + practitioner + '\'' +
                    // ", payer='" + payer + '\'' +
                    ", cost='" + cost + '\'' +
                    ", coverage='" + coverage + '\'' +
                    '}';
        }
    }
}
