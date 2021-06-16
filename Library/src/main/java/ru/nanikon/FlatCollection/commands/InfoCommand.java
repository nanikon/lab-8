package ru.nanikon.FlatCollection.commands;

import ru.nanikon.FlatCollection.arguments.AbstractArgument;
import ru.nanikon.FlatCollection.data.Flat;
import ru.nanikon.FlatCollection.db.DBManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * output information about the collection (type, initialization date, number of elements) to the standard output stream.)
 */
public class InfoCommand implements Command, Serializable {
    private AbstractArgument<?>[] params = {};
    private String information = "info_help";
    private String login;
    private String password;

    private static final long serialVersionUID = 57;

    public InfoCommand() {

    }

    @Override
    public ServerAnswer<List<String>> execute(DBManager manager) {
        ServerAnswer<List<String>> result = new ServerAnswer<>();
        if (!manager.chekUser(login, password)) {
            result.setStatus(false);
            result.setMessage("Ой, вы там в приложении что-то напортачили и мы то ли логин не найдем, то ли пароль для него не тот. Перезайдите нормально!");
        } else {
            result.setStatus(true);
            result.setAnswer(Arrays.asList(Flat.class.getName(), manager.getSize()));
        }
        return result;
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
        return "info";
    }

    /**
     * @return - returns the help for the command. For help command
     */
    @Override
    public String getInformation() {
        return information;
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
