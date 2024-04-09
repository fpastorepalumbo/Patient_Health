package unisa.diem.patient_health;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hl7.fhir.r4.model.*;
import unisa.diem.cda.CDAImporter;
import unisa.diem.converter.*;
import unisa.diem.dicom.DicomService;
import unisa.diem.downloader.*;
import unisa.diem.fhir.FhirWrapper;
import unisa.diem.parser.DatasetService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public TableView<PatientConverter.PatientClass> patientTable;
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
    public TableView<ImagingStudiesConverter.ImagingStudiesClass> imageTable;

    //  patientTable
    @FXML
    public TableColumn nameClmPatient;
    @FXML
    public TableColumn surnameClmPatient;
    @FXML
    public TableColumn birthdateClmPatient;
    @FXML
    public TableColumn deathdateClmPatient;
    @FXML
    public TableColumn ssnClmPatient;
    @FXML
    public TableColumn maritalClmPatient;
    @FXML
    public TableColumn genderClmPatient;
    @FXML
    public TableColumn birthplaceClmPatient;
    @FXML
    public TableColumn addressClmPatient;
    @FXML
    public TableColumn cityClmPatient;
    @FXML
    public TableColumn stateClmPatient;

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
    public TableColumn organizationIdClm;
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
    public TableColumn payerIdClm;
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
    public TableColumn idPractitionerClm;
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
    public TableColumn idClmImage;
    @FXML
    public TableColumn bodyCodeClmImage;
    @FXML
    public TableColumn bodyDescrClmImage;
    @FXML
    public TableColumn modeCodeClmImage;
    @FXML
    public TableColumn modeDescrClmImage;
    @FXML
    public TableColumn sopCodeClmImage;
    @FXML
    public TableColumn sopDescrClmImage;
    @FXML
    public TableColumn dateClmImage;
    @FXML
    public TableColumn patientClmImage;
    @FXML
    public TableColumn encounterClmImage;

    private final DicomService dicomService;
    private final CDAImporter cdaImporter;
    public Label patientLabel;
    public Label organizationLabel;
    public Label encounterLabel;
    public Label imageLabel;
    private ObservableList<String> currentFrames;
    @FXML
    public Pane homePaneId;
    @FXML
    public SplitPane splitPaneId;

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
    private Pane imagePane;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;
    @FXML
    private CheckBox checkBox1;
    @FXML
    private CheckBox checkBox2;
    @FXML
    private CheckBox checkBox3;
    @FXML
    private CheckBox checkBox4;
    @FXML
    private CheckBox checkBox5;
    @FXML
    private CheckBox checkBox6;

    public PatientDownload patientDownload;
    public PatientDownload patientDownloadSearch;
    public EncountersDownload encounterDownload;
    public EncountersDownload encounterDownloadSearch;
    public OrganizationDownload organizationDownload;
    public OrganizationDownload organizationDownloadSearch;
    public PayersDownload payerDownload;
    public PayersDownload payerDownloadSearch;
    public PractitionersDownload practitionerDownload;
    public PractitionersDownload practitionerDownloadSearch;
    public ImagingStudiesDownload imagingStudyDownload;
    public ImagingStudiesDownload imagingStudyDownloadSearch;
    public DatasetService datasetUtility;
    public PatientConverter.PatientClass personElement;
    public EncounterConverter.EncounterClass encounterElement;
    public boolean firstClickPatient;
    public boolean firstClickOrganization;
    public boolean firstClickEncounter;
    public boolean firstClickImage;

    public HelloController() {
        patientDownload = new PatientDownload();
        patientDownloadSearch = new PatientDownload();
        encounterDownload = new EncountersDownload();
        encounterDownloadSearch = new EncountersDownload();
        organizationDownload = new OrganizationDownload();
        organizationDownloadSearch = new OrganizationDownload();
        payerDownload = new PayersDownload();
        payerDownloadSearch = new PayersDownload();
        practitionerDownload = new PractitionersDownload();
        practitionerDownloadSearch = new PractitionersDownload();
        imagingStudyDownload = new ImagingStudiesDownload();
        imagingStudyDownloadSearch = new ImagingStudiesDownload();

        datasetUtility = new DatasetService();
        personElement = null;
        encounterElement = null;

        dicomService = new DicomService();
        currentFrames = FXCollections.observableArrayList();
        cdaImporter = new CDAImporter();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homePaneId.setVisible(true);
        homePaneId.setStyle("-fx-background-image: url('file:src/main/resources/images/sfondoHome.png'); " + "-fx-background-size: 1280 720;");

        splitPaneId.setVisible(false);
        patientPane.setVisible(false);
        organizationPane.setVisible(false);
        exstEncounterPane.setVisible(false);
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(false);
        imagePane.setVisible(false);

        searchBar.setVisible(false);
        searchButton.setVisible(false);
        checkBox1.setVisible(false);
        checkBox2.setVisible(false);
        checkBox3.setVisible(false);
        checkBox4.setVisible(false);
        checkBox5.setVisible(false);
        checkBox6.setVisible(false);


        checkBox1.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                checkBox2.setSelected(false);
                checkBox3.setSelected(false);
                checkBox4.setSelected(false);
                checkBox5.setSelected(false);
                checkBox6.setSelected(false);
            }
        });
        checkBox2.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                checkBox1.setSelected(false);
                checkBox3.setSelected(false);
                checkBox4.setSelected(false);
                checkBox5.setSelected(false);
                checkBox6.setSelected(false);
            }
        });
        checkBox3.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                checkBox2.setSelected(false);
                checkBox1.setSelected(false);
                checkBox4.setSelected(false);
                checkBox5.setSelected(false);
                checkBox6.setSelected(false);
            }
        });
        checkBox4.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                checkBox2.setSelected(false);
                checkBox3.setSelected(false);
                checkBox1.setSelected(false);
                checkBox5.setSelected(false);
                checkBox6.setSelected(false);
            }
        });
        checkBox5.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                checkBox2.setSelected(false);
                checkBox3.setSelected(false);
                checkBox4.setSelected(false);
                checkBox1.setSelected(false);
                checkBox6.setSelected(false);
            }
        });
        checkBox6.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                checkBox2.setSelected(false);
                checkBox3.setSelected(false);
                checkBox4.setSelected(false);
                checkBox5.setSelected(false);
                checkBox1.setSelected(false);
            }
        });

        nameClmPatient.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameClmPatient.setCellValueFactory(new PropertyValueFactory<>("surname"));
        birthdateClmPatient.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        deathdateClmPatient.setCellValueFactory(new PropertyValueFactory<>("deathdate"));
        ssnClmPatient.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        maritalClmPatient.setCellValueFactory(new PropertyValueFactory<>("marital"));
        genderClmPatient.setCellValueFactory(new PropertyValueFactory<>("gender"));
        birthplaceClmPatient.setCellValueFactory(new PropertyValueFactory<>("birthplace"));
        addressClmPatient.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityClmPatient.setCellValueFactory(new PropertyValueFactory<>("city"));
        stateClmPatient.setCellValueFactory(new PropertyValueFactory<>("state"));

        nameClmPatient.setCellFactory(TextFieldTableCell.forTableColumn());

        codeClmAllergy.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionClmAllergy.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateClmAllergy.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        stopDateClmAllergy.setCellValueFactory(new PropertyValueFactory<>("stopDate"));
        encounterClmAllergy.setCellValueFactory(new PropertyValueFactory<>("encounter"));

        codeClmAllergy.setCellFactory(TextFieldTableCell.forTableColumn());

        codeClmImmunization.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionClmImmunization.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateClmImmunization.setCellValueFactory(new PropertyValueFactory<>("date"));
        encounterClmImmunization.setCellValueFactory(new PropertyValueFactory<>("encounter"));

        codeClmImmunization.setCellFactory(TextFieldTableCell.forTableColumn());

        codeClmCondition.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionClmCondition.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateClmCondition.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        stopDateClmCondition.setCellValueFactory(new PropertyValueFactory<>("stopDate"));
        encounterClmCondition.setCellValueFactory(new PropertyValueFactory<>("encounter"));

        codeClmCondition.setCellFactory(TextFieldTableCell.forTableColumn());

        codeClmCarePlan.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionClmCarePlan.setCellValueFactory(new PropertyValueFactory<>("description"));
        reasonCodeClmCarePlan.setCellValueFactory(new PropertyValueFactory<>("reasonCode"));
        reasonDescriptionClmCarePlan.setCellValueFactory(new PropertyValueFactory<>("reasonDescription"));

        codeClmCarePlan.setCellFactory(TextFieldTableCell.forTableColumn());

        organizationIdClm.setCellValueFactory(new PropertyValueFactory<>("id"));
        organizationNameClm.setCellValueFactory(new PropertyValueFactory<>("name"));
        cityOrganizationClm.setCellValueFactory(new PropertyValueFactory<>("city"));
        stateOrganizationClm.setCellValueFactory(new PropertyValueFactory<>("state"));
        phoneOrganizationClm.setCellValueFactory(new PropertyValueFactory<>("phone"));

        organizationNameClm.setCellFactory(TextFieldTableCell.forTableColumn());

        payerIdClm.setCellValueFactory(new PropertyValueFactory<>("id"));
        payerNameClm.setCellValueFactory(new PropertyValueFactory<>("name"));
        cityPayerClm.setCellValueFactory(new PropertyValueFactory<>("city"));
        statePayerClm.setCellValueFactory(new PropertyValueFactory<>("state"));
        phonePayerClm.setCellValueFactory(new PropertyValueFactory<>("phone"));

        payerNameClm.setCellFactory(TextFieldTableCell.forTableColumn());

        idPractitionerClm.setCellValueFactory(new PropertyValueFactory<>("id"));
        namePractitionerClm.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderPractitionerClm.setCellValueFactory(new PropertyValueFactory<>("gender"));
        specialtyPractitionerClm.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        organizationPractitionerClm.setCellValueFactory(new PropertyValueFactory<>("organization"));
        addressPractitionerClm.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityPractitionerClm.setCellValueFactory(new PropertyValueFactory<>("city"));
        statePractitionerClm.setCellValueFactory(new PropertyValueFactory<>("state"));

        namePractitionerClm.setCellFactory(TextFieldTableCell.forTableColumn());

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

        idEncounterClm.setCellFactory(TextFieldTableCell.forTableColumn());

        observationEncounterClm2.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionEncounterClm2.setCellValueFactory(new PropertyValueFactory<>("description"));
        valueObservationClm.setCellValueFactory(new PropertyValueFactory<>("value"));
        dateObservationClm.setCellValueFactory(new PropertyValueFactory<>("date"));
        patientObservationClm.setCellValueFactory(new PropertyValueFactory<>("patient"));

        observationEncounterClm2.setCellFactory(TextFieldTableCell.forTableColumn());

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

        codeMedReqClm.setCellFactory(TextFieldTableCell.forTableColumn());

        codeProcedureClm.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionProcedureClm.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateProcedureClm.setCellValueFactory(new PropertyValueFactory<>("date"));
        patientProcedureClm.setCellValueFactory(new PropertyValueFactory<>("patient"));

        codeProcedureClm.setCellFactory(TextFieldTableCell.forTableColumn());

        codeDeviceClm.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionDeviceClm.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateDeviceClm.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        stopDateDeviceClm.setCellValueFactory(new PropertyValueFactory<>("stopDate"));
        patientDeviceClm.setCellValueFactory(new PropertyValueFactory<>("patient"));

        codeDeviceClm.setCellFactory(TextFieldTableCell.forTableColumn());

        idClmImage.setCellValueFactory(new PropertyValueFactory<>("id"));
        idClmImage.setVisible(false);
        bodyCodeClmImage.setCellValueFactory(new PropertyValueFactory<>("bodySiteCode"));
        bodyDescrClmImage.setCellValueFactory(new PropertyValueFactory<>("bodySiteDescription"));
        modeCodeClmImage.setCellValueFactory(new PropertyValueFactory<>("modalityCode"));
        modeDescrClmImage.setCellValueFactory(new PropertyValueFactory<>("modalityDescription"));
        sopCodeClmImage.setCellValueFactory(new PropertyValueFactory<>("sopCode"));
        sopDescrClmImage.setCellValueFactory(new PropertyValueFactory<>("sopDescription"));
        dateClmImage.setCellValueFactory(new PropertyValueFactory<>("date"));
        patientClmImage.setCellValueFactory(new PropertyValueFactory<>("patient"));
        encounterClmImage.setCellValueFactory(new PropertyValueFactory<>("encounter"));

        patientTable.getItems().clear();
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
        firstClickImage = true;
    }

    public void loadDataset() {
        try {
            datasetUtility.loadDataset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void homeLabelClick() {
        splitPaneId.setVisible(false);
        homePaneId.setVisible(true);
        homePaneId.setStyle("-fx-background-image: url('file:src/main/resources/images/sfondoHome.png'); " + "-fx-background-size: 1280 720;");
    }


    @FXML
    public void patientLabelClick() {
        if (patientPane.isVisible())
            return;

        setSearchPatient();

        patientPane.setVisible(true);
        organizationPane.setVisible(false);
        exstEncounterPane.setVisible(false);
        imagePane.setVisible(false);
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(false);

        allergyTable.setVisible(false);
        conditionTable.setVisible(false);
        immunizationTable.setVisible(false);
        carePlanTable.setVisible(false);

        if (firstClickPatient) {
            firstClickPatient = false;
            patientTable.getItems().clear();

            patientDownload.download();
            List<Patient> patients = patientDownload.getPatients();
            PatientConverter patientConverter = new PatientConverter(patients);
            patientConverter.convert();

            for (PatientConverter.PatientClass patient : patientConverter.getFieldsListPatient())
                patientTable.getItems().add(patient);
            autoResizeColumns(patientTable);
        }
    }

    @FXML
    public void organizationLabelClick() {
        if (organizationPane.isVisible())
            return;

        setSearchOrganization();

        organizationPane.setVisible(true);
        patientPane.setVisible(false);
        exstEncounterPane.setVisible(false);
        imagePane.setVisible(false);
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

            for (OrganizationConverter.OrganizationClass organization : organizationConverter.getFieldsListOrganization())
                organizationTable.getItems().add(organization);
            autoResizeColumns(organizationTable);

            payerDownload.download();
            List<Organization> payers = payerDownload.getPayers();
            PayersConverter payersConverter = new PayersConverter(payers);
            payersConverter.convert();

            for (PayersConverter.PayersClass payer : payersConverter.getFieldsListPayer())
                payerTable.getItems().add(payer);
            autoResizeColumns(payerTable);

            practitionerDownload.download();
            List<Practitioner> practitioners = practitionerDownload.getPractitioners();
            PractitionersConverter practitionersConverter = new PractitionersConverter(practitioners);
            practitionersConverter.convert();

            for (PractitionersConverter.PractitionerClass practitioner : practitionersConverter.getFieldsListPractitioner()) {
                practitionerTable.getItems().add(practitioner);
            }
            autoResizeColumns(practitionerTable);
        }
    }

    @FXML
    public void encounterLabelClick() {
        if (exstEncounterPane.isVisible())
            return;

        setSearchEncounter();

        exstEncounterPane.setVisible(true);
        encounterPane1.setVisible(true);
        encounterPane2.setVisible(false);
        patientPane.setVisible(false);
        organizationPane.setVisible(false);
        imagePane.setVisible(false);

        if (firstClickEncounter) {
            firstClickEncounter = false;
            encounterTable.getItems().clear();
            encounterDownload.download();
            List<Encounter> encounters = encounterDownload.getEncounters();
            EncounterConverter encounterConverter = new EncounterConverter(encounters);
            encounterConverter.convert();

            for (EncounterConverter.EncounterClass encounter : encounterConverter.getFieldsListEncounter())
                encounterTable.getItems().add(encounter);
            autoResizeColumns(encounterTable);
        }
    }

    @FXML
    public void imageLabelClick() {
        if (imagePane.isVisible())
            return;

        setSearchImage();

        imagePane.setVisible(true);
        patientPane.setVisible(false);
        organizationPane.setVisible(false);
        exstEncounterPane.setVisible(false);
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(false);

        if (firstClickImage) {
            firstClickImage = false;
            imageTable.getItems().clear();
            imagingStudyDownload.download();
            List<ImagingStudy> imagingStudies = imagingStudyDownload.getImages();
            ImagingStudiesConverter imagingStudiesConverter = new ImagingStudiesConverter(imagingStudies);
            imagingStudiesConverter.convert();

            for (ImagingStudiesConverter.ImagingStudiesClass imagingStudy : imagingStudiesConverter.getFieldsListImagingStudies())
                imageTable.getItems().add(imagingStudy);
            autoResizeColumns(imageTable);
        }
    }

    public void patientClick() {
        splitPaneId.setVisible(true);
        homePaneId.setVisible(false);
        patientLabelClick();
    }

    public void organizationClick() {
        splitPaneId.setVisible(true);
        homePaneId.setVisible(false);
        organizationLabelClick();
    }

    public void encounterClick() {
        splitPaneId.setVisible(true);
        homePaneId.setVisible(false);
        encounterLabelClick();
    }

    public void imagingStudyClick() {
        splitPaneId.setVisible(true);
        homePaneId.setVisible(false);
        imageLabelClick();
    }

    public void patientScrollTable(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) {
            patientTable.getItems().clear();
            patientDownload.download();
            List<Patient> patients = patientDownload.getPatients();
            PatientConverter patientConverter = new PatientConverter(patients);
            patientConverter.convert();
            for (PatientConverter.PatientClass patient : patientConverter.getFieldsListPatient())
                patientTable.getItems().add(patient);
            autoResizeColumns(patientTable);
        }
    }

    public void organizationScrollTable(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) {
            organizationTable.getItems().clear();
            organizationDownload.download();
            List<Organization> organizations = organizationDownload.getOrganizations();
            OrganizationConverter organizationConverter = new OrganizationConverter(organizations);
            organizationConverter.convert();
            for (OrganizationConverter.OrganizationClass organization : organizationConverter.getFieldsListOrganization())
                organizationTable.getItems().add(organization);
            autoResizeColumns(organizationTable);
        }
    }

    public void practitionerScrollTable(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) {
            practitionerTable.getItems().clear();
            practitionerDownload.download();
            List<Practitioner> practitioners = practitionerDownload.getPractitioners();
            PractitionersConverter practitionersConverter = new PractitionersConverter(practitioners);
            practitionersConverter.convert();
            for (PractitionersConverter.PractitionerClass practitioner : practitionersConverter.getFieldsListPractitioner())
                practitionerTable.getItems().add(practitioner);
            autoResizeColumns(practitionerTable);
        }
    }

    public void encounterScrollTable(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) {
            encounterTable.getItems().clear();
            encounterDownload.download();
            List<Encounter> encounters = encounterDownload.getEncounters();
            EncounterConverter encounterConverter = new EncounterConverter(encounters);
            encounterConverter.convert();
            for (EncounterConverter.EncounterClass encounter : encounterConverter.getFieldsListEncounter())
                encounterTable.getItems().add(encounter);
            autoResizeColumns(encounterTable);
        }
    }

    public void imageScrollTable(ScrollEvent scrollEvent) {
        if(scrollEvent.getDeltaY()<0){
            imageTable.getItems().clear();
            imagingStudyDownload.download();
            List<ImagingStudy> imagingStudies = imagingStudyDownload.getImages();
            ImagingStudiesConverter imagingStudiesConverter = new ImagingStudiesConverter(imagingStudies);
            imagingStudiesConverter.convert();
            for (ImagingStudiesConverter.ImagingStudiesClass imagingStudy : imagingStudiesConverter.getFieldsListImagingStudies())
                imageTable.getItems().add(imagingStudy);
            autoResizeColumns(imageTable);
        }
    }

    public void searchButtonClick() {
        String search1 = searchBar.getText();
        searchBar.clear();

        if (search1.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "No arguments find").showAndWait();
            return;
        }

        if(patientPane.isVisible()){
            firstClickPatient = true;

            allergyTable.visibleProperty().setValue(false);
            conditionTable.visibleProperty().setValue(false);
            immunizationTable.visibleProperty().setValue(false);
            carePlanTable.visibleProperty().setValue(false);

            if (checkBox1.isSelected())
                patientDownloadSearch.downloadPatientWithId(search1);
            else if (checkBox2.isSelected()){
                if(search1.contains(" ")){
                    String[] search = search1.split(" ");
                    patientDownloadSearch.downloadPatientWithName(search[0], search[1]);
                } else {
                    patientDownloadSearch.downloadPatientWithName(search1, "");
                }
            }

            List<Patient> patients = patientDownloadSearch.getPatientSearch();
            PatientConverter patientConverter = new PatientConverter(patients);
            patientConverter.convert();

            patientTable.getItems().clear();
            for (PatientConverter.PatientClass patient : patientConverter.getFieldsListPatient()) {
             if(!patient.getVuoto())
                patientTable.getItems().add(patient);
             else
                 new Alert(Alert.AlertType.ERROR, "Patient not found").showAndWait();
            }
            autoResizeColumns(patientTable);

        } else if (organizationPane.isVisible()) {
            firstClickOrganization = true;
            if (checkBox1.isSelected()){
                organizationTable.getItems().clear();
                organizationDownloadSearch.downloadOrganizationWithName(search1);
                List<Organization> organizations = organizationDownloadSearch.getOrganizationsSearch();
                OrganizationConverter organizationConverter = new OrganizationConverter(organizations);
                organizationConverter.convert();
                for (OrganizationConverter.OrganizationClass organization : organizationConverter.getFieldsListOrganization())
                    if(!organization.getVuoto())
                        organizationTable.getItems().add(organization);
                    else
                        new Alert(Alert.AlertType.ERROR, "Organization not found").showAndWait();
                autoResizeColumns(organizationTable);

            } else if (checkBox2.isSelected()){
                organizationTable.getItems().clear();
                organizationDownloadSearch.downloadOrganizationWithId(search1);
                List<Organization> organizations = organizationDownloadSearch.getOrganizationsSearch();
                OrganizationConverter organizationConverter = new OrganizationConverter(organizations);
                organizationConverter.convert();
                for (OrganizationConverter.OrganizationClass organization : organizationConverter.getFieldsListOrganization()) {
                    if (!organization.getVuoto())
                        organizationTable.getItems().add(organization);
                    else
                        new Alert(Alert.AlertType.ERROR, "Organization not found").showAndWait();
                }
                autoResizeColumns(organizationTable);
            }
            else if (checkBox3.isSelected()){
                payerTable.getItems().clear();
                payerDownloadSearch.downloadPayerWithName(search1);
                List<Organization> payers = payerDownloadSearch.getPayerSearch();
                PayersConverter payersConverter = new PayersConverter(payers);
                payersConverter.convert();
                for (PayersConverter.PayersClass payer : payersConverter.getFieldsListPayer())
                    if (!payer.getVuoto())
                        payerTable.getItems().add(payer);
                    else
                        new Alert(Alert.AlertType.ERROR, "Payer not found").showAndWait();
                autoResizeColumns(payerTable);
            } else if (checkBox4.isSelected()){
                payerTable.getItems().clear();
                payerDownloadSearch.downloadPayerWithId(search1);
                List<Organization> payers = payerDownloadSearch.getPayerSearch();
                PayersConverter payersConverter = new PayersConverter(payers);
                payersConverter.convert();
                for (PayersConverter.PayersClass payer : payersConverter.getFieldsListPayer())
                    if (!payer.getVuoto())
                        payerTable.getItems().add(payer);
                    else
                        new Alert(Alert.AlertType.ERROR, "Payer not found").showAndWait();
                autoResizeColumns(payerTable);
            }
            else if (checkBox5.isSelected()){
                practitionerTable.getItems().clear();
                practitionerDownloadSearch.downloadPractitionerWithName(search1);
                List<Practitioner> practitioners = practitionerDownloadSearch.getPractitionerSearch();
                PractitionersConverter practitionersConverter = new PractitionersConverter(practitioners);
                practitionersConverter.convert();
                for (PractitionersConverter.PractitionerClass practitioner : practitionersConverter.getFieldsListPractitioner())
                    if (!practitioner.getVuoto())
                        practitionerTable.getItems().add(practitioner);
                    else
                        new Alert(Alert.AlertType.ERROR, "Practitioner not found").showAndWait();
                autoResizeColumns(practitionerTable);

            } else if (checkBox6.isSelected()){
                practitionerTable.getItems().clear();
                practitionerDownloadSearch.downloadPractitionerWithId(search1);
                List<Practitioner> practitioners = practitionerDownloadSearch.getPractitionerSearch();
                PractitionersConverter practitionersConverter = new PractitionersConverter(practitioners);
                practitionersConverter.convert();
                for (PractitionersConverter.PractitionerClass practitioner : practitionersConverter.getFieldsListPractitioner())
                    if (!practitioner.getVuoto())
                        practitionerTable.getItems().add(practitioner);
                    else
                        new Alert(Alert.AlertType.ERROR, "Practitioner not found").showAndWait();
                autoResizeColumns(practitionerTable);
            }

        } else if (exstEncounterPane.isVisible() && encounterPane1.isVisible()) {
            firstClickEncounter = true;

            encounterTable.getItems().clear();

            if (checkBox1.isSelected())
                encounterDownloadSearch.downloadEncounterWithEncounterId(search1);
            else if (checkBox2.isSelected())
                encounterDownloadSearch.downloadEncounterWithPatientId(search1);

            List<Encounter> encounter = encounterDownloadSearch.getEncounterSearch();
            EncounterConverter encounterConverter = new EncounterConverter(encounter);
            encounterConverter.convert();
            for (EncounterConverter.EncounterClass encounter1 : encounterConverter.getFieldsListEncounter())
                if (!encounter1.getVuoto())
                    encounterTable.getItems().add(encounter1);
                else
                    new Alert(Alert.AlertType.ERROR, "Encounter not found").showAndWait();
            autoResizeColumns(encounterTable);

        } else if (imagePane.isVisible()) {
            firstClickImage = true;
            imageTable.getItems().clear();

            if (checkBox1.isSelected())
                imagingStudyDownloadSearch.downloadImageWithPatientId(search1);
            else if (checkBox2.isSelected())
                imagingStudyDownloadSearch.downloadImageWithEncounterId(search1);

            List<ImagingStudy> imagingStudies = imagingStudyDownloadSearch.getImageSearch();
            ImagingStudiesConverter imagingStudiesConverter = new ImagingStudiesConverter(imagingStudies);
            imagingStudiesConverter.convert();
            for (ImagingStudiesConverter.ImagingStudiesClass imagingStudy : imagingStudiesConverter.getFieldsListImagingStudies())
                if (!imagingStudy.getVuoto())
                    imageTable.getItems().add(imagingStudy);
                else
                    new Alert(Alert.AlertType.ERROR, "Image not found").showAndWait();
            autoResizeColumns(imageTable);
        }
    }

    public void setSearchPatient() {
        searchBar.setVisible(true);
        searchButton.setVisible(true);
        checkBox1.setVisible(true);
        checkBox1.setText("Patient ID");
        checkBox2.setVisible(true);
        checkBox2.setText("Patient Name and Surname");
        checkBox3.setVisible(false);
        checkBox4.setVisible(false);
        checkBox5.setVisible(false);
        checkBox6.setVisible(false);
        checkBox1.setSelected(true);
    }

    public void setSearchOrganization() {
        searchBar.setVisible(true);
        searchButton.setVisible(true);
        checkBox1.setVisible(true);
        checkBox1.setText("Organization Name");
        checkBox2.setVisible(true);
        checkBox2.setText("Organization ID");
        checkBox3.setVisible(true);
        checkBox3.setText("Payer Name");
        checkBox4.setVisible(true);
        checkBox4.setText("Payer ID");
        checkBox5.setVisible(true);
        checkBox5.setText("Practitioner Name and Surname");
        checkBox6.setVisible(true);
        checkBox6.setText("Practitioner ID");
        checkBox1.setSelected(true);
    }

    public void setSearchOff() {
        searchBar.setVisible(false);
        searchButton.setVisible(false);
        checkBox1.setVisible(false);
        checkBox2.setVisible(false);
        checkBox3.setVisible(false);
        checkBox4.setVisible(false);
        checkBox5.setVisible(false);
        checkBox6.setVisible(false);
    }

    public void setSearchEncounter() {
        searchBar.setVisible(true);
        searchButton.setVisible(true);
        checkBox1.setVisible(true);
        checkBox1.setText("Encounter ID");
        checkBox2.setVisible(true);
        checkBox2.setText("Patient ID");
        checkBox3.setVisible(false);
        checkBox4.setVisible(false);
        checkBox5.setVisible(false);
        checkBox6.setVisible(false);
        checkBox1.setSelected(true);
    }

    public void setSearchImage() {
        searchBar.setVisible(true);
        searchButton.setVisible(true);
        checkBox1.setVisible(true);
        checkBox1.setText("Patient ID");
        checkBox2.setVisible(true);
        checkBox2.setText("Encounter ID");
        checkBox3.setVisible(false);
        checkBox4.setVisible(false);
        checkBox5.setVisible(false);
        checkBox6.setVisible(false);
        checkBox1.setSelected(true);
    }

    public void generateCDAEncounter() {
        String id = encounterTable.getSelectionModel().getSelectedItem().getId();
        cdaImporter.saveCDAToFhir(id);
    }

    public void copyID(String id) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(id);
        clipboard.setContent(content);
    }

    public void copyIDPatient() {
        String id = patientTable.getSelectionModel().getSelectedItem().getId();
        copyID(id);
    }

    public void copyIDOrganization() {
        String id = organizationTable.getSelectionModel().getSelectedItem().getId();
        copyID(id);
    }

    public void copyIDPayer() {
        String id = payerTable.getSelectionModel().getSelectedItem().getId();
        copyID(id);
    }
    public void copyIDPractitioner() {
        String id = practitionerTable.getSelectionModel().getSelectedItem().getId();
        copyID(id);
    }

    public void copyIDEncounter() {
        String id = encounterTable.getSelectionModel().getSelectedItem().getId();
        copyID(id);
    }

    public void copyIDPatientOnEncounter() {
        String id = encounterTable.getSelectionModel().getSelectedItem().getPatient();
        copyID(id);
    }

    public void copyIDOrganizationOnEncounter() {
        String id = encounterTable.getSelectionModel().getSelectedItem().getOrganization();
        copyID(id);
    }

    public void copyIDPayerOnEncounter() {
        String id = encounterTable.getSelectionModel().getSelectedItem().getPayer();
        copyID(id);
    }

    public void copyIDPractitionerOnEncounter() {
        String id = encounterTable.getSelectionModel().getSelectedItem().getPractitioner();
        copyID(id);
    }

    public void copyNameOrganization() {
        String id = organizationTable.getSelectionModel().getSelectedItem().getName();
        copyID(id);
    }

    public void copyNamePayer() {
        String id = payerTable.getSelectionModel().getSelectedItem().getName();
        copyID(id);
    }

    public void copyNamePractitioner() {
        String id = practitionerTable.getSelectionModel().getSelectedItem().getName();
        copyID(id);
    }

    public void copyCodeDevice() {
        String id = deviceTable.getSelectionModel().getSelectedItem().getCode();
        copyID(id);
    }

    public void copyCodeProcedure() {
        String id = procedureTable.getSelectionModel().getSelectedItem().getCode();
        copyID(id);
    }

    public void copyCodeMedReq() {
        String id = medReqTable.getSelectionModel().getSelectedItem().getCode();
        copyID(id);
    }

    public void copyCodeObservation() {
        String id = observationTable.getSelectionModel().getSelectedItem().getCode();
        copyID(id);
    }

    public void examinePatient() {
        allergyTable.getItems().clear();
        conditionTable.getItems().clear();
        immunizationTable.getItems().clear();
        carePlanTable.getItems().clear();
        personElement = patientTable.getSelectionModel().getSelectedItem();

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
            for (AllergyConverter.AllergyClass allergy : allergyConverter.getFieldsListAllergy())
                allergyTable.getItems().add(allergy);
            autoResizeColumns(allergyTable);

            ImmunizationDownload immunizationDownload = new ImmunizationDownload(personElement.getId());
            immunizationDownload.download();
            List<Immunization> immunizations = immunizationDownload.getImmunizations();
            ImmunizationConverter immunizationConverter = new ImmunizationConverter(immunizations);
            immunizationConverter.convert();
            for (ImmunizationConverter.ImmunizationClass immunization : immunizationConverter.getFieldsListImmunization())
                immunizationTable.getItems().add(immunization);
            autoResizeColumns(immunizationTable);

            ConditionDownload conditionDownload = new ConditionDownload(personElement.getId());
            conditionDownload.download();
            List<Condition> conditions = conditionDownload.getConditions();
            ConditionConverter conditionConverter = new ConditionConverter(conditions);
            conditionConverter.convert();
            for (ConditionConverter.ConditionClass condition : conditionConverter.getFieldsListCondition())
                conditionTable.getItems().add(condition);
            autoResizeColumns(conditionTable);
        }
    }

    public void examineCondition() {
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
        autoResizeColumns(carePlanTable);
    }

    public void examineEncounter() {
        setSearchOff();
        exstEncounterPane.setVisible(true);
        encounterPane1.setVisible(false);
        encounterPane2.setVisible(true);
        patientPane.setVisible(false);
        organizationPane.setVisible(false);
        imagePane.setVisible(false);

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

            ObservationsDownload observationDownload = new ObservationsDownload(encID);
            observationDownload.download();
            List<Observation> observations = observationDownload.getObservations();
            ObservationsConverter observationsConverter = new ObservationsConverter(observations);
            observationsConverter.convert();
            for (ObservationsConverter.ObservationClass observation : observationsConverter.getFieldsListObservation())
                observationTable.getItems().add(observation);
            autoResizeColumns(observationTable);

            MedicationRequestDownload medReqDownload = new MedicationRequestDownload(encID);
            medReqDownload.download();
            List<MedicationRequest> medReqs = medReqDownload.getMedicationRequests();
            MedicationRequestsConverter medReqConverter = new MedicationRequestsConverter(medReqs);
            medReqConverter.convert();
            for (MedicationRequestsConverter.MedicationRequestsClass medReq : medReqConverter.getFieldsListMedRequest())
                medReqTable.getItems().add(medReq);
            autoResizeColumns(medReqTable);

            ProceduresDownload procedureDownload = new ProceduresDownload(encID);
            procedureDownload.download();
            List<Procedure> procedures = procedureDownload.getProcedures();
            ProceduresConverter procedureConverter = new ProceduresConverter(procedures);
            procedureConverter.convert();
            for (ProceduresConverter.ProcedureClass procedure : procedureConverter.getFieldsListProcedure())
                procedureTable.getItems().add(procedure);
            autoResizeColumns(procedureTable);

            DevicesDownload deviceDownload = new DevicesDownload(encID);
            deviceDownload.download();
            List<DeviceRequest> devices = deviceDownload.getDevices();
            DevicesConverter deviceConverter = new DevicesConverter(devices);
            deviceConverter.convert();
            for (DevicesConverter.DeviceClass device : deviceConverter.getFieldsListDevices())
                deviceTable.getItems().add(device);
            autoResizeColumns(deviceTable);
        }
    }

    public void showImages(Stage primaryStage) {
        GridPane gridPane = new GridPane();

        Image[] images = new Image[256];
        Base64.Decoder base64Decoder = Base64.getDecoder();
        for (int i = 0; i < 256; i++) {
            ByteArrayInputStream imgInputStream = new ByteArrayInputStream(base64Decoder.decode(currentFrames.get(i)));
            images[i] = new Image(imgInputStream);
        }

        for (int i = 0; i < 256; i++) {
            ImageView imageView = new ImageView(images[i]);
            imageView.setFitWidth(60);
            imageView.setFitHeight(50);
            gridPane.add(imageView, i/16, i%16);
        }

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Image Grid");
        primaryStage.show();
    }

    public void clickImageTable() {
        ImagingStudiesConverter.ImagingStudiesClass imageElement = imageTable.getSelectionModel().getSelectedItem();

        if (imageElement != null) {
            ImagingStudy im = FhirWrapper.getClient().read().resource(ImagingStudy.class).withId(imageElement.getId()).execute();

            currentFrames = dicomService.getDicomFile(im).serveAllFrames();
            showImages(new Stage());
        }
    }

    public static void autoResizeColumns(TableView<?> table) {
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().forEach((column) ->
        {
            Text t = new Text(column.getText());
            double max = t.getLayoutBounds().getWidth();
            for (int i = 0; i < table.getItems().size(); i++)
            {
                if (column.getCellData(i) != null)
                {
                    t = new Text(column.getCellData(i).toString());
                    double calcWidth = t.getLayoutBounds().getWidth();
                    if (calcWidth > max)
                    {
                        max = calcWidth;
                    }
                }
            }
            column.setPrefWidth(max + 10.0d );
        });
    }
}
