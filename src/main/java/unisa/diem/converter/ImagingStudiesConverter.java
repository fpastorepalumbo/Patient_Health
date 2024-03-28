package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.ImagingStudy;

import java.util.List;

public class ImagingStudiesConverter extends BaseConverter {

    private final List<ImagingStudy> bundleImagingStudies;
    @Getter
    @FXML
    private final ObservableList<ImagingStudiesClass> fieldsListImagingStudies;

    public ImagingStudiesConverter(List<ImagingStudy> bundleImagingStudies) {
        this.bundleImagingStudies = bundleImagingStudies;
        this.fieldsListImagingStudies = FXCollections.observableArrayList();
    }

    @Override
    public void convert(){
        for(ImagingStudy imagingStudy : bundleImagingStudies) {
            ImagingStudiesClass isc = new ImagingStudiesClass();
            String[] parts;

            isc.setBodySiteCode(imagingStudy.getSeries().get(0).getBodySite().getCode());

            isc.setBodySiteDescription(imagingStudy.getSeries().get(0).getBodySite().getDisplay());

            isc.setModalityCode(imagingStudy.getModality().get(0).getCode());

            isc.setModalityDescription(imagingStudy.getModality().get(0).getDisplay());

            isc.setSopCode(imagingStudy.getSeries().get(0).getInstance().get(0).getSopClass().getCode());

            isc.setSopDescription(imagingStudy.getSeries().get(0).getInstance().get(0).getSopClass().getDisplay());

            parts = imagingStudy.getStarted().toString().split(" ");
            isc.setDate(parts[5]+ "-" + parts[1] + "-" + parts[2]);

            parts = imagingStudy.getSubject().getReference().split("/");
            isc.setPatient(parts[1]);

            parts = imagingStudy.getEncounter().getReference().split("/");
            isc.setEncounter(parts[1]);

            fieldsListImagingStudies.add(isc);
        }

        if (fieldsListImagingStudies.isEmpty()) {
            ImagingStudiesClass isc = new ImagingStudiesClass();
            isc.setBodySiteCode("N/A");
            isc.setBodySiteDescription("N/A");
            isc.setModalityCode("N/A");
            isc.setModalityDescription("N/A");
            isc.setSopCode("N/A");
            isc.setSopDescription("N/A");
            isc.setDate("N/A");
            isc.setPatient("N/A");
            isc.setEncounter("N/A");
            fieldsListImagingStudies.add(isc);
        }
    }

    @Setter
    @Getter
    public static class ImagingStudiesClass {
        private String bodySiteCode;
        private String bodySiteDescription;
        private String modalityCode;
        private String modalityDescription;
        private String sopCode;
        private String sopDescription;
        private String date;
        private String patient;
        private String encounter;

        public ImagingStudiesClass() {
            this.bodySiteCode = "";
            this.bodySiteDescription = "";
            this.modalityCode = "";
            this.modalityDescription = "";
            this.sopCode = "";
            this.sopDescription = "";
            this.date = "";
            this.patient = "";
            this.encounter = "";
        }

        @Override
        public String toString() {
            return "ImagingStudiesClass{" +
                    "bodySiteCode='" + bodySiteCode + '\'' +
                    ", bodySiteDescription='" + bodySiteDescription + '\'' +
                    ", modalityCode='" + modalityCode + '\'' +
                    ", modalityDescription='" + modalityDescription + '\'' +
                    ", sopCode='" + sopCode + '\'' +
                    ", sopDescription='" + sopDescription + '\'' +
                    ", date='" + date + '\'' +
                    ", patient='" + patient + '\'' +
                    ", encounter='" + encounter + '\'' +
                    '}';
        }
    }
}
