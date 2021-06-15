package ru.nanikon.FlatCollection.scenceControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import ru.nanikon.FlatCollection.App;
import ru.nanikon.FlatCollection.Language;

import java.io.IOException;

public class startScene {

    public Button exitButton;
    public Button logInButton;
    public ChoiceBox<Language> selectLocal;
    public Button registerButton;
    private boolean isInitialized = false;

    @FXML
    public void initialize() {
        initLabels();
    }

    private void initLabels() {
        exitButton.setText(App.getRB().getString("exit"));
        logInButton.setText(App.getRB().getString("logIn"));
        registerButton.setText(App.getRB().getString("register"));
        selectLocal.setValue(App.getCurrentLanguage());
        selectLocal.getItems().add(0, Language.RUSSIAN);
        selectLocal.getItems().add(1, Language.SLOVAK);
        selectLocal.getItems().add(2, Language.DANISH);
        selectLocal.getItems().add(3, Language.SPANISH);
        isInitialized = true;
    }

    public void goToLogIn(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(App.getSceneParentByUrl("/fxmls/logInScene.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToRegister(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(App.getSceneParentByUrl("/fxmls/registerScene.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeLocal(ActionEvent event) {
        Language value = selectLocal.getValue();
        if(value != null && isInitialized) {
            App.setLOCALE(value.getLocale(), value);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            App.setTitle(stage, App.getRB().getString("appTitle"));
            try {
                stage.setScene(new Scene(App.getSceneParentByUrl("/fxmls/startScene.fxml")));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
