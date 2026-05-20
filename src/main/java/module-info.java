module lk.ijse.mental_health_therapy_center {
    requires javafx.controls;
    requires javafx.fxml;
  //  requires javafx.web;

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

//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
//    requires net.synedra.validatorfx;
//    requires org.kordamp.ikonli.javafx;
//    requires eu.hansolo.tilesfx;
    requires mysql.connector.j;

    opens lk.ijse.mental_health_therapy_center to javafx.fxml;
    exports lk.ijse.mental_health_therapy_center;
}

//        opens lk.ijse.gdse.controller to javafx.fxml;
//        opens lk.ijse.gdse.dto.tm to javafx.base;
//        opens lk.ijse.gdse.config to jakarta.persistence;
//        opens lk.ijse.gdse.entity to org.hibernate.orm.core;
//
//        exports lk.ijse.gdse;
//        exports lk.ijse.gdse.controller;




//        opens com.serenity.entity to org.hibernate.orm.core;
//        opens com.serenity.view.tdm to javafx.base;
//        opens com.serenity.config to jakarta.persistence;
//
//        opens com.serenity.controller to javafx.fxml;
//
//
//        exports com.serenity;
//        exports com.serenity.view.tdm;
