package com.autoirrigation.scheduler;

import com.autoirrigation.service.IrrigationConfigService;
import com.autoirrigation.entity.IrrigationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class IrrigationScheduler {

    protected static final int MAX_RETRY_COUNT = 3;
    @Autowired
    private IrrigationConfigService configService;

    @Autowired
    private SensorConnector sensorConnector;

    private final ScheduledExecutorService executorService;

    private final Map<IrrigationConfig, List<ScheduledFuture>> scheduledFutures = new ConcurrentHashMap<>();

    public IrrigationScheduler() {
        executorService = Executors.newScheduledThreadPool(10);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setupScheduler() {
        List<IrrigationConfig> configList = configService.getAll();
        configList.stream().forEach(config -> addConfig(config));
    }

    public void addConfig(IrrigationConfig config) {
        long intervalInMillis = config.getIntervalUnit().toMillis(config.getIntervalValue());

        LocalTime currentTime = LocalTime.now().withNano(0);
        long initialDelayToStart, initialDelayToStop;
        if (currentTime.isBefore(config.getStartTime())) {
            initialDelayToStart = currentTime.until(config.getStartTime(), ChronoUnit.MILLIS);
            initialDelayToStop = currentTime.until(config.getEndTime(), ChronoUnit.MILLIS);
        } else {
            initialDelayToStart = config.getStartTime().until(currentTime, ChronoUnit.MILLIS);
            initialDelayToStop = config.getEndTime().until(currentTime, ChronoUnit.MILLIS);
        }
        ScheduledFuture startFuture = executorService.scheduleAtFixedRate(() -> sensorConnector.sendStartToSensor(config), initialDelayToStart, intervalInMillis, TimeUnit.MILLISECONDS);
        ScheduledFuture stopFuture = executorService.scheduleAtFixedRate(() -> sensorConnector.sendStopToSensor(config), initialDelayToStop, intervalInMillis, TimeUnit.MILLISECONDS);
        scheduledFutures.put(config, List.of(startFuture, stopFuture));
    }

    public void removeConfig(IrrigationConfig config) {
        if (scheduledFutures.containsKey(config)) {
            List<ScheduledFuture> futureList = scheduledFutures.get(config);
            futureList.stream().forEach(scheduledFuture -> scheduledFuture.cancel(false));
        }
    }

}
