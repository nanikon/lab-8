package ru.nanikon.FlatCollection.scenceControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import ru.nanikon.FlatCollection.App;
import ru.nanikon.FlatCollection.CommandController;
import ru.nanikon.FlatCollection.commands.Command;
import ru.nanikon.FlatCollection.commands.ServerAnswer;
import ru.nanikon.FlatCollection.data.Flat;
import ru.nanikon.FlatCollection.stages.*;

import java.io.IOException;
import java.util.*;

public class plotScene {
    public StackPane pane;
    public Label asWhoLabel;
    public Label currentLoginLabel;
    private GraphicsContext gc;
    public Canvas canvas;
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

    private static double xScale = 0.2;
    private static double yScale = 0.2;
    private int wBorder = 10;
    private int hBorder = 10;
    private double minX;
    private double maxX;
    private double maxY;
    private double minY;
    private int currentColor = 0;
    private long timerStartDate;
    private boolean fillOrStroke = true;

    private final Map<String, Color> colorMap = new HashMap<>();
    private final ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.AQUAMARINE, Color.CORAL, Color.GREENYELLOW, Color.PEACHPUFF, Color.YELLOW, Color.LIGHTSKYBLUE, Color.ORCHID));
    private LinkedList<Flat> currentCollection;
    private final Timer timer = new Timer();
    private final TimerTask task = new TimerTask() {
        public void run() {
            try {
                LinkedList<Flat> collection = CommandController.updateCollection();
                currentCollection = collection;
                App.setCollection(collection);
            } catch (IllegalStateException ignored) {

            }
            int secondsFromStart = (int) (System.currentTimeMillis() - timerStartDate) / 1000;
            if (secondsFromStart % 3 == 0) {
                fillOrStroke = !fillOrStroke;
            }
            redraw();
        }
    };

    private void timerUpdateMethod() {
        timerStartDate = System.currentTimeMillis();
        timer.scheduleAtFixedRate(task, new Date(), 1000L);
    }

    @FXML
    public void initialize() {
        initLabels();
        gc = canvas.getGraphicsContext2D();
        LinkedList<Flat> collection = CommandController.updateCollection();
        currentCollection = App.getCollection();

        redrawPlot();
        canvas.widthProperty().addListener(observable -> {
            redraw();
        });
        canvas.heightProperty().addListener(observable -> {
            redraw();
        });

        canvas.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            try {
                Flat element = findFlat(x, y);
                HelpStage.showFlat(element);
            } catch (NoSuchElementException ignored) {
            }
        });

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
        //filterButton.setText(App.getRB().getString("filter"));
        averageButton.setText(App.getRB().getString("average"));
        infoButton.setText(App.getRB().getString("info"));
        historyButton.setText(App.getRB().getString("history"));
        helpButton.setText(App.getRB().getString("help"));
        //executeScriptButton.setText(App.getRB().getString("execute_script"));
        asWhoLabel.setText(App.getRB().getString("as_who"));
        currentLoginLabel.setText(App.getLogin());
    }

    private double geXScale() {
        return minX /canvas.getWidth();
    }

    private double getYScale() {
        return maxY/canvas.getHeight();
    }

    private Flat findFlat(double x, double y) throws NoSuchElementException {
        for (Flat flat : currentCollection) {
            double[] size = getFlatWidthHeight(flat);
            if (convertX(flat.getX()) < x + size[0] / 2 && convertX(flat.getX()) > x - size[0] / 2 &&
                    convertY(flat.getY().intValue()) < y + size[1] / 2 && convertY(flat.getY().intValue()) > y - size[1] / 2) {
                return flat;
            }
        }
        throw new NoSuchElementException();
    }

    @FXML
    public double prefWidth(double height) {
        return canvas.getWidth();
    }

    @FXML
    public double prefHeight(double width) {
        return canvas.getHeight();
    }

    @FXML
    public boolean isResizable() {
        return true;
    }

    private void redraw() {
        updateScales();
        redrawPlot();
        for (Flat flat : currentCollection) {
            if (!colorMap.containsKey(flat.getOwner())) {
                colorMap.put(flat.getOwner(), getNewColor());
            }
            drawFlat(flat, colorMap.get(flat.getOwner()));
        }
    }

    private void updateScales() {
        minX = currentCollection.stream().mapToDouble(Flat::getX).min().orElse(0) - 20;
        maxX = currentCollection.stream().mapToDouble(Flat::getX).max().orElse(0) + 20;
        minY = currentCollection.stream().mapToDouble(Flat::getY).min().orElse(0) - 20;
        maxY = currentCollection.stream().mapToDouble(Flat::getY).max().orElse(0) + 20;
        xScale = (maxX - minX)/canvas.getWidth();
        yScale = (maxY - minY)/canvas.getHeight();
    }

    private void redrawPlot() {
        double h = canvas.getHeight();
        double w = canvas.getWidth();
        //background
        gc.clearRect(0, 0, w, h);
        gc.setFill(Color.ANTIQUEWHITE);
        gc.fillRect(0, 0, w, h);


        double bottom = Math.abs(Math.min(0, minY)) / yScale;
        double right = Math.max(0, maxX) / xScale;
        drawArrow(0, (int) (h - bottom), (int) w, (int) (h - bottom),
                minX,
                maxX, xScale
        );
        drawArrow((int) (w - right), (int) h, (int) (w - right), 0,
                minY,
                maxY, yScale
        );
    }

    private void drawFlat(Flat element, Color userColor) {
        int x = (int) convertX(element.getX());
        int y = (int) convertY(element.getY());
        double[] size = getFlatWidthHeight(element);
        double width = size[0];
        double height = size[1];

        if (fillOrStroke) {
            gc.setFill(Color.ORANGE);
        } else {
            gc.setFill(userColor);
        }
        gc.fillRect(x - width / 2, y - height / 2, width, height);

        if (fillOrStroke) {
            gc.setStroke(userColor);
        } else {
            gc.setStroke(Color.ORANGE);
        }
        gc.setLineWidth(5);
        gc.strokeRect(x - width / 2, y - height / 2, width, height);

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("null", 14));
        gc.fillText(App.getRB().getString("flat"), x - width / 3, y);
    }

    private void drawArrow(int x1, int y1, int x2, int y2, double startVal, double stopVal, double scale) {
        double ARR_SIZE = 4;

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        //
        Transform transform = Transform.translate(x1, y1);
        transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle), 0, 0));
        gc.setTransform(new Affine(transform));

        gc.setLineWidth(1);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(0, 0, len, 0);

        drawDivides(startVal, stopVal, scale, len);

        gc.fillPolygon(new double[]{len, len - ARR_SIZE, len - ARR_SIZE, len}, new double[]{0, -ARR_SIZE, ARR_SIZE, 0}, 4);

        transform = Transform.translate(0, 0);
        gc.setTransform(new Affine((transform)));
    }

    private void drawDivides(double startVal, double stopVal, double scale, int lengthInPix) {
        double widthOfDividersInPix = 3; // lengthInPix of division lines
        double textSpace = 3; //how far is text from line
        double intervalInPix = 100;//intervalInPix between divisions in pix
        int numberOfDivides = (int) Math.round(lengthInPix / intervalInPix);
        int intervalInCoords = (int) Math.round((stopVal - startVal) / numberOfDivides );
        gc.setFill(Color.BLACK);
        double textValue = startVal;
        for (int i = 0; i <= lengthInPix; i += intervalInPix) {
            gc.strokeLine(i, -(widthOfDividersInPix / 2), i, (widthOfDividersInPix / 2));
            gc.fillText(String.valueOf(Math.round(textValue)), i, -textSpace);
            textValue += intervalInCoords;
        }
    }

    private double convertX(double x) {
        return (canvas.getWidth() - wBorder - (maxX - x) / xScale);
    }

    private double convertY(double y) {
        return (double) (canvas.getHeight() - hBorder - (y - minY) / yScale);
    }

    private Color getNewColor() {
        Color color = colors.get(currentColor);
        currentColor++;
        return color;
    }

    private double[] getFlatWidthHeight(Flat element) {
        double widthScale = canvas.getWidth() / (400);
        int minimalSize = 5;
        double percentageDifference = 2;
        double size = percentageDifference * (minimalSize + element.getArea());
        double widthSize = size * widthScale;
        double heightSize = size * widthScale;
        return new double[]{widthSize, heightSize};
    }


    public void changeView(ActionEvent event) {
        timer.cancel();
        //CommandController.stopConnection();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        App.getTableScene((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    public void exitClick(ActionEvent actionEvent) {
        ExitStage.tryToExit();
    }

    public void logOut(ActionEvent event) {
        Command command = CommandController.commandMap.get("log_out");
        ServerAnswer<String> answer = CommandController.newCommand(command, App.getLogin(), App.getPassword());
        App.setPassword("");
        App.setLogin("");
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(App.getSceneParentByUrl("/fxmls/startScene.fxml")));
            MessageStage.startMessageStage(answer.getAnswer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearCollection(ActionEvent event) {
        Command command = CommandController.commandMap.get("clear");
        ServerAnswer<String> answer = CommandController.newCommand(command, App.getLogin(), App.getPassword());
        MessageStage.startMessageStage(answer.getAnswer());
    }

    public void averageRooms(ActionEvent event) {
        Command command = CommandController.commandMap.get("average_of_number_of_rooms");
        ServerAnswer<Integer> answer = CommandController.newCommand(command, App.getLogin(), App.getPassword());
        if (answer.isStatus()) {
            AverageStage.startShow(answer.getAnswer());
        } else {
            MessageStage.startMessageStage(answer.getErrorMessage());
        }
    }

    public void infoCollection(ActionEvent event) {
        Command command = CommandController.commandMap.get("info");
        ServerAnswer<List<String>> answer = CommandController.newCommand(command, App.getLogin(), App.getPassword());
        if (answer.isStatus()) {
            InfoStage.startShow(answer.getAnswer());
        } else {
            MessageStage.startMessageStage(answer.getErrorMessage());
        }
    }

    public void helpCommands(ActionEvent event) {
        Command command = CommandController.commandMap.get("help");
        ServerAnswer<HashMap<String, String>> answer = CommandController.newCommand(command, App.getLogin(), App.getPassword());
        HelpStage.showHelp(answer.getAnswer());
    }

    public void getHistory(ActionEvent actionEvent) {
        Command command = CommandController.commandMap.get("history");
        ServerAnswer<String> answer = CommandController.newCommand(command, App.getLogin(), App.getPassword());
        HistoryStage.showHistory(answer.getAnswer());
    }

    public void addElement(ActionEvent event) {
        AddStage.startAdd();
    }

    public void removeByIdStart(ActionEvent actionEvent) {
        IdStage.startRemove();
    }

    public void updateElement(ActionEvent event) {
        IdStage.startUpdate();
    }

    public void removeByTransportStart(ActionEvent actionEvent) {
        TransportStage.startDelete();
    }
}
