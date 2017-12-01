package org.cuixe.communication.server;

import org.cuixe.communication.core.processors.Processor;
import org.cuixe.communication.core.utils.ArgumetUtils;
import org.cuixe.communication.server.processors.PingProcessor;
import org.cuixe.communication.server.sockets.ConnectionServer;

import java.io.IOException;
import java.util.Properties;

public class ServerMain {

    public static void main(String args[]) throws IOException {
        Properties properties = ArgumetUtils.create(args);
        //int port = Integer.valueOf(properties.getProperty("port"));
        int port = 11500;

        Processor pingProcessor = new PingProcessor();
        ServerCallback callback = new ServerCallback();
        callback.registerProcessor(pingProcessor);
        ConnectionServer connectionServer = new ConnectionServer(port, callback);
        connectionServer.start();

    }
}
