package org.cuixe.communication.core.services;

import java.util.concurrent.TimeUnit;

public interface ScheduledService extends Service {

    long getPeriodTime();

    TimeUnit getPeriodTimeUnit();


}
