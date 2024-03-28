package unisa.diem.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.ImagingStudy;

import java.util.List;

public class ImagingStudiesConverter {


    private List<ImagingStudy> bundleImagingStudies;

    @Getter
    @FXML
    private final ObservableList<ImagingStudiesClass> fieldsListImagingStudies;

    public ImagingStudiesConverter(List<ImagingStudy> bundleImagingStudies) {
        this.bundleImagingStudies = bundleImagingStudies;
        this.fieldsListImagingStudies = FXCollections.observableArrayList();
    }


    public void convert(){
        for(ImagingStudy imagingStudy : bundleImagingStudies) {
            ImagingStudiesClass isc = new ImagingStudiesClass();
            String[] parts;

            isc.setBodySiteCode(imagingStudy.getBodySite().getCoding().get(0).getCode());
            isc.setBodySiteDescription(imagingStudy.getBodySite().getCoding().get(0).getDisplay());
            isc.setModalityCode(imagingStudy.getModality().getCoding().get(0).getCode());
            isc.setModalityDescription(imagingStudy.getModality().getCoding().get(0).getDisplay());
            isc.setSopCode(imagingStudy.getSeries().get(0).getUid());
            isc.setSopDescription(imagingStudy.getSeries().get(0).getModality().getCoding().get(0).getDisplay());
            parts = imagingStudy.getStarted().toString().split(" ");
            isc.setDate(parts[5] + "-" + parts[1] + "-" + parts[2]);
            isc.setPatient(imagingStudy.getSubject().getReference());
            isc.setEncounter(imagingStudy.getEncounter().getReference());

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
        }

    }



    public class ImagingStudiesClass {
        @Getter
        @Setter
        private String bodySiteCode;
        @Getter
        @Setter
        private String bodySiteDescription;
        @Getter
        @Setter
        private String modalityCode;
        @Getter
        @Setter
        private String modalityDescription;
        @Getter
        @Setter
        private String sopCode;
        @Getter
        @Setter
        private String sopDescription;
        @Getter
        @Setter
        private String date;
        @Getter
        @Setter
        private String patient;
        @Getter
        @Setter
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
