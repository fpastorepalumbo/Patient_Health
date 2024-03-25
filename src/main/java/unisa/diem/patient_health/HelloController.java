package unisa.diem.patient_health;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import org.hl7.fhir.r4.model.*;
import unisa.diem.converter.*;
import unisa.diem.downloader.*;
import unisa.diem.parser.DatasetService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public TableView<PatientConverter.PatientClass> personTable;
    @FXML
    public TableView<AllergyConverter.AllergyClass> allergyTable;
    @FXML
    public TableView<ConditionConverter.ConditionClass> conditionTable;
    @FXML
    public TableView<ImmunizationConverter.ImmunizationClass> immunizationTable;
    @FXML
    public TableView<CarePlanConverter.CarePlanClass> carePlanTable;
    @FXML
    public TableView<OrganizationConverter.OrganizationClass> organizationTable;
    @FXML
    public TableView<PayersConverter.PayersClass> payerTable;
    @FXML
    public TableView<PractitionersConverter.PractitionerClass> practitionerTable;
    @FXML
    public TableView<EncounterConverter.EncounterClass> encounterTable;
    @FXML
    public TableView<ObservationsConverter.ObservationClass> observationTable;
    @FXML
    public TableView<MedicationRequestsConverter.MedicationRequestsClass> medReqTable;
    @FXML
    public TableView<ProceduresConverter.ProcedureClass> procedureTable;
    @FXML
    public TableView<DevicesConverter.DeviceClass> deviceTable;

    @FXML
    public TableView imageTable;

    /*
    @FXML
    public TableView<ImagingStudyConverter.ImagingStudyClass> imageTable;
    */

    //  personTable
    @FXML
    public TableColumn nameClmPersona;
    @FXML
    public TableColumn surnameClmPersona;
    @FXML
    public TableColumn birthdateClmPersona;
    @FXML
    public TableColumn deathdateClmPersona;
    @FXML
    public TableColumn ssnClmPersona;
    @FXML
    public TableColumn maritalClmPersona;
    @FXML
    public TableColumn genderClmPersona;
    @FXML
    public TableColumn birthplaceClmPersona;
    @FXML
    public TableColumn addressClmPersona;
    @FXML
    public TableColumn cityClmPersona;
    @FXML
    public TableColumn stateClmPersona;

    //  allergyTable
    @FXML
    public TableColumn codeClmAllergy;
    @FXML
    public TableColumn descriptionClmAllergy;
    @FXML
    public TableColumn startDateClmAllergy;
    @FXML
    public TableColumn stopDateClmAllergy;
    @FXML
    public TableColumn encounterClmAllergy;

    //  immunizationTable
    @FXML
    public TableColumn codeClmImmunization;
    @FXML
    public TableColumn descriptionClmImmunization;
    @FXML
    public TableColumn dateClmImmunization;
    @FXML
    public TableColumn encounterClmImmunization;

    // conditionTable
    @FXML
    public TableColumn codeClmCondition;
    @FXML
    public TableColumn descriptionClmCondition;
    @FXML
    public TableColumn startDateClmCondition;
    @FXML
    public TableColumn stopDateClmCondition;
    @FXML
    public TableColumn encounterClmCondition;

    // carePlanTable
    @FXML
    public TableColumn codeClmCarePlan;
    @FXML
    public TableColumn descriptionClmCarePlan;
    @FXML
    public TableColumn reasonCodeClmCarePlan;
    @FXML
    public TableColumn reasonDescriptionClmCarePlan;

    // organizationTable
    @FXML
    public TableColumn organizationNameClm;
    @FXML
    public TableColumn cityOrganizationClm;
    @FXML
    public TableColumn stateOrganizationClm;
    @FXML
    public TableColumn phoneOrganizationClm;

    // payerTable
    @FXML
    public TableColumn payerNameClm;
    @FXML
    public TableColumn cityPayerClm;
    @FXML
    public TableColumn statePayerClm;
    @FXML
    public TableColumn phonePayerClm;

    // practitionerTable
    @FXML
    public TableColumn namePractitionerClm;
    @FXML
    public TableColumn genderPractitionerClm;
    @FXML
    public TableColumn specialtyPractitionerClm;
    @FXML
    public TableColumn organizationPractitionerClm;
    @FXML
    public TableColumn addressPractitionerClm;
    @FXML
    public TableColumn cityPractitionerClm;
    @FXML
    public TableColumn statePractitionerClm;

    // encounterTable
    @FXML
    public TableColumn idEncounterClm;
    @FXML
    public TableColumn codeEncounterClm;
    @FXML
    public TableColumn descriptionEncounterClm;
    @FXML
    public TableColumn startDateEncounterClm;
    @FXML
    public TableColumn stopDateEncounterClm;
    @FXML
    public TableColumn patientEncounterClm;
    @FXML
    public TableColumn organizationEncounterClm;
    @FXML
    public TableColumn practitionerEncounterClm;
    @FXML
    public TableColumn payerEncounterClm;
    @FXML
    public TableColumn costEncounterClm;
    @FXML
    public TableColumn coverageEncounterClm;

    // observationTable
    @FXML
    public TableColumn observationEncounterClm2;
    @FXML
    public TableColumn descriptionEncounterClm2;
    @FXML
    public TableColumn valueObservationClm;
    @FXML
    public TableColumn dateObservationClm;
    @FXML
    public TableColumn patientObservationClm;

    // medReqTable
    @FXML
    public TableColumn codeMedReqClm;
    @FXML
    public TableColumn descriptionMedReqClm;
    @FXML
    public TableColumn baseCostMedReqClm;
    @FXML
    public TableColumn coverageMedReqClm;
    @FXML
    public TableColumn dispensesMedReqClm;
    @FXML
    public TableColumn totCostMedReqClm;
    @FXML
    public TableColumn startDateMedReqClm;
    @FXML
    public TableColumn stopDateMedReqClm;
    @FXML
    public TableColumn patientMedReqClm;
    @FXML
    public TableColumn payerMedReqClm;

    // procedureTable
    @FXML
    public TableColumn codeProcedureClm;
    @FXML
    public TableColumn descriptionProcedureClm;
    @FXML
    public TableColumn dateProcedureClm;
    @FXML
    public TableColumn patientProcedureClm;

    // deviceTable
    @FXML
    public TableColumn codeDeviceClm;
    @FXML
    public TableColumn descriptionDeviceClm;
    @FXML
    public TableColumn startDateDeviceClm;
    @FXML
    public TableColumn stopDateDeviceClm;
    @FXML
    public TableColumn patientDeviceClm;

    // imageTable
    @FXML
    public TableColumn bodyCodeClm;
    @FXML
    public TableColumn bodyDescrClm;
    @FXML
    public TableColumn modeCodeClm;
    @FXML
    public TableColumn modeDescrClm;
    @FXML
    public TableColumn sopCodeClm;
    @FXML
    public TableColumn sopDescrClm;
    @FXML
    public TableColumn dateImageClm;
    @FXML
    public TableColumn patientImageClm;
    @FXML
    public TableColumn encounterImageClm;

    @FXML
    private Label welcomeText;
    @FXML
    private Label patientLabel;
    @FXML
    private Label organizationLabel;
    @FXML
    private Label encounterLabel;
    @FXML
    private Label imagineLabel;
    @FXML
    private Pane patientPane;
    @FXML
    private Pane organizationPane;
    @FXML
    private Pane exstEncounterPane;
    @FXML
    private Pane encounterPane1;
    @FXML
    private Pane encounterPane2;
    @FXML
    private Pane imagingPane;

    @FXML
    private TextField searchBar;

    public PatientDownload patientDownload;
    public EncountersDownload encounterDownload;

    public EncountersDownload encounterDownloadSearch;
    public OrganizationDownload organizationDownload;
    public PayersDownload payersDownload;
    public PractitionersDownload practitionersDownload;
    public DatasetService datasetUtility;
    public PatientConverter.PatientClass personElement;
    public EncounterConverter.EncounterClass encounterElement;
    public boolean firstClickPatient;
    public boolean firstClickOrganization;
    public boolean firstClickEncounter;
    public boolean firstClickImaging;

    public HelloController() {
        patientDownload = new PatientDownload();
        encounterDownload = new EncountersDownload();
        encounterDownloadSearch = new EncountersDownload();
        organizationDownload = new OrganizationDownload();
        payersDownload = new PayersDownload();
        practitionersDownload = new PractitionersDownload();
        datasetUtility = new DatasetService();
        personElement = null;
        encounterElement = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patientPane.setVisible(false);
        organizationPane.setVisible(false);
        exstEncounterPane.setVisible(false);
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(false);
        imagingPane.setVisible(false);

        nameClmPersona.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameClmPersona.setCellValueFactory(new PropertyValueFactory<>("surname"));
        birthdateClmPersona.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        deathdateClmPersona.setCellValueFactory(new PropertyValueFactory<>("deathdate"));
        ssnClmPersona.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        maritalClmPersona.setCellValueFactory(new PropertyValueFactory<>("marital"));
        genderClmPersona.setCellValueFactory(new PropertyValueFactory<>("gender"));
        birthplaceClmPersona.setCellValueFactory(new PropertyValueFactory<>("birthplace"));
        addressClmPersona.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityClmPersona.setCellValueFactory(new PropertyValueFactory<>("city"));
        stateClmPersona.setCellValueFactory(new PropertyValueFactory<>("state"));

        codeClmAllergy.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionClmAllergy.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateClmAllergy.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        stopDateClmAllergy.setCellValueFactory(new PropertyValueFactory<>("stopDate"));
        encounterClmAllergy.setCellValueFactory(new PropertyValueFactory<>("encounter"));

        codeClmImmunization.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionClmImmunization.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateClmImmunization.setCellValueFactory(new PropertyValueFactory<>("date"));
        encounterClmImmunization.setCellValueFactory(new PropertyValueFactory<>("encounter"));

        codeClmCondition.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionClmCondition.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateClmCondition.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        stopDateClmCondition.setCellValueFactory(new PropertyValueFactory<>("stopDate"));
        encounterClmCondition.setCellValueFactory(new PropertyValueFactory<>("encounter"));

        codeClmCarePlan.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionClmCarePlan.setCellValueFactory(new PropertyValueFactory<>("description"));
        reasonCodeClmCarePlan.setCellValueFactory(new PropertyValueFactory<>("reasonCode"));
        reasonDescriptionClmCarePlan.setCellValueFactory(new PropertyValueFactory<>("reasonDescription"));

        organizationNameClm.setCellValueFactory(new PropertyValueFactory<>("name"));
        cityOrganizationClm.setCellValueFactory(new PropertyValueFactory<>("city"));
        stateOrganizationClm.setCellValueFactory(new PropertyValueFactory<>("state"));
        phoneOrganizationClm.setCellValueFactory(new PropertyValueFactory<>("phone"));

        payerNameClm.setCellValueFactory(new PropertyValueFactory<>("name"));
        cityPayerClm.setCellValueFactory(new PropertyValueFactory<>("city"));
        statePayerClm.setCellValueFactory(new PropertyValueFactory<>("state"));
        phonePayerClm.setCellValueFactory(new PropertyValueFactory<>("phone"));

        namePractitionerClm.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderPractitionerClm.setCellValueFactory(new PropertyValueFactory<>("gender"));
        specialtyPractitionerClm.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        organizationPractitionerClm.setCellValueFactory(new PropertyValueFactory<>("organization"));
        addressPractitionerClm.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityPractitionerClm.setCellValueFactory(new PropertyValueFactory<>("city"));
        statePractitionerClm.setCellValueFactory(new PropertyValueFactory<>("state"));

        idEncounterClm.setCellValueFactory(new PropertyValueFactory<>("id"));
        codeEncounterClm.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionEncounterClm.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateEncounterClm.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        stopDateEncounterClm.setCellValueFactory(new PropertyValueFactory<>("stopDate"));
        patientEncounterClm.setCellValueFactory(new PropertyValueFactory<>("patient"));
        organizationEncounterClm.setCellValueFactory(new PropertyValueFactory<>("organization"));
        practitionerEncounterClm.setCellValueFactory(new PropertyValueFactory<>("practitioner"));
        payerEncounterClm.setCellValueFactory(new PropertyValueFactory<>("payer"));
        costEncounterClm.setCellValueFactory(new PropertyValueFactory<>("cost"));
        coverageEncounterClm.setCellValueFactory(new PropertyValueFactory<>("coverage"));

        observationEncounterClm2.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionEncounterClm2.setCellValueFactory(new PropertyValueFactory<>("description"));
        valueObservationClm.setCellValueFactory(new PropertyValueFactory<>("value"));
        dateObservationClm.setCellValueFactory(new PropertyValueFactory<>("date"));
        patientObservationClm.setCellValueFactory(new PropertyValueFactory<>("patient"));

        codeMedReqClm.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionMedReqClm.setCellValueFactory(new PropertyValueFactory<>("description"));
        baseCostMedReqClm.setCellValueFactory(new PropertyValueFactory<>("baseCost"));
        coverageMedReqClm.setCellValueFactory(new PropertyValueFactory<>("coverage"));
        dispensesMedReqClm.setCellValueFactory(new PropertyValueFactory<>("dispenses"));
        totCostMedReqClm.setCellValueFactory(new PropertyValueFactory<>("totCost"));
        startDateMedReqClm.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        stopDateMedReqClm.setCellValueFactory(new PropertyValueFactory<>("stopDate"));
        patientMedReqClm.setCellValueFactory(new PropertyValueFactory<>("patient"));
        payerMedReqClm.setCellValueFactory(new PropertyValueFactory<>("payer"));

        codeProcedureClm.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionProcedureClm.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateProcedureClm.setCellValueFactory(new PropertyValueFactory<>("date"));
        patientProcedureClm.setCellValueFactory(new PropertyValueFactory<>("patient"));

        codeDeviceClm.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionDeviceClm.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateDeviceClm.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        stopDateDeviceClm.setCellValueFactory(new PropertyValueFactory<>("stopDate"));
        patientDeviceClm.setCellValueFactory(new PropertyValueFactory<>("patient"));

        bodyCodeClm.setCellValueFactory(new PropertyValueFactory<>("bodyCode"));
        bodyDescrClm.setCellValueFactory(new PropertyValueFactory<>("bodyDescr"));
        modeCodeClm.setCellValueFactory(new PropertyValueFactory<>("modeCode"));
        modeDescrClm.setCellValueFactory(new PropertyValueFactory<>("modeDescr"));
        sopCodeClm.setCellValueFactory(new PropertyValueFactory<>("sopCode"));
        sopDescrClm.setCellValueFactory(new PropertyValueFactory<>("sopDescr"));
        dateImageClm.setCellValueFactory(new PropertyValueFactory<>("date"));
        patientImageClm.setCellValueFactory(new PropertyValueFactory<>("patient"));
        encounterImageClm.setCellValueFactory(new PropertyValueFactory<>("encounter"));

        personTable.getItems().clear();
        allergyTable.getItems().clear();
        conditionTable.getItems().clear();
        immunizationTable.getItems().clear();
        carePlanTable.getItems().clear();
        organizationTable.getItems().clear();
        payerTable.getItems().clear();
        practitionerTable.getItems().clear();
        encounterTable.getItems().clear();
        observationTable.getItems().clear();
        medReqTable.getItems().clear();
        procedureTable.getItems().clear();
        deviceTable.getItems().clear();
        imageTable.getItems().clear();

        firstClickPatient = true;
        firstClickOrganization = true;
        firstClickEncounter = true;
        firstClickImaging = true;

    }

    @FXML
    public void patientLabelClick(MouseEvent mouseEvent) {
        if (patientPane.isVisible())
            return;

        patientPane.setVisible(true);
        organizationPane.setVisible(false);
        exstEncounterPane.setVisible(false);
        imagingPane.setVisible(false);
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(false);

        allergyTable.setVisible(false);
        conditionTable.setVisible(false);
        immunizationTable.setVisible(false);
        carePlanTable.setVisible(false);

        if (firstClickPatient) {
            firstClickPatient = false;
            personTable.getItems().clear();

            patientDownload.download();
            List<Patient> patients = patientDownload.getPatients();
            PatientConverter patientConverter = new PatientConverter(patients);
            patientConverter.convert();

            if (patientConverter.getFieldsListPatient().isEmpty())
                throw new RuntimeException("Patient Data Conversion Error");
            for (PatientConverter.PatientClass patient : patientConverter.getFieldsListPatient())
                personTable.getItems().add(patient);
        }
    }

    @FXML
    public void organizationLabelClick(MouseEvent mouseEvent) {
        if (organizationPane.isVisible())
            return;

        organizationPane.setVisible(true);
        patientPane.setVisible(false);
        exstEncounterPane.setVisible(false);
        imagingPane.setVisible(false);
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(false);

        if (firstClickOrganization) {
            firstClickOrganization = false;
            organizationTable.getItems().clear();
            payerTable.getItems().clear();
            practitionerTable.getItems().clear();

            organizationDownload.download();
            List<Organization> organizations = organizationDownload.getOrganizations();
            OrganizationConverter organizationConverter = new OrganizationConverter(organizations);
            organizationConverter.convert();

            if (organizationConverter.getFieldsListOrganization().isEmpty())
                throw new RuntimeException("Organization Data Conversion Error");
            for (OrganizationConverter.OrganizationClass organization : organizationConverter.getFieldsListOrganization())
                organizationTable.getItems().add(organization);

            payersDownload.download();
            List<Organization> payers = payersDownload.getPayers();
            PayersConverter payersConverter = new PayersConverter(payers);
            payersConverter.convert();

            if (payersConverter.getFieldsListPayer().isEmpty())
                throw new RuntimeException("Payers Data Conversion Error");
            for (PayersConverter.PayersClass payer : payersConverter.getFieldsListPayer())
                payerTable.getItems().add(payer);

            practitionersDownload.download();
            List<Practitioner> practitioners = practitionersDownload.getPractitioners();
            PractitionersConverter practitionersConverter = new PractitionersConverter(practitioners);
            practitionersConverter.convert();

            if (practitionersConverter.getFieldsListPractitioner().isEmpty()) {
                throw new RuntimeException("Practitioners Data Conversion Error");
            }
            for (PractitionersConverter.PractitionerClass practitioner : practitionersConverter.getFieldsListPractitioner()) {
                practitionerTable.getItems().add(practitioner);
            }
        }
    }

    @FXML
    public void encounterLabelClick() {
        if (exstEncounterPane.isVisible())
            return;

        exstEncounterPane.setVisible(true);
        encounterPane1.setVisible(true);
        encounterPane2.setVisible(false);
        patientPane.setVisible(false);
        organizationPane.setVisible(false);
        imagingPane.setVisible(false);

        if (firstClickEncounter) {
            firstClickEncounter = false;
            encounterTable.getItems().clear();
            encounterDownload.download();
            List<Encounter> encounters = encounterDownload.getEncounters();
            EncounterConverter encounterConverter = new EncounterConverter(encounters);
            encounterConverter.convert();

            if (encounterConverter.getFieldsListEncounter().isEmpty())
                throw new RuntimeException("Encounter Data Conversion Error");
            for (EncounterConverter.EncounterClass encounter : encounterConverter.getFieldsListEncounter())
                encounterTable.getItems().add(encounter);
        }
    }
    @FXML
    public void imagineLabelClick() {
        if (imagingPane.isVisible())
            return;

        imagingPane.setVisible(true);
        patientPane.setVisible(false);
        organizationPane.setVisible(false);
        exstEncounterPane.setVisible(false);
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(false);

        if (firstClickEncounter) {
            firstClickEncounter = false;
            imageTable.getItems().clear();
            /*
            * TODO: RICHIAMA I METODI DI DOWNLOAD E CONVERSIONE
            **/
        }
    }

    public void clickPatientTable() {
        allergyTable.getItems().clear();
        conditionTable.getItems().clear();
        immunizationTable.getItems().clear();
        carePlanTable.getItems().clear();
        personElement = personTable.getSelectionModel().getSelectedItem();
         
        if(personElement != null) {
            allergyTable.setVisible(true);
            conditionTable.setVisible(true);
            immunizationTable.setVisible(true);
            carePlanTable.setVisible(false);

            AllergyDownload allergyDownload = new AllergyDownload(personElement.getId());
            allergyDownload.download();
            List<AllergyIntolerance> allergies = allergyDownload.getAllergies();
            AllergyConverter allergyConverter = new AllergyConverter(allergies);
            allergyConverter.convert();
            if (allergyConverter.getFieldsListAllergy().isEmpty())
                throw new RuntimeException("Allergy Data Conversion Error");
            for (AllergyConverter.AllergyClass allergy : allergyConverter.getFieldsListAllergy())
                allergyTable.getItems().add(allergy);

            ImmunizationDownload immunizationDownload = new ImmunizationDownload(personElement.getId());
            immunizationDownload.download();
            List<Immunization> immunizations = immunizationDownload.getImmunizations();
            ImmunizationConverter immunizationConverter = new ImmunizationConverter(immunizations);
            immunizationConverter.convert();
            if (immunizationConverter.getFieldsListImmunization().isEmpty())
                throw new RuntimeException("Immunization Data Conversion Error");
            for (ImmunizationConverter.ImmunizationClass immunization : immunizationConverter.getFieldsListImmunization())
                immunizationTable.getItems().add(immunization);

            ConditionDownload conditionDownload = new ConditionDownload(personElement.getId());
            conditionDownload.download();
            List<Condition> conditions = conditionDownload.getConditions();
            ConditionConverter conditionConverter = new ConditionConverter(conditions);
            conditionConverter.convert();
            if (conditionConverter.getFieldsListCondition().isEmpty())
                throw new RuntimeException("Condition Data Conversion Error");
            for (ConditionConverter.ConditionClass condition : conditionConverter.getFieldsListCondition())
                conditionTable.getItems().add(condition);
        }
    }

    public void clickEncounterTable() {

        /*
        for(Encounter encounter : bundleEncounters) {
            EncounterClass ec = new EncounterClass();
            String[] parts;

            parts = encounter.getId().split("/");
            String encID = parts[5];

            ExplanationOfBenefit eob = getExplanationOfBenefit(encID);
            Claim claim = getClaim(encID);

            ec.setId(encID);
         */

        exstEncounterPane.setVisible(true);
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(true);
        patientPane.setVisible(false);
        organizationPane.setVisible(false);
        imagingPane.setVisible(false);

        observationTable.getItems().clear();
        medReqTable.getItems().clear();
        procedureTable.getItems().clear();
        deviceTable.getItems().clear();

        encounterElement = encounterTable.getSelectionModel().getSelectedItem();

        if (encounterElement != null) {
            medReqTable.setVisible(true);
            observationTable.setVisible(true);
            procedureTable.setVisible(true);
            deviceTable.setVisible(true);
            String encID = encounterElement.getId();

            System.out.println(encID);

            ObservationsDownload observationDownload = new ObservationsDownload(encID);
            observationDownload.download();
            List<Observation> observations = observationDownload.getObservations();
            ObservationsConverter observationsConverter = new ObservationsConverter(observations);
            observationsConverter.convert();
            if (observationsConverter.getFieldsListObservation().isEmpty())
                throw new RuntimeException("Observation Data Conversion Error");
            for (ObservationsConverter.ObservationClass observation : observationsConverter.getFieldsListObservation())
                observationTable.getItems().add(observation);

            MedicationRequestDownload medReqDownload = new MedicationRequestDownload(encID);
            medReqDownload.download();
            List<MedicationRequest> medReqs = medReqDownload.getMedicationRequests();
            MedicationRequestsConverter medReqConverter = new MedicationRequestsConverter(medReqs);
            medReqConverter.convert();
            if (medReqConverter.getFieldsListMedRequest().isEmpty())
                throw new RuntimeException("Medication Request Data Conversion Error");
            for (MedicationRequestsConverter.MedicationRequestsClass medReq : medReqConverter.getFieldsListMedRequest())
                medReqTable.getItems().add(medReq);

            ProceduresDownload procedureDownload = new ProceduresDownload(encID);
            procedureDownload.download();
            List<Procedure> procedures = procedureDownload.getProcedures();
            ProceduresConverter procedureConverter = new ProceduresConverter(procedures);
            procedureConverter.convert();
            if (procedureConverter.getFieldsListProcedure().isEmpty())
                throw new RuntimeException("Procedure Data Conversion Error");
            for (ProceduresConverter.ProcedureClass procedure : procedureConverter.getFieldsListProcedure())
                procedureTable.getItems().add(procedure);

            DevicesDownload deviceDownload = new DevicesDownload(encID);
            deviceDownload.download();
            List<DeviceRequest> devices = deviceDownload.getDevices();
            DevicesConverter deviceConverter = new DevicesConverter(devices);
            deviceConverter.convert();
            if (deviceConverter.getFieldsListDevices().isEmpty())
                throw new RuntimeException("Device Data Conversion Error");
            for (DevicesConverter.DeviceClass device : deviceConverter.getFieldsListDevices())
                deviceTable.getItems().add(device);
        }
    }

    public void clickConditionTable() {
        ConditionConverter.ConditionClass conditionElement = conditionTable.getSelectionModel().getSelectedItem();

        if (conditionElement != null)
            carePlanTable.setVisible(true);
        carePlanTable.getItems().clear();

        CarePlanDownload carePlanDownload = new CarePlanDownload(personElement.getId());
        carePlanDownload.download();
        List<CarePlan> carePlans = carePlanDownload.getCarePlans();
        CarePlanConverter carePlanConverter = new CarePlanConverter(carePlans);
        carePlanConverter.convert();
        for (CarePlanConverter.CarePlanClass carePlan : carePlanConverter.getFieldsListCarePlan())
            carePlanTable.getItems().add(carePlan);
    }

    public void loadDataset() {
        try {
            datasetUtility.loadDataset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void patientScrollTable(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) {
            personTable.getItems().clear();
            patientDownload.download();
            List<Patient> patients = patientDownload.getPatients();
            PatientConverter patientConverter = new PatientConverter(patients);
            patientConverter.convert();
            if (patientConverter.getFieldsListPatient().isEmpty())
                throw new RuntimeException("ERROR DURING THE SCROLL OF PATIENT");
            for (PatientConverter.PatientClass patient : patientConverter.getFieldsListPatient())
                personTable.getItems().add(patient);
        }
    }

    public void organizationScrollTable(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) {
            organizationTable.getItems().clear();
            organizationDownload.download();
            List<Organization> organizations = organizationDownload.getOrganizations();
            OrganizationConverter organizationConverter = new OrganizationConverter(organizations);
            organizationConverter.convert();
            if (organizationConverter.getFieldsListOrganization().isEmpty())
                throw new RuntimeException("ERROR DURING THE SCROLL OF ORGANIZATIONS");
            for (OrganizationConverter.OrganizationClass organization : organizationConverter.getFieldsListOrganization())
                organizationTable.getItems().add(organization);
        }
    }

    public void practitionerScrollTable(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) {
            practitionerTable.getItems().clear();
            practitionersDownload.download();
            List<Practitioner> practitioners = practitionersDownload.getPractitioners();
            PractitionersConverter practitionersConverter = new PractitionersConverter(practitioners);
            practitionersConverter.convert();
            if (practitionersConverter.getFieldsListPractitioner().isEmpty())
                throw new RuntimeException("ERROR DURING THE SCROLL OF PRACTITIONERS");
            for (PractitionersConverter.PractitionerClass practitioner : practitionersConverter.getFieldsListPractitioner())
                practitionerTable.getItems().add(practitioner);
        }
    }

    public void encounterScrollTable(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) {
            encounterTable.getItems().clear();
            encounterDownload.download();
            List<Encounter> encounters = encounterDownload.getEncounters();
            EncounterConverter encounterConverter = new EncounterConverter(encounters);
            encounterConverter.convert();
            if (encounterConverter.getFieldsListEncounter().isEmpty())
                throw new RuntimeException("ERROR DURING THE SCROLL OF ENCOUNTERS");
            for (EncounterConverter.EncounterClass encounter : encounterConverter.getFieldsListEncounter())
                encounterTable.getItems().add(encounter);
        }
    }

    public void searchButtonClick(MouseEvent mouseEvent) {
        encounterTable.getItems().clear();
        String search = searchBar.getText();
        if (search.isEmpty())
            return;

        encounterDownloadSearch.downloadEncounter(search);
        List<Encounter> encounter = encounterDownloadSearch.getEncounterSearch();
        EncounterConverter encounterConverter = new EncounterConverter(encounter);
        encounterConverter.convert();

        if (encounterConverter.getFieldsListEncounter().isEmpty())
            throw new RuntimeException("Encounter Data Conversion Error");

        for (EncounterConverter.EncounterClass encounter1 : encounterConverter.getFieldsListEncounter())
            encounterTable.getItems().add(encounter1);
    }

    // TODO: Metodo Scroll immagini
}