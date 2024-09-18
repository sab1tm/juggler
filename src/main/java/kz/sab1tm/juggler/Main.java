package kz.sab1tm.juggler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static kz.sab1tm.juggler.configs.PicoFactory.configurePicoFactory;
import static kz.sab1tm.juggler.utils.ResourceUtil.getResource;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getResource("/views/main.fxml"));

        // set pico as a factory
        configurePicoFactory(fxmlLoader);

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Juggler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}