module unisa.diem.patient_health {
    requires javafx.controls;
    requires javafx.fxml;
    requires dicom;
    requires java.desktop;
    requires java.logging;
    requires org.apache.commons.codec;
    requires org.apache.commons.io;
    requires org.hl7.fhir.r4;
    requires hapi.fhir.base;
    requires lombok;
    requires org.apache.commons.csv;
    requires com.fasterxml.jackson.core;
    requires org.apache.commons.lang3;
    requires ipf.oht.mdht.uml.cda;
    requires ipf.oht.mdht.uml.hl7.datatypes;
    requires ipf.oht.mdht.uml.hl7.vocab;
    requires commons.net;


    opens unisa.diem.patient_health to javafx.fxml;
    opens unisa.diem.converter to javafx.base;
    exports unisa.diem.patient_health;
}