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
    private boolean connected = true;
    private Thread connectionStatus;
    private boolean initialized = false;

    public SocketConnection(Socket socket, Callback callback) {
        this.socket = socket;
        this.callback = callback;
        connectionStatus = new Thread(this::checkStatus);
    }

    @Override
    public void sendMessage(Messaging.Message message) {
        try {
            message.writeDelimitedTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("MENSAJE ENVIADO");
    }

    @Override
    public void run() {
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionStatus.start();
        while (true) {
            try {
                inputStream = socket.getInputStream();
                Messaging.Message message = Messaging.Message.parseDelimitedFrom(inputStream);
                if(message != null) {
                    connected = true;
                    callback.receive(message);
                } else {
                    connected = false;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    private void checkStatus() {
        boolean status = false;
        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                if(!initialized || status != connected) {
                    super.setChanged();
                    super.notifyObservers(this);
                    status = connected;
                    initialized = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        return connected;
    }

}
