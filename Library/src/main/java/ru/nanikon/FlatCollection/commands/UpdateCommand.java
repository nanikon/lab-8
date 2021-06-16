package ru.nanikon.FlatCollection.commands;

import ru.nanikon.FlatCollection.arguments.AbstractArgument;
import ru.nanikon.FlatCollection.arguments.FlatArg;
import ru.nanikon.FlatCollection.arguments.IntArg;
import ru.nanikon.FlatCollection.data.Flat;
import ru.nanikon.FlatCollection.data.FlatBuilder;
import ru.nanikon.FlatCollection.db.DBManager;
import ru.nanikon.FlatCollection.exceptions.BooleanInputException;
import ru.nanikon.FlatCollection.exceptions.NotPositiveNumberException;

import java.io.Serializable;
import java.util.HashSet;

public class UpdateCommand implements Command, Serializable {
    private AbstractArgument<?>[] params = {new IntArg(), new FlatArg()};
    private String information = "update_help";

    private String login;
    private String password;

    private static final long serialVersionUID = 68;

    public UpdateCommand() {
        //((IdArg) params[0]).setCollection(collection);
    }

    @Override
    public ServerAnswer<String> execute(DBManager manager) {
        ServerAnswer<String> result = new ServerAnswer<>();
        if (!manager.chekUser(login, password)) {
            result.setStatus(false);
            result.setMessage("login_password_wrong");
        } else {
            int id = ((IntArg) params[0]).getValue();
            FlatBuilder oldBuilder = ((FlatArg) params[1]).getBuilder();
            result = manager.updateById(id, login, oldBuilder);
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
        return "update";
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
