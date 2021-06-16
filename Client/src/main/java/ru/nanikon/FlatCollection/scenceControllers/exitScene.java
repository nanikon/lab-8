package ru.nanikon.FlatCollection.scenceControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.nanikon.FlatCollection.App;
import ru.nanikon.FlatCollection.CommandController;
import ru.nanikon.FlatCollection.commands.Command;
import ru.nanikon.FlatCollection.commands.ServerAnswer;

public class exitScene {
    public Label exitLabel;
    public Button exitButton;
    public Button cancelButton;

    @FXML
    public void initialize() {
        initLabels();
    }

    private void initLabels() {
        cancelButton.setText(App.getRB().getString("cancel"));
        exitButton.setText(App.getRB().getString("exit"));
        exitLabel.setText(App.getRB().getString("doYouWantToExit"));
    }

    public void exitButtonClicked(ActionEvent event) {
        Command command = CommandController.commandMap.get("exit");
        ServerAnswer<String> answer = CommandController.newCommand(command, App.getLogin(), App.getPassword());
        System.exit(0);
    }

    public void cancelButtonClicked(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}
