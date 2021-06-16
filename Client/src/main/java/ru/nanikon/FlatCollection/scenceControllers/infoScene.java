package ru.nanikon.FlatCollection.scenceControllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class infoScene {
    public Label infoTitle;
    public Label typeTextLabel;
    public Label typeLabel;
    public Label sizeTextLabel;
    public Label sizeLabel;
    public Button continueButton;

    public void continueWork(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
