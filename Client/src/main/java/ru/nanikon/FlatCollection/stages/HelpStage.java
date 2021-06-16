package ru.nanikon.FlatCollection.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.nanikon.FlatCollection.App;
import ru.nanikon.FlatCollection.scenceControllers.helpScene;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class HelpStage {
    public static void showHelp(HashMap<String, String> answer) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = App.class.getResource("/fxmls/helpScene.fxml");
            loader.setLocation(xmlUrl);
            stage.setScene(new Scene(loader.load()));

            helpScene controller = loader.getController();
            controller.helpText.setText(App.getRB().getString("help_title"));
            for (String command : answer.keySet()) {
                controller.helpText.appendText("\n" + command + " - " + App.getRB().getString(answer.get(command)));
            }
            controller.okButton.setText(App.getRB().getString("continueOK"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
