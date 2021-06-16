package ru.nanikon.FlatCollection;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nanikon.FlatCollection.commands.Command;
import ru.nanikon.FlatCollection.commands.MapCommand;
import ru.nanikon.FlatCollection.commands.ServerAnswer;
import ru.nanikon.FlatCollection.scenceControllers.plotScene;
import ru.nanikon.FlatCollection.utils.Connection;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class App extends Application {
    static String host;
    static int port;
    private Connection connection = new Connection();
    private static Locale LOCALE = Locale.getDefault();
    private static Language currentLanguage = Language.RUSSIAN;
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("ru.nanikon.FlatCollection.texts.Text", LOCALE);

    private static String login;
    private static String password;

    public static void main(String[] args) {
        /*try {
            host = args[0];
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! Порт должен быть числом");
            System.exit(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Ошибка! Вы не ввели все аргументы: хост и порт");
            System.exit(0);
        }*/
        host = "localhost";
        port = 8235;
        launch();
    }
    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            ServerAnswer<HashMap<String, Command>> answer = CommandController.newCommand(new MapCommand(), null, null);
            if (answer.isStatus()) {
                CommandController.commandMap = answer.getAnswer();
                primaryStage.setScene(new Scene(getSceneParentByUrl("/fxmls/startScene.fxml")));
                primaryStage.show();
            } else {
                System.out.println("У нас проблемы...");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Parent getSceneParentByUrl(String url) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = App.class.getResource(url);
        loader.setLocation(xmlUrl);
        return loader.load();
    }

    public static void setTitle(Stage stage, String title) {
        stage.setTitle(title);
    }

    public static Locale getLOCALE() {
        return LOCALE;
    }

    public static ResourceBundle getRB() {
        return resourceBundle;
    }

    public static Language getCurrentLanguage() {
        return currentLanguage;
    }

    public static void setLOCALE(Locale LOCALE, Language language) {
        App.LOCALE = LOCALE;
        App.currentLanguage = language;
        App.resourceBundle = ResourceBundle.getBundle("ru.nanikon.FlatCollection.texts.Text", LOCALE);
    }

    public static void setLogin(String login) {
        App.login = login;
    }

    public static void setPassword(String password) {
        App.password = password;
    }

    public static String getLogin() {return login;}
    public static String getPassword() {return password;}

    public static void getPlotScene(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = App.class.getResource("/fxmls/plotScene.fxml");
            loader.setLocation(xmlUrl);
            stage.setScene(new Scene(loader.load()));
            stage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);  //todo: think about such killing termination
            });
            plotScene controller = loader.getController();
            controller.canvas.widthProperty().bind(controller.pane.widthProperty());
            controller.canvas.heightProperty().bind(controller.pane.heightProperty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getTableScene(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = App.class.getResource("/fxmls/tableScene.fxml");
            loader.setLocation(xmlUrl);
            stage.setScene(new Scene(loader.load()));
            stage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
