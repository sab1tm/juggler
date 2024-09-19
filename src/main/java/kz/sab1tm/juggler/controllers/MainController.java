package kz.sab1tm.juggler.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import kz.sab1tm.juggler.models.HttpResponse;
import kz.sab1tm.juggler.models.enums.HttpMethodEnum;
import kz.sab1tm.juggler.services.HttpRequestService;
import kz.sab1tm.juggler.services.UserConfigService;
import lombok.RequiredArgsConstructor;

import static kz.sab1tm.juggler.utils.JsonUtil.toPrettyString;

@RequiredArgsConstructor
public class MainController {

    private final HttpRequestService httpRequestService;
    private final UserConfigService userConfigService;

    @FXML
    private Parent parent;

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
    private TextField requestPath;

    @FXML
    private Label responseStatus;

    @FXML
    private Label responseDuration;

    @FXML
    private Label responseSize;

    @FXML
    private TextArea responseBody;


    @FXML
    private void initialize() {
        themeSelect.setValue(userConfigService.getUserUITheme().name());
        userConfigService.applyUserUIConfig(parent);
    }

    @FXML
    private void onThemeChange() {
        userConfigService.setUserUITheme(parent, themeSelect.getValue());
    }

    @FXML
    private void onRequestMethodChange() {
    }

    @FXML
    private void onSendRequest(ActionEvent actionEvent) {
        HttpResponse httpResponse = httpRequestService.sendRequest(
                HttpMethodEnum.valueOf(requestMethodType.getValue()),
                requestPath.getText(),
                null,
                null,
                null
        );
        responseStatus.setText(httpResponse.getStatus());
        responseStatus.setTextFill(httpResponse.getStatusColor());

        responseDuration.setText(httpResponse.getDuration());
        responseSize.setText(httpResponse.getSize());
        responseBody.setText(toPrettyString(httpResponse.getBody()));
    }
}