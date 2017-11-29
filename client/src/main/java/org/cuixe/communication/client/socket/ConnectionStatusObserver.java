package org.cuixe.communication.client.socket;

import org.cuixe.communication.core.ServiceManager;

import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ConnectionStatusObserver implements Observer {

    private ServiceManager serviceManager;

    public ConnectionStatusObserver(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @Override
    public void update(Observable o, Object arg) {
        Socket socket = (Socket) arg;
        if(socket.isConnected()) {
            serviceManager.runAllServices();
        }
    }
}
