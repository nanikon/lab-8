package ru.nanikon.FlatCollection.scenceControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.nanikon.FlatCollection.App;
import ru.nanikon.FlatCollection.CommandController;
import ru.nanikon.FlatCollection.commands.Command;
import ru.nanikon.FlatCollection.commands.ServerAnswer;
import ru.nanikon.FlatCollection.stages.MessageStage;

import java.io.IOException;

public class logInScene {
    public Button back;
    public Label title;
    public Label loginLabel;
    public TextField loginField;
    public Label passwordLabel;
    public PasswordField passwordField;
    public Button submit;

    @FXML
    public void initialize() {
        initLabels();
    }

    private void initLabels() {
        back.setText(App.getRB().getString("back"));
        title.setText(App.getRB().getString("logIn"));
        loginLabel.setText(App.getRB().getString("login"));
        passwordLabel.setText(App.getRB().getString("password"));
        submit.setText(App.getRB().getString("send"));
    }

    public void goToStart(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(App.getSceneParentByUrl("/fxmls/startScene.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToTable(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();
        Command command = CommandController.commandMap.get("log_in");
        ServerAnswer<String> answer = CommandController.newCommand(command, login, password);
        if (answer.isStatus()) {
            App.setLogin(login);
            App.setPassword(password);
            App.getTableScene((Stage) ((Node) event.getSource()).getScene().getWindow());
        } else {
            MessageStage.startMessageStage(answer.getErrorMessage());
        }
    }
}
