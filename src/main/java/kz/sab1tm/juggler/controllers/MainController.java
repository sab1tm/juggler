package kz.sab1tm.juggler.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.util.Objects;

public class MainController {

    @FXML
    private Parent root;

    // left panel's components

    @FXML
    private ComboBox<String> themeSelect;

    @FXML
    private Button requestImport;

    @FXML
    private TreeView<String> requestsList;

    // right panel's components

    @FXML
    private ComboBox<String> requestMethodType;

    @FXML
    public TextField requestPath;


    @FXML
    private void initialize() {
        themeSelect.setValue("Dark");
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
        String selectedTheme = themeSelect.getValue();
        if ("Dark".equals(selectedTheme)) {
            switchToDarkTheme();
        } else if ("Light".equals(selectedTheme)) {
            switchToLightTheme();
        }
    }

    @FXML
    private void onRequestMethodChange() {
    }

    @FXML
    public void onSendRequest(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sending request");
        alert.setHeaderText(null);
        alert.setContentText(requestMethodType.getValue() + " " + requestPath.getText());
        alert.showAndWait();
    }
}