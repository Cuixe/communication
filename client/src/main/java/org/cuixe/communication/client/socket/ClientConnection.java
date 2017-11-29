package org.cuixe.communication.client.socket;

import org.cuixe.communication.core.Callback;
import org.cuixe.communication.core.Messaging;
import org.cuixe.communication.core.sockets.SocketConnection;

import java.net.Socket;

public class ClientConnection extends SocketConnection {

    private Messaging.Message.Builder message = Messaging.Message.newBuilder();
    private Messaging.Header.Builder header = Messaging.Header.newBuilder();

    public ClientConnection(Socket socket, Callback callback) {
        super(socket, callback);
    }

    public void sendPingRequest(Messaging.PingRequest pingRequest) {
        header.clear();
        header.setSendingTime(System.currentTimeMillis());
        header.setType(Messaging.Type.PING_REQUEST);

        message.setPingRequest(pingRequest);
        message.setHeader(header);
        sendMessage(message.build());
    }

}
