package org.cuixe.communication.client.socket.observers;

import org.cuixe.communication.core.Connection;
import org.cuixe.communication.core.ServiceManager;

import java.util.Observable;
import java.util.Observer;

public class ConnectionStatusObserver implements Observer {

    private ServiceManager serviceManager;

    public ConnectionStatusObserver(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @Override
    public void update(Observable o, Object arg) {
        Connection socket = (Connection) arg;
        if(socket.isConnected()) {
            serviceManager.runAllServices();
        } else {
            serviceManager.stopScheduledServices();
        }
    }
}
