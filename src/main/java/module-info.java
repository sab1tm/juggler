module kz.sab1tm.juggler {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires okhttp3;
    requires java.net.http;
    requires static lombok;
    requires picocontainer;
    requires com.fasterxml.jackson.databind;
    requires org.slf4j;

    opens kz.sab1tm.juggler to javafx.fxml;
    opens kz.sab1tm.juggler.services to javafx.fxml;
    opens kz.sab1tm.juggler.controllers to javafx.fxml;

    exports kz.sab1tm.juggler;
    exports kz.sab1tm.juggler.models;
    exports kz.sab1tm.juggler.models.enums;
    exports kz.sab1tm.juggler.services;
    exports kz.sab1tm.juggler.controllers;
}