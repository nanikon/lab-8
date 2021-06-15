package ru.nanikon.FlatCollection.scenceControllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class errorServerSceneController {
    public Label errorLabel;
    public Button reconnectButton;
    public Button exitButton;

    public void reconnect(ActionEvent actionEvent) {
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
