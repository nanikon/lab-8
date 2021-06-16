package ru.nanikon.FlatCollection.commands;

import ru.nanikon.FlatCollection.arguments.AbstractArgument;
import ru.nanikon.FlatCollection.db.DBManager;

import java.io.Serializable;
import java.util.HashMap;

/**
 * display help for available commands
 */
public class HelpCommand implements Command, Serializable {
    private HashMap<String, Command> commands;
    private AbstractArgument<?>[] params = {};
    private String information = "help_help";
    private HashMap<String, AbstractArgument<?>> args;
    private String login;
    private String password;

    private static final long serialVersionUID = 55;

    public HelpCommand(HashMap<String, Command> commands) {
        this.commands = commands;
    }

    /**
     * running the command
     */
    @Override
    public ServerAnswer<HashMap<String, String>> execute(DBManager manager) {
        HashMap<String, String> results = new HashMap<>();
        for (String nameCommand : commands.keySet()) {
            results.put(nameCommand, commands.get(nameCommand).getInformation());
        }
        ServerAnswer<HashMap<String, String>> result = new ServerAnswer<>();
        result.setStatus(true);
        result.setAnswer(results);
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
        return "help";
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
