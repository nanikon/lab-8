package ru.nanikon.FlatCollection.stages;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.nanikon.FlatCollection.App;

import java.io.IOException;

public class ExitStage {
    public static void tryToExit(){
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(App.getSceneParentByUrl("/fxmls/exitScene.fxml")));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
