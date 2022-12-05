package com.autoirrigation.controller;

import com.autoirrigation.scheduler.SensorConnector;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.autoirrigation.utils.Constants.HTTP_OK;

@Tag(name = "Sensor Notifications")
@RestController
@RequestMapping("/sensor-notifications")
public class SensorController {

    @Autowired
    private SensorConnector connector;

    @Operation(description = "Publish Sensor Notifications")
    @ApiResponses(@ApiResponse(responseCode = HTTP_OK))
    @GetMapping
    public ResponseEntity<List<String>> publishNotifications() {
        List<String> sensorLogs = connector.getNotifications().stream().collect(Collectors.toList());
        return new ResponseEntity<>(sensorLogs, new HttpHeaders(), HttpStatus.OK);
    }
}
