package ru.nanikon.FlatCollection;

import ru.nanikon.FlatCollection.utils.Connection;

import java.util.HashMap;

public class Client {
    private final Connection connection;
    public static String PS1 = "$";
    public static String PS2 = ">";
    //HashMap<String, Command> commands;
    private final int port;
    private final String addr;
    private String login;
    private String password;
    private boolean run;

    public Client(String addr, int port) {
        this.connection = new Connection();
        this.port = port;
        this.addr = addr;
    }
}
