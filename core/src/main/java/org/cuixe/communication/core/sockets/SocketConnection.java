package org.cuixe.communication.core.sockets;

import org.cuixe.communication.core.Callback;
import org.cuixe.communication.core.Connection;
import org.cuixe.communication.core.Messaging;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

public class SocketConnection extends Observable implements Connection, Runnable {

    protected Socket socket;
    private Callback callback;
    private InputStream inputStream;
    private OutputStream outputStream;

    private Thread connectionStatus;

    public SocketConnection(Socket socket, Callback callback) {
        this.socket = socket;
        this.callback = callback;
        connectionStatus = new Thread(this::checkStatus);
        connectionStatus.start();
    }

    @Override
    public void sendMessage(Messaging.Message message) {
        try {
            message.writeDelimitedTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        while (true) {
            try {
                Messaging.Message message = Messaging.Message.parseDelimitedFrom(inputStream);
                callback.receive(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    private void checkStatus() {
        boolean status = socket.isConnected();
        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                if(status != socket.isConnected()) {
                    super.notifyObservers(socket);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
