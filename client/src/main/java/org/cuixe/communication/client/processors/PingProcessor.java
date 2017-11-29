package org.cuixe.communication.client.processors;

import org.cuixe.communication.core.Messaging;
import org.cuixe.communication.core.processors.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingProcessor implements Processor {

    private Logger logger = LoggerFactory.getLogger("ROOT");

    @Override
    public Messaging.Type getType() {
        return Messaging.Type.PING_RESPONSE;
    }

    @Override
    public void processMessage(Messaging.Message message) {
        Messaging.PingResponse response = message.getPingResponse();
        logger.debug(response.toString());
        System.out.println(response.toString());
    }
}
