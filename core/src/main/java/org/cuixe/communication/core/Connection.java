package org.cuixe.communication.core;

public interface Connection {

    void sendMessage(Messaging.Message message);

    boolean isConnected();

}
