package org.cuixe.communication.server.processors;

import org.cuixe.communication.core.Messaging;
import org.cuixe.communication.core.processors.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingProcessor implements Processor{

    private Logger logger = LoggerFactory.getLogger("ROOT");

    @Override
    public Messaging.Type getType() {
        return Messaging.Type.PING_REQUEST;
    }

    @Override
    public void processMessage(Messaging.Message message) {
        if (message != null) {
            logger.debug(message.toString());
            System.out.println(message.toString());
        }
    }
}
