package ru.nanikon.FlatCollection.commands;

import ru.nanikon.FlatCollection.arguments.AbstractArgument;
import ru.nanikon.FlatCollection.db.DBManager;

import java.io.Serializable;
import java.util.HashMap;

public class MapCommand implements Command, Serializable {
    private HashMap<String, Command> commands;
    private String login;
    private String password;

    private static final long serialVersionUID = 61;
    @Override
    public ServerAnswer<HashMap<String, Command>> execute(DBManager manager) {
        ServerAnswer<HashMap<String, Command>> result = new ServerAnswer<>();
        result.setAnswer(commands);
        result.setStatus(true);
        result.setMessage("Подключение успешно");
        return result;
    }

    public void setMap(HashMap<String, Command> map) {
        this.commands = map;
    }

    /**
     * @return - returns the help for the command. For help command
     */
    @Override
    public String getInformation() {
        return null;
    }

    /**
     * @return - returns the list of arguments required for the command to work, which must be obtained from the user
     */
    @Override
    public AbstractArgument<?>[] getArgs() {
        return new AbstractArgument[0];
    }

    @Override
    public String getName() {
        return "send map";
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
