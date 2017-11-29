package org.cuixe.communication.client;

import org.cuixe.communication.client.processors.PingProcessor;
import org.cuixe.communication.client.services.PingService;
import org.cuixe.communication.client.socket.ClientConnection;
import org.cuixe.communication.client.socket.ConnectionStatusObserver;
import org.cuixe.communication.core.ServiceManager;

import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

public class ClientMain {

    public static void main(String args[]) {
        Properties properties = create(args);
        String server = properties.getProperty("server");
        int port = Integer.valueOf(properties.getProperty("port"));

        try {
            Socket socket = new Socket(server, port);
            ClientCallBack callback = new ClientCallBack();
            callback.registerProcessor(new PingProcessor());
            ClientConnection connection = new ClientConnection(socket, callback);
            ServiceManager manager = new ServiceManager();
            manager.registerService(new PingService(connection, "PING_SERVICE"));
            new ConnectionStatusObserver(manager);
            Thread thread = new Thread(connection);
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Properties create(String [] args) {
        Properties properties = new Properties();
        for(int i = 0; i< args.length; i++) {
            String arg = args[i];
            String[] paths = arg.split("=");
            properties.setProperty(paths[0], paths[1]);
        }
        return properties;
    }
}
