package unisa.diem.patient_health;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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

import javax.swing.event.ChangeEvent;
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
    public TableView organizationTable;
    @FXML
    public TableView payerTable;
    @FXML
    public TableView practitionerTable;
    @FXML
    public TableView encounterTable;
    @FXML
    public TableView observationTable;
    @FXML
    public TableView medReqTable;
    @FXML
    public TableView procedureTable;
    @FXML
    public TableView deviceTable;
    @FXML
    public TableView imageTable;
    /*
    @FXML
    public TableView<OrganizationConverter.OrganizationClass> organizationTable;
    @FXML
    public TableView<PayerConverter.PayerClass> payerTable;
    @FXML
    public TableView<PractitionerConverter.PractitionerClass> practitionerTable;
    @FXML
    public TableView<EncounterConverter.EncounterClass> encounterTable;
    @FXML
    public TableView<ObservationConverter.ObservationClass> observationTable;
    @FXML
    public TableView<ObservationConverter.MedReqClass> medReqTable;
    @FXML
    public TableView<ProcedureConverter.ProcedureClass> procedureTable;
    @FXML
    public TableView<DeviceConverter.DeviceClass> deviceTable;
    @FXML
    public TableView<ImagingStudyConverter.ImagingStudyClass> imageTable;
    */


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
    @FXML
    public TableColumn codeClmImmunization;
    @FXML
    public TableColumn descriptionClmImmunization;
    @FXML
    public TableColumn dateClmImmunization;
    @FXML
    public TableColumn encounterClmImmunization;
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
    @FXML
    public TableColumn codeClmCarePlan;
    @FXML
    public TableColumn descriptionClmCarePlan;
    @FXML
    public TableColumn reasonCodeClmCarePlan;
    @FXML
    public TableColumn reasonDescriptionClmCarePlan;

    @FXML
    public TableColumn organizationNameClm;
    @FXML
    public TableColumn addressOrganizationClm;
    @FXML
    public TableColumn cityOrganizationClm;
    @FXML
    public TableColumn stateOrganizationClm;
    @FXML
    public TableColumn phoneOrganizationClm;
    @FXML
    public TableColumn revenueOrganizationClm;

    //declare columns for payerTable
    @FXML
    public TableColumn payerNameClm;
    @FXML
    public TableColumn addressPayerClm;
    @FXML
    public TableColumn cityPayerClm;
    @FXML
    public TableColumn statePayerClm;
    @FXML
    public TableColumn phonePayerClm;
    @FXML
    public TableColumn revenuePayerClm;

    //declare columns for practitionerTable
    @FXML
    public TableColumn namePractitionerClm;
    @FXML
    public TableColumn genderPractitionerClm;
    @FXML
    public TableColumn specialityPractitionerClm;
    @FXML
    public TableColumn organizationPractitionerClm;
    @FXML
    public TableColumn addressPractitionerClm;
    @FXML
    public TableColumn cityPractitionerClm;
    @FXML
    public TableColumn statePractitionerClm;

    //declare columns for encounterTable
    @FXML
    public TableColumn codeEncounterClm;
    @FXML
    public TableColumn classEncounterClm;
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

    @FXML
    public TableColumn codeMedReqClm;

    @FXML
    public TableColumn descriptionMedReqClm;

    @FXML
    public TableColumn baseCostMedReqClm;

    @FXML
    public TableColumn coverageMedReqClm;

    @FXML
    public TableColumn dispendesMedReqClm;

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
    @FXML
    public TableColumn codeProcedureClm;
    @FXML
    public TableColumn descriptionProcedureClm;
    @FXML
    public TableColumn dateProcedureClm;
    @FXML
    public TableColumn costProcedureClm;
    @FXML
    public TableColumn patientProcedureClm;
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


    public PatientDownload patientDownload = new PatientDownload();


    public DatasetService datasetUtility = new DatasetService();

    public PatientConverter.PatientClass personElement = null;

    public boolean firstClickPatient;
    public boolean firstClickOrganization;
    public boolean firstClickEncounter;
    public boolean firstClickImaging;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patientPane.setVisible(false);
        organizationPane.setVisible(false);
        exstEncounterPane.setVisible(false);
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(false);
        imagingPane.setVisible(false);

        // Associazione delle colonne ai campi del modello di dati
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
        addressOrganizationClm.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityOrganizationClm.setCellValueFactory(new PropertyValueFactory<>("city"));
        stateOrganizationClm.setCellValueFactory(new PropertyValueFactory<>("state"));
        phoneOrganizationClm.setCellValueFactory(new PropertyValueFactory<>("phone"));
        revenueOrganizationClm.setCellValueFactory(new PropertyValueFactory<>("revenue"));

        payerNameClm.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressPayerClm.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityPayerClm.setCellValueFactory(new PropertyValueFactory<>("city"));
        statePayerClm.setCellValueFactory(new PropertyValueFactory<>("state"));
        phonePayerClm.setCellValueFactory(new PropertyValueFactory<>("phone"));
        revenuePayerClm.setCellValueFactory(new PropertyValueFactory<>("revenue"));

        payerNameClm.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressPayerClm.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityPayerClm.setCellValueFactory(new PropertyValueFactory<>("city"));
        statePayerClm.setCellValueFactory(new PropertyValueFactory<>("state"));
        phonePayerClm.setCellValueFactory(new PropertyValueFactory<>("phone"));
        revenuePayerClm.setCellValueFactory(new PropertyValueFactory<>("revenue"));

        namePractitionerClm.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderClmPersona.setCellValueFactory(new PropertyValueFactory<>("gender"));
        specialityPractitionerClm.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        organizationPractitionerClm.setCellValueFactory(new PropertyValueFactory<>("organization"));
        addressPractitionerClm.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityPractitionerClm.setCellValueFactory(new PropertyValueFactory<>("city"));
        statePractitionerClm.setCellValueFactory(new PropertyValueFactory<>("state"));

        codeEncounterClm.setCellValueFactory(new PropertyValueFactory<>("code"));
        classEncounterClm.setCellValueFactory(new PropertyValueFactory<>("class"));
        descriptionEncounterClm.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateEncounterClm.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        stopDateEncounterClm.setCellValueFactory(new PropertyValueFactory<>("stopDate"));
        patientEncounterClm.setCellValueFactory(new PropertyValueFactory<>("patient"));
        organizationEncounterClm.setCellValueFactory(new PropertyValueFactory<>("organization"));
        practitionerEncounterClm.setCellValueFactory(new PropertyValueFactory<>("practitioner"));
        payerEncounterClm.setCellValueFactory(new PropertyValueFactory<>("payer"));
        costEncounterClm.setCellValueFactory(new PropertyValueFactory<>("cost"));
        coverageEncounterClm.setCellValueFactory(new PropertyValueFactory<>("coverage"));
        observationEncounterClm2.setCellValueFactory(new PropertyValueFactory<>("observation"));
        descriptionEncounterClm2.setCellValueFactory(new PropertyValueFactory<>("description"));

        valueObservationClm.setCellValueFactory(new PropertyValueFactory<>("value"));
        dateObservationClm.setCellValueFactory(new PropertyValueFactory<>("date"));
        patientObservationClm.setCellValueFactory(new PropertyValueFactory<>("patient"));

        codeMedReqClm.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionMedReqClm.setCellValueFactory(new PropertyValueFactory<>("description"));
        baseCostMedReqClm.setCellValueFactory(new PropertyValueFactory<>("baseCost"));
        coverageMedReqClm.setCellValueFactory(new PropertyValueFactory<>("coverage"));
        dispendesMedReqClm.setCellValueFactory(new PropertyValueFactory<>("dispendes"));
        totCostMedReqClm.setCellValueFactory(new PropertyValueFactory<>("totCost"));
        startDateMedReqClm.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        stopDateMedReqClm.setCellValueFactory(new PropertyValueFactory<>("stopDate"));
        patientMedReqClm.setCellValueFactory(new PropertyValueFactory<>("patient"));
        payerMedReqClm.setCellValueFactory(new PropertyValueFactory<>("payer"));

        codeProcedureClm.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionProcedureClm.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateProcedureClm.setCellValueFactory(new PropertyValueFactory<>("date"));
        costProcedureClm.setCellValueFactory(new PropertyValueFactory<>("cost"));
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

        //pulisci tutte le tabelle
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

        //dichiara una variabile bool false
        firstClickPatient = true;
        firstClickOrganization = true;
        firstClickEncounter = true;
        firstClickImaging = true;

    }

    @FXML
    public void patientLabelClick(MouseEvent mouseEvent) {

        if (patientPane.isVisible()) {
            return;
        }

        patientPane.setVisible(true);
        organizationPane.setVisible(false);
        exstEncounterPane.setVisible(false);
        imagingPane.setVisible(false);
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(false);
        //nascondi le altre tabelle
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

            if (patientConverter.getListaCampiPatient().isEmpty()) {
                throw new RuntimeException("ERRORE CONVERSIONE DATI PATIENT");
            }

            for (PatientConverter.PatientClass patient : patientConverter.getListaCampiPatient()) {
                personTable.getItems().add(patient);
            }
        }
    }
    @FXML
    public void organizzationLabelClick(MouseEvent mouseEvent) {

        if (organizationPane.isVisible()) {
            return;
        }

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
            /*
            RICHIAMA I METODI DI DOWNLOAD E CONVERSIONE
            */
        }
    }
    @FXML
    public void encounterLabelClick(MouseEvent mouseEvent) {

        if (exstEncounterPane.isVisible()) {
            return;
        }

        exstEncounterPane.setVisible(true);
        encounterPane1.setVisible(true);
        patientPane.setVisible(false);
        organizationPane.setVisible(false);
        imagingPane.setVisible(false);

        if (firstClickEncounter) {
            firstClickEncounter = false;
            encounterTable.getItems().clear();
            /*
            RICHIAMA I METODI DI DOWNLOAD E CONVERSIONE
            */
        }
    }
    @FXML
    public void imagineLabelClick(MouseEvent mouseEvent) {

        if (imagingPane.isVisible()) {
            return;
        }
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
            RICHIAMA I METODI DI DOWNLOAD E CONVERSIONE
            */
        }
    }

    public void encounterTableClick(MouseEvent mouseEvent) {
        //encounterPane1.setVisible(false);
        //encounterPane2.setVisible(true);

    }

    public void clickPatientTable(MouseEvent mouseEvent) {
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
            System.out.println("\n" + personElement.getName() + "\n " + personElement.getSurname() + "\n " + personElement.getId() + "\n");

            AllergyDownload allergyDownload = new AllergyDownload(personElement.getId());
            allergyDownload.download();
            List<AllergyIntolerance> allergies = allergyDownload.getAllergies();
            AllergyConverter allergyConverter = new AllergyConverter(allergies);
            allergyConverter.convert();
            for (AllergyConverter.AllergyClass allergie : allergyConverter.getListaCampiAllergie()) {
                allergyTable.getItems().add(allergie);
            }

            ImmunizationDownload immunizationDownload = new ImmunizationDownload(personElement.getId());
            immunizationDownload.download();
            List<Immunization> immunizations = immunizationDownload.getImmunizations();
            ImmunizationConverter immunizationConverter = new ImmunizationConverter(immunizations);
            immunizationConverter.convert();
            for (ImmunizationConverter.ImmunizationClass immunization : immunizationConverter.getListaCampiImmunization()) {
                immunizationTable.getItems().add(immunization);
            }

            ConditionDownload conditionDownload = new ConditionDownload(personElement.getId());
            conditionDownload.download();
            List<Condition> conditions = conditionDownload.getConditions();
            ConditionConverter conditionConverter = new ConditionConverter(conditions);
            conditionConverter.convert();
            for (ConditionConverter.ConditionClass condition : conditionConverter.getListaCampiCondition()) {
                conditionTable.getItems().add(condition);
            }
        }

    }

    public void clickConditionTable(MouseEvent mouseEvent) {
        ConditionConverter.ConditionClass conditionElement = conditionTable.getSelectionModel().getSelectedItem();

        if (conditionElement != null) {
            carePlanTable.setVisible(true);
        }
        carePlanTable.getItems().clear();

        CarePlanDownload carePlanDownload = new CarePlanDownload(personElement.getId());
        carePlanDownload.download();
        List<CarePlan> carePlans = carePlanDownload.getCarePlans();
        CarePlanConverter carePlanConverter = new CarePlanConverter(carePlans);
        carePlanConverter.convert();
        for (CarePlanConverter.CarePlanClass carePlan : carePlanConverter.getListaCampiCarePlan()) {
            carePlanTable.getItems().add(carePlan);
        }
    }

    public void loadDataset(ActionEvent actionEvent) {
        try {
            datasetUtility.loadDataset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onScroll1(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) {
            personTable.getItems().clear();
            patientDownload.download();
            List<Patient> patients = patientDownload.getPatients();
            PatientConverter patientConverter = new PatientConverter(patients);
            patientConverter.convert();
            if (patientConverter.getListaCampiPatient().isEmpty()) {
                throw new RuntimeException("ERROR DURING THE SCROLL OF PATIENT");
            }
            for (PatientConverter.PatientClass patient : patientConverter.getListaCampiPatient()) {
                personTable.getItems().add(patient);
            }
        }
    }
}