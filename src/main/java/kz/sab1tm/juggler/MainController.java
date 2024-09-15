package kz.sab1tm.juggler;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;

import java.util.Objects;

public class MainController {

    @FXML
    private Parent root;

    @FXML
    private ComboBox<String> leftActionSelectTheme;

    @FXML
    private void initialize() {
        leftActionSelectTheme.setValue("Dark"); // Установите тему по умолчанию
    }

    public void switchToDarkTheme() {
        root.getStylesheets().clear();
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css/dark.css")).toExternalForm());
    }

    public void switchToLightTheme() {
        root.getStylesheets().clear();
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css/light.css")).toExternalForm());
    }

    @FXML
    private void onThemeChange() {
        String selectedTheme = leftActionSelectTheme.getValue();
        if ("Dark".equals(selectedTheme)) {
            switchToDarkTheme();
        } else if ("Light".equals(selectedTheme)) {
            switchToLightTheme();
        }
    }
}