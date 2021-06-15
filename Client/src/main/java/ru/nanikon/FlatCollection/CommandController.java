package ru.nanikon.FlatCollection;

import ru.nanikon.FlatCollection.commands.Command;
import ru.nanikon.FlatCollection.commands.ServerAnswer;
import ru.nanikon.FlatCollection.data.Flat;
import ru.nanikon.FlatCollection.utils.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class CommandController {
    private static Connection connection = new Connection();
    public static HashMap<String, Command> commandMap;

    private static boolean tryToConnect(String host, int port) {
        try {
            connection.startConnection(host, port);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static ServerAnswer newCommand(Command command, String login, String password) {
        try {
            if (tryToConnect(App.host, App.port)) {
                command.setLogin(login);
                command.setPassword(password);
                connection.sendCommand(command);
                Thread.sleep(500);
                ServerAnswer result = connection.receive();
                connection.stopConnection();
                return result;
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            System.out.println("Не можем подключиться к серверу. Завершение работы");
            System.exit(0);
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static LinkedList<Flat> updateCollection() {
        Command command = commandMap.get("show");
        ServerAnswer<LinkedList<Flat>> answer = newCommand(command, App.getLogin(), App.getPassword());
        return answer.getAnswer();
    }
}
