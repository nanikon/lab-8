package ru.nanikon.FlatCollection.scenceControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.nanikon.FlatCollection.App;
import ru.nanikon.FlatCollection.CommandController;
import ru.nanikon.FlatCollection.arguments.TransportArg;
import ru.nanikon.FlatCollection.commands.Command;
import ru.nanikon.FlatCollection.commands.ServerAnswer;
import ru.nanikon.FlatCollection.data.Transport;
import ru.nanikon.FlatCollection.stages.MessageStage;

public class askerTransportScene {
    public Label transportLabel;
    public ChoiceBox<Transport> transportSelect;
    public Button applyButton;

    @FXML
    public void initialize() {
        initLabels();
    }

    private void initLabels() {
        transportSelect.setValue(Transport.FEW);
        transportSelect.getItems().add(0, Transport.FEW);
        transportSelect.getItems().add(1, Transport.NONE);
        transportSelect.getItems().add(2, Transport.LITTLE);
        transportSelect.getItems().add(3, Transport.NORMAL);
        applyButton.setText(App.getRB().getString("send"));
        transportLabel.setText(App.getRB().getString("get_transport"));
    }

    public void deleteTransport(ActionEvent event) {
        Command command = CommandController.commandMap.get("remove_any_by_transport");
        ((TransportArg) command.getArgs()[0]).setValue(transportSelect.getValue().name());
        ServerAnswer<String> answer = CommandController.newCommand(command, App.getLogin(), App.getPassword());
        MessageStage.startMessageStage(answer.getAnswer());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
