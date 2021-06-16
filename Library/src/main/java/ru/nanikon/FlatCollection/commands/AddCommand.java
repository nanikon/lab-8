package ru.nanikon.FlatCollection.commands;

import ru.nanikon.FlatCollection.arguments.AbstractArgument;
import ru.nanikon.FlatCollection.arguments.FlatArg;
import ru.nanikon.FlatCollection.data.Flat;
import ru.nanikon.FlatCollection.data.FlatBuilder;
import ru.nanikon.FlatCollection.db.DBManager;
import ru.nanikon.FlatCollection.exceptions.NotPositiveNumberException;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Adds an object to the collection
 */
public class AddCommand implements Command, Serializable {
    private AbstractArgument<?>[] params = {new FlatArg()};
    private String information = "add_help";
    private HashMap<String, AbstractArgument<?>> args;

    private String login;
    private String password;

    private static final long serialVersionUID = 50;

    public AddCommand() {

    }

    public ServerAnswer<String> execute(DBManager manager) {
        ServerAnswer<String> result = new ServerAnswer<>();
        if (!manager.chekUser(login, password)) {
            result.setStatus(false);
            result.setMessage("Ой, вы там в приложении что-то напортачили и мы то ли логин не найдем, то ли пароль для него не тот. Перезайдите нормально!");
        }
        FlatBuilder builder = ((FlatArg) params[0]).getBuilder();
        result.setStatus(true); //TODO: исправить, ведь ошибки и из менеджера могут выползти
        result.setMessage(manager.addFlat(builder.getResult(), login));
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
        return "add";
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

    @Override
    public String getInformation() {
        return information;
    }


}

