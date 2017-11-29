package org.cuixe.communication.core.processors;

import org.cuixe.communication.core.Messaging;

public interface Processor {

    Messaging.Type getType();

    void processMessage(Messaging.Message message);
}
