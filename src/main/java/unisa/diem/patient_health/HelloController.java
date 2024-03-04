package unisa.diem.patient_health;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.hl7.fhir.r4.model.*;
import unisa.diem.converter.*;
import unisa.diem.downloader.*;
import unisa.diem.parser.DatasetService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public TableView<PatientConverter.PatientClass> personTable;
    @FXML
    public TableView<AllergyConverter.AllergieClass> allergieTable;
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
            // personTable.setItems(patient);
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
        // encounterTable.getSelectionModel().getSelectedItem();
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(true);
    }

    public void clickPatientTable(MouseEvent mouseEvent) {
        personTable.getSelectionModel().getSelectedItem();
        System.out.println("\n"+personTable.getSelectionModel().getSelectedItem().getName() + "\n " + personTable.getSelectionModel().getSelectedItem().getSurname() + "\n " + personTable.getSelectionModel().getSelectedItem().getId() + "\n");
        // carico allergie, immunization e condition
        // carico le relative tabelle
        AllergyDownload allergyDownload = new AllergyDownload(personTable.getSelectionModel().getSelectedItem().getId());
        allergyDownload.download();
        List<AllergyIntolerance> allergies = allergyDownload.getAllergies();
        AllergyConverter allergyConverter = new AllergyConverter(allergies);
        allergyConverter.convert();
        for (AllergyConverter.AllergieClass allergie : allergyConverter.getListaCampiAllergie()) {
            allergieTable.getItems().add(allergie);
            // personTable.setItems(patient);
        }

        ImmunizationDownload immunizationDownload = new ImmunizationDownload(personTable.getSelectionModel().getSelectedItem().getId());
        immunizationDownload.download();
        List<Immunization> immunizations = immunizationDownload.getImmunizations();
        ImmunizationConverter immunizationConverter = new ImmunizationConverter(immunizations);
        immunizationConverter.convert();
        for (ImmunizationConverter.ImmunizationClass immunization : immunizationConverter.getListaCampiImmunization()) {
            immunizationTable.getItems().add(immunization);
            // personTable.setItems(patient);
        }

        ConditionDownload conditionDownload = new ConditionDownload(personTable.getSelectionModel().getSelectedItem().getId());
        conditionDownload.download();
        List<Condition> conditions = conditionDownload.getConditions();
        ConditionConverter conditionConverter = new ConditionConverter(conditions);
        conditionConverter.convert();
        for (ConditionConverter.ConditionClass condition : conditionConverter.getListaCampiCondition()) {
            conditionTable.getItems().add(condition);
            // personTable.setItems(patient);
        }


    }

    public void clickConditionTable(MouseEvent mouseEvent) {

        conditionTable.getSelectionModel().getSelectedItem();
        CarePlanDownload carePlanDownload = new CarePlanDownload(personTable.getSelectionModel().getSelectedItem().getId());
        carePlanDownload.download();
        List<CarePlan> carePlans = carePlanDownload.getCarePlans();
        CarePlanConverter carePlanConverter = new CarePlanConverter(carePlans);
        carePlanConverter.convert();
        for (CarePlanConverter.CarePlanClass carePlan : carePlanConverter.getListaCampiCarePlan()) {
            carePlanTable.getItems().add(carePlan);
            // personTable.setItems(patient);
        }
    }

    public void loadDataset(ActionEvent actionEvent) {

        try {
            datasetUtility.loadDataset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}