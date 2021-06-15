package ru.nanikon.FlatCollection.commands;

import ru.nanikon.FlatCollection.arguments.AbstractArgument;
import ru.nanikon.FlatCollection.db.DBManager;

import java.io.Serializable;

/**
 * sort the collection in the natural order
 */
public class SortCommand implements Command, Serializable {
    private AbstractArgument<?>[] params = {};
    private String information = "'sort' - отсортировать коллекцию в естественном порядке";
    private String login;
    private String password;

    private static final long serialVersionUID = 67;

    public SortCommand() {

    }


    @Override
    public ServerAnswer<String> execute(DBManager manager) {
        ServerAnswer<String> result = new ServerAnswer<>(); //TODO убрать ибо сортировка на клиенте
        if (!manager.chekUser(login, password)) {
            result.setStatus(false);
            result.setMessage("Ой, вы там в приложении что-то напортачили и мы то ли логин не найдем, то ли пароль для него не тот. Перезайдите нормально!");
        }
        String results = manager.sortCollection();
        if (result.equals("")) {
            results = "Коллекция пуста, и сортировать нечего...";
        } else {
            results = "Коллекция успешно отсортированна:\n" + result;
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
        return "sort";
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

