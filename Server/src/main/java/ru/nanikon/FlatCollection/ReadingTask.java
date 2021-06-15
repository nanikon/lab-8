package ru.nanikon.FlatCollection;

import ru.nanikon.FlatCollection.commands.Command;
import ru.nanikon.FlatCollection.commands.MapCommand;
import ru.nanikon.FlatCollection.utils.Receiver;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

public class ReadingTask implements Runnable {
    private Receiver receiver;
    private ExecutorService processThreads;
    private ProcessingTask processingTask;
    private HashMap<String, Command> map;
    public ReadingTask(Receiver receiver, ExecutorService processThreads, ProcessingTask processingTask, HashMap<String, Command> map) {
        this.receiver = receiver;
        this.processingTask = processingTask;
        this.processThreads = processThreads;
        this.map = map;
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
            Command command = receiver.receiveCommand();
            Server.logger.info("Команда принята");
            processingTask.setCommand(command);
            Server.logger.info("Команда принята, начинает исполнятся");
            if (command instanceof MapCommand) {
                ((MapCommand) command).setMap(map);
            }
            processThreads.execute(processingTask);
        } catch (ClassCastException | ClassNotFoundException e) {
            Server.logger.error("Непонятная ошибка ", e);
        } catch (IOException e) {
            Server.logger.error("Клиент отвалился на чтении");
        }
    }
}
