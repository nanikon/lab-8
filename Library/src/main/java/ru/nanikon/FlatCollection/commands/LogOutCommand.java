package ru.nanikon.FlatCollection.commands;

import ru.nanikon.FlatCollection.arguments.AbstractArgument;
import ru.nanikon.FlatCollection.db.DBManager;

import java.io.Serializable;

public class LogOutCommand implements Command, Serializable {
    private AbstractArgument<?>[] params = {};
    private String information = "logout_help";
    private String login;
    private String password;

    private static final long serialVersionUID = 60;
    @Override
    public ServerAnswer<String> execute(DBManager manager) {
        ServerAnswer<String> result = new ServerAnswer<>();
        result.setStatus(true);
        result.setAnswer("log_out_message");
        return result;
    }

    /**
     * @return - returns the help for the command. For help command
     */
    @Override
    public String getInformation() {
        return information;
    }

    /**
     * @return - returns the list of arguments required for the command to work, which must be obtained from the user
     */
    @Override
    public AbstractArgument<?>[] getArgs() {
        return params;
    }

    @Override
    public String getName() {
        return "log_out";
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
