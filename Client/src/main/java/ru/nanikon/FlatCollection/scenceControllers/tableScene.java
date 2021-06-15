package ru.nanikon.FlatCollection.scenceControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.nanikon.FlatCollection.App;
import ru.nanikon.FlatCollection.CommandController;
import ru.nanikon.FlatCollection.data.Flat;
import ru.nanikon.FlatCollection.data.Transport;
import ru.nanikon.FlatCollection.data.View;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class tableScene {
    public TableView<Flat> showTable;
    public Button changeViewButton;
    public Button logOutButton;
    public Button exitButton;
    public Button addButton;
    public Button updateButton;
    public Button removeByIdButton;
    public Button removeAnyByTransportButton;
    public Button clearButton;
    public Button filterButton;
    public Button averageButton;
    public Button infoButton;
    public Button historyButton;
    public Button helpButton;
    public Button executeScriptButton;
    private final Timer timer = new Timer();
    private final TimerTask task = new TimerTask(){
        public void run()
        {
            LinkedList<Flat> collection = CommandController.updateCollection();
            ObservableList<Flat> list = FXCollections.observableList(collection);
            showTable.setItems(list);
        }

    };
    @FXML private TableColumn<Flat, Integer> id;
    @FXML private TableColumn<Flat, String> name;
    @FXML private TableColumn<Flat, Double> x;
    @FXML private TableColumn<Flat, Double> y;
    @FXML private TableColumn<Flat, ZonedDateTime> creationDate;
    @FXML private TableColumn<Flat, Long> area;
    @FXML private TableColumn<Flat, Integer> numberOfRooms;
    @FXML private TableColumn<Flat, Boolean> centralHeating;
    @FXML private TableColumn<Flat, View> view;
    @FXML private TableColumn<Flat, Transport> transport;
    @FXML private TableColumn<Flat, String> houseName;
    @FXML private TableColumn<Flat, Long> year;
    @FXML private TableColumn<Flat, Integer> numberOfFloors;
    @FXML private TableColumn<Flat, String> owner;

    @FXML
    public void initialize() {
        initLabels();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        x.setCellValueFactory(new PropertyValueFactory<>("x"));
        y.setCellValueFactory(new PropertyValueFactory<>("y"));
        creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        area.setCellValueFactory(new PropertyValueFactory<>("area"));
        numberOfRooms.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        centralHeating.setCellValueFactory(new PropertyValueFactory<>("centralHeating"));
        view.setCellValueFactory(new PropertyValueFactory<>("view"));
        transport.setCellValueFactory(new PropertyValueFactory<>("transport"));
        houseName.setCellValueFactory(new PropertyValueFactory<>("houseName"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        numberOfFloors.setCellValueFactory(new PropertyValueFactory<>("numberOfFloors"));
        owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        timerUpdateMethod();
    }

    private void initLabels() {
        changeViewButton.setText(App.getRB().getString("change_view"));
        logOutButton.setText(App.getRB().getString("logout"));
        exitButton.setText(App.getRB().getString("exit"));
        addButton.setText(App.getRB().getString("add"));
        updateButton.setText(App.getRB().getString("update"));
        removeByIdButton.setText(App.getRB().getString("remove_by_id"));
        removeAnyByTransportButton.setText(App.getRB().getString("remove_any_by_transport"));
        clearButton.setText(App.getRB().getString("clear"));
        filterButton.setText(App.getRB().getString("filter"));
        averageButton.setText(App.getRB().getString("average"));
        infoButton.setText(App.getRB().getString("info"));
        historyButton.setText(App.getRB().getString("history"));
        helpButton.setText(App.getRB().getString("help"));
        executeScriptButton.setText(App.getRB().getString("execute_script"));

        id.setText(App.getRB().getString("id"));
        name.setText(App.getRB().getString("name"));
        x.setText(App.getRB().getString("x"));
        y.setText(App.getRB().getString("y"));
        creationDate.setText(App.getRB().getString("creationDate"));
        area.setText(App.getRB().getString("area"));
        numberOfRooms.setText(App.getRB().getString("numberOfRooms"));
        centralHeating.setText(App.getRB().getString("centralHeating"));
        view.setText(App.getRB().getString("view"));
        transport.setText(App.getRB().getString("transport"));
        houseName.setText(App.getRB().getString("houseName"));
        year.setText(App.getRB().getString("year"));
        numberOfFloors.setText(App.getRB().getString("numberOfFloors"));
        owner.setText(App.getRB().getString("owner"));
    }

    public void stopTimer(){
        timer.cancel();
    }

    private void timerUpdateMethod(){
        timer.scheduleAtFixedRate(task, new Date(), 1000L);
    }
}
