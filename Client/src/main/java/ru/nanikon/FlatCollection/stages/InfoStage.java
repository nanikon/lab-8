package ru.nanikon.FlatCollection.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.nanikon.FlatCollection.App;
import ru.nanikon.FlatCollection.scenceControllers.infoScene;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class InfoStage {
    public static void startShow(List<String> answer) {
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = App.class.getResource("/fxmls/infoScene.fxml");
            loader.setLocation(xmlUrl);
            stage.setScene(new Scene(loader.load()));

            infoScene controller = loader.getController();
            controller.continueButton.setText(App.getRB().getString("continueOK"));
            controller.infoTitle.setText(App.getRB().getString("info_title"));
            controller.typeTextLabel.setText(App.getRB().getString("type_info"));
            controller.sizeTextLabel.setText(App.getRB().getString("size_info"));
            controller.typeLabel.setText(answer.get(0));
            controller.sizeLabel.setText(answer.get(1));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
