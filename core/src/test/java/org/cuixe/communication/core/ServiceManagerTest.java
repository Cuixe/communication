package org.cuixe.communication.core;

import org.cuixe.communication.core.services.ScheduledService;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ServiceManagerTest {

    ServiceManager serviceManager = new ServiceManager();

    @Test
    public void executeService() throws Exception {
        FooService service = new FooService();
        serviceManager.registerService(service);
        serviceManager.runService("FOO");
        TimeUnit.MILLISECONDS.sleep(300);
        Assert.assertTrue(service.executions == 1);
    }

    @Test
    public void executeScheduledService() throws Exception {
        FooService service = new FooService();
        serviceManager.registerScheduledService(service);
        serviceManager.runScheduledService("FOO");
        TimeUnit.SECONDS.sleep(1);
        Assert.assertTrue(service.executions > 8 && service.executions < 11);
        System.out.println("Executions: " + service.executions);
    }

    @Test
    public void executeAll() throws Exception {
        FooService service1 = new FooService();
        FooService service2 = new FooService();
        serviceManager.registerService(service1);
        serviceManager.registerScheduledService(service2);
        serviceManager.runAllServices();
        TimeUnit.SECONDS.sleep(1);
        Assert.assertTrue(service1.executions == 1);
        Assert.assertTrue(service2.executions > 8 && service2.executions < 11);
        System.out.println("Executions: " + service2.executions);
    }


    class FooService implements ScheduledService {
        int executions = 0;

        @Override
        public String getServiceName() {
            return "FOO";
        }

        @Override
        public void execute() {
            executions++;
        }

        @Override
        public long getPeriodTime() {
            return 100;
        }

        @Override
        public TimeUnit getPeriodTimeUnit() {
            return TimeUnit.MILLISECONDS;
        }
    }

}