package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hl7.fhir.r4.model.Encounter;

import java.util.List;

public class EncounterConverter extends BaseConverter {

    private List<Encounter> boundleEncounters;
    @FXML
    private ObservableList<EncounterClass> listaCampiEncounter;

    public EncounterConverter(List<Encounter> boundleEncounters) {
        this.boundleEncounters = boundleEncounters;
        this.listaCampiEncounter = FXCollections.observableArrayList();
    }

    @Override
    public void convert() {

        for(Encounter encounter : boundleEncounters) {
            EncounterClass ec = new EncounterClass();

            ec.setId(encounter.getId());
            ec.setCode(encounter.getId());
            ec.setClasse(encounter.getClass_().getDisplay());
            ec.setDescription(encounter.getType().get(0).getText());
            ec.setStartDate(encounter.getPeriod().getStart().toString());
            ec.setStopDate(encounter.getPeriod().getEnd().toString());
            ec.setPatient(encounter.getSubject().getReference());
            ec.setOrganization(encounter.getServiceProvider().getReference());
            ec.setPractitioner(encounter.getParticipant().get(0).getIndividual().getReference());
            //ec.setPayer(encounter.getInsurer().getReference());  //CAPISCI COME é SALVATO IL PAYER
            //ec.setCost(encounter.getHospitalization().getAccomodation().get(0).getCost().toString());
            //ec.setCoverage(encounter.getHospitalization().getAccomodation().get(0).getPrecedence().toString());

            listaCampiEncounter.add(ec);
        }

        if (listaCampiEncounter.isEmpty()) {
            throw new RuntimeException("listaCampiEncounter is empty");
        }
    }

    public ObservableList<EncounterClass> getListaCampiEncounter() {
        return listaCampiEncounter;
    }

    public class EncounterClass {
        private String code;
        private String classe;
        private String description;
        private String startDate;
        private String stopDate;
        private String patient;
        private String organization;
        private String practitioner;
        private String payer;
        private String cost;
        private String coverage;

        private String id;

        public EncounterClass() {
            this.code = "";
            this.classe = "";
            this.description = "";
            this.startDate = "";
            this.stopDate = "";
            this.patient = "";
            this.organization = "";
            this.practitioner = "";
            this.payer = "";
            this.cost = "";
            this.coverage = "";
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getClasse() {
            return classe;
        }

        public void setClasse(String classe) {
            this.classe = classe;
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

        public String getPatient() {
            return patient;
        }

        public void setPatient(String patient) {
            this.patient = patient;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public String getPractitioner() {
            return practitioner;
        }

        public void setPractitioner(String practitioner) {
            this.practitioner = practitioner;
        }

        public String getPayer() {
            return payer;
        }

        public void setPayer(String payer) {
            this.payer = payer;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getCoverage() {
            return coverage;
        }

        public void setCoverage(String coverage) {
            this.coverage = coverage;
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
                    ", payer='" + payer + '\'' +
                    ", cost='" + cost + '\'' +
                    ", coverage='" + coverage + '\'' +
                    '}';
        }
    }
}
