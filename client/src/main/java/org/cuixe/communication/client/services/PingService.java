package org.cuixe.communication.client.services;

import org.cuixe.communication.client.socket.ClientConnection;
import org.cuixe.communication.core.services.AbstractService;
import org.cuixe.communication.core.services.ScheduledService;

import java.util.concurrent.TimeUnit;

import static org.cuixe.communication.core.Messaging.*;

public class PingService extends AbstractService implements ScheduledService {

    private ClientConnection connection;
    private static int attempt = 0;

    private Message.Builder builder = Message.newBuilder();
    private Header.Builder header = Header.newBuilder();
    private PingRequest.Builder ping = PingRequest.newBuilder();

    public PingService(ClientConnection connection, String name) {
        super(name);
        this.connection = connection;
        header.setType(Type.PING_REQUEST);
    }

    @Override
    public void execute() {
        ping.setAttempt(++attempt);
        builder.setHeader(header);
        builder.setPingRequest(ping);
        connection.sendPingRequest(ping.build());
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
