package org.cuixe.communication.client;

import org.cuixe.communication.client.processors.PingProcessor;
import org.cuixe.communication.client.services.PingService;
import org.cuixe.communication.client.socket.ClientConnection;
import org.cuixe.communication.client.socket.observers.ConnectionStatusObserver;
import org.cuixe.communication.core.ServiceManager;
import org.cuixe.communication.core.utils.ArgumetUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.Observer;
import java.util.Properties;

public class ClientMain {

    public static void main(String args[]) {
        Properties properties = ArgumetUtils.create(args);
        //String server = properties.getProperty("server");
        //int port = Integer.valueOf(properties.getProperty("port"));
        String server = "localhost";
        int port = 11500;

        try {
            Socket socket = new Socket(server, port);
            ClientCallBack callback = new ClientCallBack();
            callback.registerProcessor(new PingProcessor());
            ClientConnection connection = new ClientConnection(socket, callback);
            ServiceManager manager = new ServiceManager();
            manager.registerScheduledService(new PingService(connection, "PING_SERVICE"));
            Observer statusObserver = new ConnectionStatusObserver(manager);
            connection.addObserver(statusObserver);
            Thread thread = new Thread(connection);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
