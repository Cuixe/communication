package org.cuixe.communication.client.services;

import org.cuixe.communication.core.connection.Connection;
import org.cuixe.communication.core.services.AbstractService;
import org.cuixe.communication.core.services.ScheduledService;
import org.cuixe.communication.core.utils.MessageFactory;

import java.util.concurrent.TimeUnit;

import static org.cuixe.communication.core.Messaging.*;

public class PingService extends AbstractService implements ScheduledService {

    private Connection connection;
    private static int attempt = 0;

    public PingService(Connection connection, String name) {
        super(name);
        this.connection = connection;
    }

    @Override
    public void execute() {
        Message message = MessageFactory.getPingRequest(attempt++);
        connection.sendMessage(message);
    }

    @Override
    public long getPeriodTime() {
        return 1;
    }

    @Override
    public TimeUnit getPeriodTimeUnit() {
        return TimeUnit.SECONDS;
    }

}
