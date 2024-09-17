package kz.sab1tm.juggler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kz.sab1tm.juggler.controllers.MainController;
import kz.sab1tm.juggler.services.HttpRequestService;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import java.io.IOException;

import static kz.sab1tm.juggler.utils.ResourceUtil.getResource;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getResource("/views/main.fxml"));

        // instances factory
        MutablePicoContainer pico = new DefaultPicoContainer();
        pico.addComponent(HttpRequestService.class);
        pico.addComponent(MainController.class);

        fxmlLoader.setControllerFactory(pico::getComponent);

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Juggler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}