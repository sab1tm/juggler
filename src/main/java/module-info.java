module kz.sab1tm.juggler {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires okhttp3;
    requires java.net.http;
    requires static lombok;

    opens kz.sab1tm.juggler to javafx.fxml;
    exports kz.sab1tm.juggler;
    exports kz.sab1tm.juggler.controllers;
    opens kz.sab1tm.juggler.controllers to javafx.fxml;
}