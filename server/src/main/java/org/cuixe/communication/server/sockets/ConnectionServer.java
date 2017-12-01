package org.cuixe.communication.server.sockets;

import org.cuixe.communication.core.connection.Connection;
import org.cuixe.communication.core.connection.ObservableConnection;
import org.cuixe.communication.server.ServerCallback;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionServer extends Thread {

    private ServerSocket socketServer;
    private ServerCallback serverCallback;
    private Map<Integer, Connection> connections = new HashMap<>();
    private AtomicInteger clients = new AtomicInteger(0);

    public ConnectionServer(int port, ServerCallback serverCallback ) throws IOException {
        socketServer = new ServerSocket(port);
        this.serverCallback = serverCallback;
    }

    public void run() {
        System.out.println("INICIANDO SERVIDOR");
        while (true) {
            try {
                System.out.println("ESPERANDO CONEXION");
                ObservableConnection connection = new ObservableConnection(socketServer.accept(), serverCallback);
                connection.start();
                System.out.println("CONEXION RECIBIDA");
                connections.put(clients.incrementAndGet(), connection);
            }catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

}
