package org.cuixe.communication.core.services;

public abstract class AbstractService implements Service {

    private String name;

    public AbstractService(String name) {
        this.name = name;
    }

    @Override
    public String getServiceName() {
        return name;
    }
}
