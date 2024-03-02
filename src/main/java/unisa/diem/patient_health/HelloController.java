package unisa.diem.patient_health;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Person;
import unisa.diem.converter.*;
import unisa.diem.downloader.AllergieDownload;
import unisa.diem.downloader.PatientDownload;
import unisa.diem.parser.DatasetService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public TableView<PatientConverter.PatientClass> personTable;
    @FXML
    public TableView<AllergieConverter.AllergieClass> allergieTable;
    @FXML
    public TableView<ConditionConverter.ConditionClass> conditionTable;
    @FXML
    public TableView<ImmunizationConverter.ImmunizationClass> immunizationTable;
    @FXML
    public TableView<CarePlanConverter.CarePlanClass> carePlanTable;
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
    public TableColumn expensesClmPersona;
    @FXML
    public TableColumn coverageClmPersona;
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
    public TableColumn baseCostClmImmunization;
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
    public TableView encounterTable;

    public PatientDownload patientDownload = new PatientDownload();


    public DatasetService datasetUtility = new DatasetService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patientPane.setVisible(false);
        organizationPane.setVisible(false);
        exstEncounterPane.setVisible(false);
        imagingPane.setVisible(false);
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(false);
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
        expensesClmPersona.setCellValueFactory(new PropertyValueFactory<>("expenses"));
        coverageClmPersona.setCellValueFactory(new PropertyValueFactory<>("coverage"));

        codeClmAllergy.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionClmAllergy.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateClmAllergy.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        stopDateClmAllergy.setCellValueFactory(new PropertyValueFactory<>("stopDate"));
        encounterClmAllergy.setCellValueFactory(new PropertyValueFactory<>("encounter"));

        codeClmImmunization.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionClmImmunization.setCellValueFactory(new PropertyValueFactory<>("description"));
        baseCostClmImmunization.setCellValueFactory(new PropertyValueFactory<>("baseCost"));
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

    }
    @FXML
    public void patientLabelClick(MouseEvent mouseEvent) {

        patientPane.setVisible(true);
        organizationPane.setVisible(false);
        exstEncounterPane.setVisible(false);
        imagingPane.setVisible(false);
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(false);

        patientDownload.download();

        List<Patient> patients = patientDownload.getPatients();

        PatientConverter patientConverter = new PatientConverter(patients);

        patientConverter.convert();

        if (patientConverter.getListaCampiPatient().isEmpty()) {
        throw new RuntimeException("PAR O CAZZ FRAAAAA!!!");
        }

        for (PatientConverter.PatientClass patient : patientConverter.getListaCampiPatient()) {
            personTable.getItems().add(patient);
            //personTable.setItems(patient);
        }


    }
    @FXML
    public void organizzationLabelClick(MouseEvent mouseEvent) {
        organizationPane.setVisible(true);
        patientPane.setVisible(false);
        exstEncounterPane.setVisible(false);
        imagingPane.setVisible(false);
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(false);
    }
    @FXML
    public void encounterLabelClick(MouseEvent mouseEvent) {
        exstEncounterPane.setVisible(true);
        encounterPane1.setVisible(true);
        patientPane.setVisible(false);
        organizationPane.setVisible(false);
        imagingPane.setVisible(false);
    }
    @FXML
    public void imagineLabelClick(MouseEvent mouseEvent) {
        imagingPane.setVisible(true);
        patientPane.setVisible(false);
        organizationPane.setVisible(false);
        exstEncounterPane.setVisible(false);
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(false);
        /*
        try {
            datasetUtility.loadDataset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

         */
    }


    public void encounterTableClick(MouseEvent mouseEvent) {
        //encounterTable.getSelectionModel().getSelectedItem();
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(true);
    }

    public void clickPatientTable(MouseEvent mouseEvent) {
        personTable.getSelectionModel().getSelectedItem();
        System.out.println("\n"+personTable.getSelectionModel().getSelectedItem().getName() + "\n " + personTable.getSelectionModel().getSelectedItem().getSurname() + "\n " + personTable.getSelectionModel().getSelectedItem().getId() + "\n");
        //carico allergie, immunization e condition
        //carico le relative tabelle
        AllergieDownload allergieDownload = new AllergieDownload(personTable.getSelectionModel().getSelectedItem().getId());
        allergieDownload.download();
        List<AllergyIntolerance> allergies = allergieDownload.getAllergies();
        AllergieConverter allergieConverter = new AllergieConverter(allergies);
        allergieConverter.convert();

        for (AllergieConverter.AllergieClass allergie : allergieConverter.getListaCampiAllergie()) {
            allergieTable.getItems().add(allergie);
            //personTable.setItems(patient);
        }

    }
}