package ru.nanikon.FlatCollection;

import ru.nanikon.FlatCollection.commands.ServerAnswer;
import ru.nanikon.FlatCollection.utils.Sender;

import java.io.IOException;
import java.util.HashMap;

public class SendingTask implements Runnable {
    private Sender sender;
    private ServerAnswer answer;


    public SendingTask(Sender sender) {
        this.sender = sender;
    }

    public void setSendString(ServerAnswer answer) {
        this.answer = answer;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            sender.sendAnswer(answer);
            Server.logger.info("Отправлен ответ команды");
        } catch (IOException e) {
            Server.logger.warn("Проблемы со связью", e);
        }
    }
}
