package org.cuixe.communication.core;

import org.cuixe.communication.core.services.ScheduledService;
import org.cuixe.communication.core.services.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServiceManager {

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
    private Map<String, ScheduledService> nameScheduledServices = new HashMap<>();
    private Map<String, Service> nameServices = new HashMap<>();
    private static final int INITIAL_DELAY = 0;

    public void registerService(Service service) {
        nameServices.put(service.getServiceName(), service);
    }

    public void registerScheduledService(ScheduledService scheduledService) {
        nameScheduledServices.put(scheduledService.getServiceName(), scheduledService);
    }

    public void runService(String name) {
        Service service  = nameServices.get(name);
        executeService(service);
    }

    public void runScheduledService(String name) {
        ScheduledService service  = nameScheduledServices.get(name);
        executeScheduledService(service);
    }

    public void runAllServices() {
        nameScheduledServices.values().forEach(this::executeScheduledService);
        nameServices.values().forEach(this::executeService);
    }

    private void executeScheduledService(ScheduledService service) {
        executor.scheduleWithFixedDelay(service::execute, INITIAL_DELAY, service.getPeriodTime(), service.getPeriodTimeUnit());
    }

    private void executeService(Service service) {
        executor.schedule(service::execute, INITIAL_DELAY, TimeUnit.SECONDS);
    }
}
