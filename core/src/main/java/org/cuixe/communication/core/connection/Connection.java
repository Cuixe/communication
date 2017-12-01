package org.cuixe.communication.core.connection;

import org.cuixe.communication.core.Messaging;

public interface Connection {

    void sendMessage(Messaging.Message message);

    boolean isConnected();

    void start();

    void stop();

}
