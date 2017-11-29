package org.cuixe.communication.core;

public interface Callback {

    void receive(Messaging.Message message);
}
