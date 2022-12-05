package com.autoirrigation.scheduler;

import com.autoirrigation.entity.IrrigationConfig;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class SensorConnector {
    private final static Queue<String> NOTIFICATION_QUEUE = new ConcurrentLinkedQueue<>();

    public boolean sendStartToSensor(IrrigationConfig config) {
        String message = String.format("START sensor for Plot-Id: {%s} and Config-Id: {%s} -> Scheduled Start-Time: {%s} and Current-Time: {%s}",
                config.getPlot().getId(), config.getId(), config.getStartTime(), LocalTime.now().withNano(0));
        NOTIFICATION_QUEUE.add(message);
        return true;
    }

    public boolean sendStopToSensor(IrrigationConfig config) {
        String message = String.format("STOP sensor for Plot-Id: {%s} and Config-Id: {%s} -> Scheduled End-Time: {%s} and Current-Time: {%s}",
                config.getPlot().getId(), config.getId(), config.getEndTime(), LocalTime.now().withNano(0));
        NOTIFICATION_QUEUE.add(message);
        return true;
    }

    public Queue<String> getNotifications() {
        ConcurrentLinkedQueue<String> newQueue = new ConcurrentLinkedQueue<>(NOTIFICATION_QUEUE);
        NOTIFICATION_QUEUE.removeAll(newQueue);
        return newQueue;
    }

    public void sensorUnavailable(IrrigationConfig config) {
        System.out.printf("Sensor Unavailable with config-Id: " + config.getId() + " and plot-Id: " + config.getPlot().getId());
    }

    public void sensorRequestFailed(IrrigationConfig config) {
        System.out.printf("Sensor Request Failed with config-Id: " + config.getId() + " and plot-Id: " + config.getPlot().getId());
    }
}
