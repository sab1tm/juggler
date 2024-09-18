package kz.sab1tm.juggler.configs;

import javafx.fxml.FXMLLoader;
import kz.sab1tm.juggler.controllers.MainController;
import kz.sab1tm.juggler.services.HttpRequestService;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

public class PicoFactory {

    private PicoFactory() {
    }

    public static void configurePicoFactory(FXMLLoader fxmlLoader) {
        MutablePicoContainer pico = new DefaultPicoContainer();

        // register components here
        pico.addComponent(HttpRequestService.class);
        pico.addComponent(MainController.class);

        fxmlLoader.setControllerFactory(pico::getComponent);
    }
}
