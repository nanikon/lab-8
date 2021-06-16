package ru.nanikon.FlatCollection.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.nanikon.FlatCollection.App;
import ru.nanikon.FlatCollection.scenceControllers.MessageSceneController;
import ru.nanikon.FlatCollection.scenceControllers.helpScene;
import ru.nanikon.FlatCollection.scenceControllers.infoScene;

import java.io.IOException;
import java.net.URL;

public class HistoryStage {
    public static void showHistory(String history) {
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = App.class.getResource("/fxmls/helpScene.fxml");
            loader.setLocation(xmlUrl);
            stage.setScene(new Scene(loader.load()));

            helpScene controller = loader.getController();
            controller.okButton.setText(App.getRB().getString("continueOK"));
            controller.helpText.setText(history);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
