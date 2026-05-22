module lk.ijse.mental_health_therapy_center {

    requires javafx.controls;
    requires javafx.fxml;

    requires net.sf.jasperreports.core;
    requires jakarta.persistence;
    requires java.sql;
    requires javafx.graphics;
    requires jbcrypt;
    requires static lombok;
    requires org.hibernate.orm.core;

    requires java.naming;
    requires java.desktop;
    requires java.mail;
    requires mysql.connector.j;

    opens lk.ijse.mental_health_therapy_center to javafx.fxml;

    opens lk.ijse.mental_health_therapy_center.controller to javafx.fxml;

    exports lk.ijse.mental_health_therapy_center;
}