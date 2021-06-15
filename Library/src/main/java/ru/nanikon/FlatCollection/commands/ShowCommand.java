package ru.nanikon.FlatCollection.commands;

import ru.nanikon.FlatCollection.arguments.AbstractArgument;
import ru.nanikon.FlatCollection.data.Flat;
import ru.nanikon.FlatCollection.db.DBManager;
import ru.nanikon.FlatCollection.exceptions.BooleanInputException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * output all elements of the collection in a string representation to the standard output stream
 */

public class ShowCommand implements Command, Serializable {
    //private CollectionManager collection;
    private AbstractArgument<?>[] params = {};
    private String information = "'show' - вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    private HashMap<String, AbstractArgument<?>> args;
    private String login;
    private String password;

    private static final long serialVersionUID = 66;

    public ShowCommand() {
    }

    @Override
    public ServerAnswer<LinkedList<Flat>> execute(DBManager manager) {
        ServerAnswer<LinkedList<Flat>> result = new ServerAnswer<>();
        if (!manager.chekUser(login, password)) {
            result.setStatus(false);
            result.setMessage("Ой, вы там в приложении что-то напортачили и мы то ли логин не найдем, то ли пароль для него не тот. Перезайдите нормально!");
        }
        result.setStatus(true);
        try {
            manager.initialCollection();
        } catch (SQLException | BooleanInputException e) {
            e.printStackTrace();
        }
        result.setAnswer(manager.getCollection());
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
        return "show";
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
