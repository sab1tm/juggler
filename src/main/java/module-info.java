module kz.sab1tm.juggler {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens kz.sab1tm.juggler to javafx.fxml;
    exports kz.sab1tm.juggler;
}