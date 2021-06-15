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

public class registerScene {
    public Button back;
    public Label title;
    public Label loginLabel;
    public TextField loginField;
    public Label passwordLabel;
    public PasswordField passwordField;
    public Label doublePasswordLabel;
    public PasswordField doublePasswordField;
    public Button submit;

    @FXML
    public void initialize() {
        initLabels();
    }

    private void initLabels() {
        back.setText(App.getRB().getString("back"));
        title.setText(App.getRB().getString("register"));
        loginLabel.setText(App.getRB().getString("login"));
        passwordLabel.setText(App.getRB().getString("password"));
        doublePasswordLabel.setText(App.getRB().getString("password_again"));
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

    public void registerApp(ActionEvent event) {
        try {
            String password_one = passwordField.getText();
            String password_two = doublePasswordField.getText();
            String login = loginField.getText();
            if (password_one.equals(password_two)) {
                Command command = CommandController.commandMap.get("register");
                ServerAnswer<String> answer = CommandController.newCommand(command, login, password_one);
                if (answer.isStatus()) {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(App.getSceneParentByUrl("/fxmls/logInScene.fxml")));
                }
                MessageStage.startMessageStage(answer.getAnswer());
            } else {
                MessageStage.startMessageStage("wrong_double_password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
