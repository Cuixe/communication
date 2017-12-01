package org.cuixe.communication.core.connection;

import org.cuixe.communication.core.Callback;
import org.cuixe.communication.core.Messaging;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

public class ObservableConnection extends Observable implements Connection {

    private Socket socket;
    private Thread connectionStatus;
    private Thread connectionThread;
    private SocketConnection connection;

    public ObservableConnection(Socket socket, Callback callback) {
        this.socket = socket;
        this.connection = new SocketConnection(socket, callback);
        this.connectionThread = new Thread(connection);
        this.connectionStatus = new Thread(this::checkStatus);
    }

    public void start() {
        connectionThread.start();
        connectionStatus.start();
    }

    @Override
    public void sendMessage(Messaging.Message message) {
        if(!isConnected()) {
            throw new RuntimeException("CONNECTION IS NOT AVILABLE");
        }
        try {
            connection.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isConnected() {
        return connection.isConnected();
    }

    @Override
    public void stop() {
        try {
            this.socket.close();
            this.connectionThread.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkStatus() {
        boolean status = false;
        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                if(status != connection.isConnected()) {
                    super.setChanged();
                    super.notifyObservers(this);
                    status = connection.isConnected();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
