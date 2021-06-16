package ru.nanikon.FlatCollection.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.nanikon.FlatCollection.App;
import ru.nanikon.FlatCollection.scenceControllers.MessageSceneController;

import java.io.IOException;
import java.net.URL;

public class AverageStage {
    public static void startShow(int average) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = App.class.getResource("/fxmls/messageScene.fxml");
            loader.setLocation(xmlUrl);
            stage.setScene(new Scene(loader.load()));

            MessageSceneController controller = loader.getController();
            controller.errorLabel.setText(App.getRB().getString("average") + ": " + average);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
