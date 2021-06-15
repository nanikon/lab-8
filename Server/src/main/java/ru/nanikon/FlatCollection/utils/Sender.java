package ru.nanikon.FlatCollection.utils;

import ru.nanikon.FlatCollection.commands.ServerAnswer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Sender {
    private Socket socket;

    public Sender(Socket socket) throws IOException {
        this.socket = socket;
    }

    public void sendAnswer(ServerAnswer message) throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        os.writeObject(message);
        //os.close();
    }

    public Socket getSocket() {return socket;}
}
