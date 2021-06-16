package ru.nanikon.FlatCollection.scenceControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.nanikon.FlatCollection.App;
import ru.nanikon.FlatCollection.CommandController;
import ru.nanikon.FlatCollection.arguments.IntArg;
import ru.nanikon.FlatCollection.commands.Command;
import ru.nanikon.FlatCollection.commands.ServerAnswer;
import ru.nanikon.FlatCollection.data.Flat;
import ru.nanikon.FlatCollection.exceptions.NotPositiveNumberException;
import ru.nanikon.FlatCollection.stages.AddStage;
import ru.nanikon.FlatCollection.stages.MessageStage;

public class askerIdScene {
    public Label idLabel;
    public Button applyButton;
    public TextField idValue;
    private boolean isUpdate = false;

    @FXML
    public void initialize() {
        applyButton.setText(App.getRB().getString("send"));
        idLabel.setText(App.getRB().getString("get_id"));
    }

    public void setUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public void sendId(ActionEvent event) {
        Command command;
        try {
            if (!isUpdate) {
                command = CommandController.commandMap.get("remove_by_id");
            } else {
                command = CommandController.commandMap.get("update");
            }
            ((IntArg) command.getArgs()[0]).setValue(idValue.getText());
        } catch (NullPointerException e) {
            MessageStage.startErrorMessageStage(App.getRB().getString("id") + " " + App.getRB().getString("not_null"));
            return;
        } catch (NumberFormatException | NotPositiveNumberException e) {
            MessageStage.startErrorMessageStage(App.getRB().getString("id") + " " + App.getRB().getString("must_int_plus_number"));
            return;
        }
        if (!isUpdate) {
            ServerAnswer<String> answer = CommandController.newCommand(command, App.getLogin(), App.getPassword());
            MessageStage.startMessageStage(answer.getAnswer());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Flat result = null;
            for (Flat flat : App.getCollection()) {
                if ((flat.getId() == ((IntArg) command.getArgs()[0]).getValue()) & (flat.getOwner().equals(App.getLogin()))) {
                    result = flat;
                }
            }
            if (result == null) {
                MessageStage.startMessageStage("update_not_found");
            } else {
                AddStage.startUpdate(result);
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
