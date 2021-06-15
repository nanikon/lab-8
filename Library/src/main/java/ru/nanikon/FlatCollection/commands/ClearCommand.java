package ru.nanikon.FlatCollection.commands;

import ru.nanikon.FlatCollection.arguments.AbstractArgument;
import ru.nanikon.FlatCollection.db.DBManager;

import java.io.Serializable;

/**
 * Clear collection
 */
public class ClearCommand implements Command, Serializable {
    private AbstractArgument<?>[] params = {};
    private String information = "'clear' - очистить коллекцию";
    private String login;
    private String password;

    private static final long serialVersionUID = 52;

    public ClearCommand() {
    }

    @Override
    public ServerAnswer<String> execute(DBManager manager) {
        ServerAnswer<String> result = new ServerAnswer<>(); //TODO: переделать
        if (!manager.chekUser(login, password)) {
            result.setStatus(false);
            result.setMessage("Ой, вы там в приложении что-то напортачили и мы то ли логин не найдем, то ли пароль для него не тот. Перезайдите нормально!");
        }
        result.setAnswer(manager.clear(login));
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
        return "clear";
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
