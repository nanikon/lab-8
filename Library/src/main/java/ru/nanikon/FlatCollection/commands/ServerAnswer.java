package ru.nanikon.FlatCollection.commands;

import java.io.Serializable;

public class ServerAnswer<T> implements Serializable {
    private boolean status;
    private String errorMessage;
    private T answer;

    private static final long serialVersionUID = 70;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setMessage(String message) {
        this.errorMessage = message;
    }

    public T getAnswer() {
        return answer;
    }

    public void setAnswer(T answer) {
        this.answer = answer;
    }
}
