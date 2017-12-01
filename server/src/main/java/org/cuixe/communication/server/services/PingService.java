package org.cuixe.communication.server.services;

import org.cuixe.communication.core.services.AbstractService;
import org.cuixe.communication.core.services.ScheduledService;
import org.cuixe.communication.core.connection.SocketConnection;

import java.util.concurrent.TimeUnit;

import static org.cuixe.communication.core.Messaging.*;

public class PingService extends AbstractService implements ScheduledService {

    private SocketConnection connection;
    private static int attempt = 0;

    private Message.Builder builder = Message.newBuilder();
    private Header.Builder header = Header.newBuilder();
    private PingRequest.Builder ping = PingRequest.newBuilder();

    public PingService(SocketConnection connection, String name) {
        super(name);
        this.connection = connection;
        header.setType(Type.PING_REQUEST);
    }

    @Override
    public void execute() {
        ping.setAttempt(attempt);
        builder.setHeader(header);
        builder.setPingRequest(ping);
        //connection.sendMessage(ping.build());
    }

    @Override
    public long getPeriodTime() {
        return 60;
    }

    @Override
    public TimeUnit getPeriodTimeUnit() {
        return TimeUnit.SECONDS;
    }

}
