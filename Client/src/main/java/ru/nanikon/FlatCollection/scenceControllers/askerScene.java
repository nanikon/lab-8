package ru.nanikon.FlatCollection.scenceControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.nanikon.FlatCollection.App;
import ru.nanikon.FlatCollection.CommandController;
import ru.nanikon.FlatCollection.Language;
import ru.nanikon.FlatCollection.arguments.FlatArg;
import ru.nanikon.FlatCollection.commands.Command;
import ru.nanikon.FlatCollection.commands.ServerAnswer;
import ru.nanikon.FlatCollection.data.FlatBuilder;
import ru.nanikon.FlatCollection.data.Transport;
import ru.nanikon.FlatCollection.data.View;
import ru.nanikon.FlatCollection.exceptions.BooleanInputException;
import ru.nanikon.FlatCollection.exceptions.NotPositiveNumberException;
import ru.nanikon.FlatCollection.stages.MessageStage;

public class askerScene {
    public Label titleLabel;
    public Label nameLabel;
    public TextField nameField;
    public Label xLabel;
    public TextField xField;
    public Label yLabel;
    public TextField yField;
    public Label areaLabel;
    public TextField areaField;
    public Label numberRoomsLabel;
    public TextField numberRoomsField;
    public CheckBox centralHeatingBox;
    public Label viewLabel;
    public ChoiceBox<View> viewSelect;
    public Label transportLabel;
    public ChoiceBox<Transport> transportSelect;
    public Label houseNameLabel;
    public Label yearLabel;
    public TextField yearField;
    public Label numberFloorsLabel;
    public TextField numberFloorsField;
    public Button applyButton;
    public TextField houseNameField;

    @FXML
    public void initialize() {
        initLabels();

    }

    private void initLabels() {
        applyButton.setText(App.getRB().getString("send"));
        titleLabel.setText(App.getRB().getString("add_title"));
        nameLabel.setText(App.getRB().getString("name"));
        xLabel.setText(App.getRB().getString("x"));
        yLabel.setText(App.getRB().getString("y"));
        areaLabel.setText(App.getRB().getString("area"));
        numberRoomsLabel.setText(App.getRB().getString("numberOfRooms"));
        centralHeatingBox.setText(App.getRB().getString("centralHeating"));
        viewLabel.setText(App.getRB().getString("view"));
        viewSelect.setValue(View.YARD);
        viewSelect.getItems().add(0, View.YARD);
        viewSelect.getItems().add(1, View.PARK);
        viewSelect.getItems().add(2, View.BAD);
        viewSelect.getItems().add(3, View.TERRIBLE);
        transportLabel.setText(App.getRB().getString("transport"));
        transportSelect.setValue(Transport.FEW);
        transportSelect.getItems().add(0, Transport.FEW);
        transportSelect.getItems().add(1, Transport.NONE);
        transportSelect.getItems().add(2, Transport.LITTLE);
        transportSelect.getItems().add(3, Transport.NORMAL);
        houseNameLabel.setText(App.getRB().getString("houseName"));
        yearLabel.setText(App.getRB().getString("year"));
        numberFloorsLabel.setText(App.getRB().getString("numberOfFloors"));
    }

    public void getElement(ActionEvent event) {
        Command command = CommandController.commandMap.get("add");
        FlatBuilder builder = ((FlatArg) command.getArgs()[0]).getBuilder();
        builder.reset();
        try {
            builder.setName(nameField.getText());
        } catch (NullPointerException e) {
            MessageStage.startErrorMessageStage(App.getRB().getString("name") + " " + App.getRB().getString("not_null"));
            return;
        }
        try {
            builder.setX(xField.getText());
        } catch (NullPointerException e) {
            MessageStage.startErrorMessageStage(App.getRB().getString("x") + " " + App.getRB().getString("not_null"));
            return;
        } catch (NumberFormatException e) {
            MessageStage.startErrorMessageStage(App.getRB().getString("x") + " " + App.getRB().getString("must_double_number"));
            return;
        }
        try {
            builder.setY(xField.getText());
        } catch (NullPointerException e) {
            MessageStage.startErrorMessageStage(App.getRB().getString("y") + " " + App.getRB().getString("not_null"));
            return;
        } catch (NumberFormatException e) {
            MessageStage.startErrorMessageStage(App.getRB().getString("y") + " " + App.getRB().getString("must_double_number"));
            return;
        }
        try {
            builder.setArea(areaField.getText());
        } catch (NullPointerException e) {
            MessageStage.startErrorMessageStage(App.getRB().getString("area") + " " + App.getRB().getString("not_null"));
            return;
        } catch (NotPositiveNumberException | NumberFormatException e) {
            MessageStage.startErrorMessageStage(App.getRB().getString("area") + " " + App.getRB().getString("must_int_plus_number"));
            return;
        }
        try {
            builder.setNumberOfRooms(numberRoomsField.getText());
        } catch (NullPointerException e) {
            MessageStage.startErrorMessageStage(App.getRB().getString("numberOfRooms") + " " + App.getRB().getString("not_null"));
            return;
        } catch (NotPositiveNumberException | NumberFormatException e) {
            MessageStage.startErrorMessageStage(App.getRB().getString("numberOfRooms") + " " + App.getRB().getString("must_int_plus_number"));
            return;
        }
        try {
            builder.setCentralHeating(centralHeatingBox.isSelected() ? "+" : "-");
        } catch (BooleanInputException ignored) {
        }
        builder.setView(viewSelect.getValue().name());
        builder.setTransport(transportSelect.getValue().name());
        builder.setHouseName(houseNameField.getText());
        try {
            builder.setYear(yearField.getText());
        } catch (NumberFormatException | NotPositiveNumberException e) {
            MessageStage.startErrorMessageStage(App.getRB().getString("year") + " " + App.getRB().getString("must_int_plus_number"));
            return;
        }
        try {
            builder.setNumberOfFloors(numberFloorsField.getText());
        } catch (NumberFormatException | NotPositiveNumberException e) {
            MessageStage.startErrorMessageStage(App.getRB().getString("numberOfFloors") + " " + App.getRB().getString("must_int_plus_number"));
            return;
        }
        ((FlatArg) command.getArgs()[0]).setValue("");

        ServerAnswer<String> answer = CommandController.newCommand(command, App.getLogin(), App.getPassword());
        MessageStage.startMessageStage(answer.getAnswer());
    }
}
