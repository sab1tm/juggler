package kz.sab1tm.juggler.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import kz.sab1tm.juggler.models.enums.HttpMethodEnum;
import kz.sab1tm.juggler.services.HttpRequestService;

import java.util.Objects;

public class MainController {

    private final HttpRequestService httpRequestService;

    public MainController(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }

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

    private void switchToDarkTheme() {
        root.getStylesheets().clear();
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css/dark.css")).toExternalForm());
    }

    private void switchToLightTheme() {
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
    private void onSendRequest(ActionEvent actionEvent) {
        httpRequestService.sendRequest(
                HttpMethodEnum.valueOf(requestMethodType.getValue()),
                requestPath.getText(),
                null,
                null,
                null
        );
    }
}