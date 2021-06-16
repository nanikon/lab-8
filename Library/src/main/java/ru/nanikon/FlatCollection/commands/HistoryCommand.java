package ru.nanikon.FlatCollection.commands;

import ru.nanikon.FlatCollection.arguments.AbstractArgument;
import ru.nanikon.FlatCollection.db.DBManager;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * output the last 8 commands (without their arguments)
 */
public class HistoryCommand implements Command, Serializable {
    Deque<String> history = new ArrayDeque<>();
    private AbstractArgument<?>[] params = {};
    private String information = "history_help";
    private String login;
    private String password;

    private static final long serialVersionUID = 56;

    public HistoryCommand() {

    }

    /**
     * running the command
     */
    @Override
    public ServerAnswer<String> execute(DBManager manager) {
        ServerAnswer<String> result = new ServerAnswer<>();
        if (!manager.chekUser(login, password)) {
            result.setStatus(false);
            result.setMessage("Ой, вы там в приложении что-то напортачили и мы то ли логин не найдем, то ли пароль для него не тот. Перезайдите нормально!");
        }
        StringBuilder results = new StringBuilder();
        for (String command: history) {
            if (!(command == null)) {
                results.append(command).append("\n");
            }
        }
        result.setStatus(true);
        result.setAnswer(results.toString().trim());
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
        return "history";
    }

    /**
     * @return - returns the help for the command. For help command
     */
    @Override
    public String getInformation() {
        return information;
    }

    public void putCommand(String nameCommand) {
        if (history.size() == 8) {
            String lastCommand = history.remove();
        }
        history.add(nameCommand);
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
