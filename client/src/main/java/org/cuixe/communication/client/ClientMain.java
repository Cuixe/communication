package org.cuixe.communication.client;

import org.cuixe.communication.client.processors.PingProcessor;
import org.cuixe.communication.client.services.PingService;
import org.cuixe.communication.client.observers.ConnectionStatusObserver;
import org.cuixe.communication.core.ServiceManager;
import org.cuixe.communication.core.connection.ObservableConnection;
import org.cuixe.communication.core.utils.ArgumetUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.Observer;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ClientMain {

    public static void main(String args[]) throws InterruptedException {
        Properties properties = ArgumetUtils.create(args);
        //String server = properties.getProperty("server");
        //int port = Integer.valueOf(properties.getProperty("port"));
        String server = "localhost";
        int port = 11500;
        int connectionAttempts = 10;
        int attempts = 1;

        ClientCallBack callback = new ClientCallBack();
        callback.registerProcessor(new PingProcessor());
        ServiceManager manager = new ServiceManager();
        Observer statusObserver = new ConnectionStatusObserver(manager);
        Socket socket = null;

        while(attempts < connectionAttempts) {
            try {
                socket = new Socket(server, port);
                break;
            } catch (Exception ex) {
                System.out.println("SERVER IS NOT AVAILABLE: attempt:" + attempts);
                TimeUnit.SECONDS.sleep(2);
            }
            attempts++;
        }
        if(socket != null) {
            ObservableConnection connection = new ObservableConnection(socket, callback);
            manager.registerScheduledService(new PingService(connection, "PING_SERVICE"));
            connection.addObserver(statusObserver);
            connection.start();
        }
    }
}
