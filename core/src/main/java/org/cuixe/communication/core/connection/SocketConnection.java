package org.cuixe.communication.core.connection;

import org.cuixe.communication.core.Callback;
import org.cuixe.communication.core.Messaging;
import org.cuixe.communication.core.utils.MessageFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketConnection implements Runnable {

    protected Socket socket;
    private Callback callback;
    private InputStream inputStream;
    private OutputStream outputStream;
    private boolean connected = false;

    SocketConnection(Socket socket, Callback callback) {
        this.socket = socket;
        this.callback = callback;
    }

    public void sendMessage(Messaging.Message message) throws IOException {
        message.writeDelimitedTo(outputStream);
        System.out.println("MENSAJE ENVIADO");
    }

    @Override
    public void run() {
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendTest();
        while (true) {
            try {
                inputStream = socket.getInputStream();
                Messaging.Message message = Messaging.Message.parseDelimitedFrom(inputStream);
                if(message != null) {
                    if (message.getHeader().getType() == Messaging.Type.TEST) {
                        System.out.println("CLIENT CONNECTED");
                    } else {
                        callback.receive(message);
                    }
                } else {
                    connected = false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                connected = false;
            }
        }
    }

    private void sendTest() {
        Messaging.Message test = MessageFactory.getTest(1);
        while (!connected) {
            try {
                test.writeDelimitedTo(outputStream);
                connected = true;
            } catch (IOException e) {
                System.out.println("NOT OPENED YET");
            }
        }
        connected = true;
    }

    public boolean isConnected() {
        return connected;
    }

}
