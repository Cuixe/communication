package org.cuixe.communication.server;

import org.cuixe.communication.core.Callback;
import org.cuixe.communication.core.Messaging;
import org.cuixe.communication.core.processors.Processor;

import java.util.HashMap;
import java.util.Map;

public class ServerCallback implements Callback {

    private Map<Messaging.Type, Processor> processors = new HashMap<>();

    @Override
    public void receive(Messaging.Message message) {
        Messaging.Type type = message.getHeader().getType();
        Processor processor = processors.get(type);
        processor.processMessage(message);
    }

    public void registerProcessor(Processor processor) {
        processors.put(processor.getType(), processor);
    }
}
