package org.cuixe.communication.server.sockets;

import org.cuixe.communication.core.sockets.SocketConnection;
import org.cuixe.communication.server.ServerCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerSocket extends Thread {

    private java.net.ServerSocket socketServer;
    private ServerCallback serverCallback;
    private Map<Integer, SocketConnection> connections = new HashMap<>();
    private AtomicInteger clients = new AtomicInteger(0);

    public ServerSocket(int port, ServerCallback serverCallback ) throws IOException {
        socketServer = new java.net.ServerSocket(port);
        this.serverCallback = serverCallback;
    }

    public void run() {
        System.out.println("INICIANDO SERVIDOR");
        while (true) {
            try {
                System.out.println("ESPERANDO CONEXION");
                SocketConnection connection = new SocketConnection(socketServer.accept(), serverCallback);
                Thread client = new Thread(connection);
                client.start();
                System.out.println("CONEXION RECIBIDA");
                connections.put(clients.incrementAndGet(), connection);
            }catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

}
