module unisa.diem.patient_health {
    requires javafx.controls;
    requires javafx.fxml;


    opens unisa.diem.patient_health to javafx.fxml;
    exports unisa.diem.patient_health;
}