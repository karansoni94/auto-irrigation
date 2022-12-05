package com.autoirrigation.controller;

import com.autoirrigation.entity.Plot;
import com.autoirrigation.service.PlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.autoirrigation.utils.Constants.*;

@Tag(name = "Plot")
@RestController
@RequestMapping("/plots")
public class PlotController {
    @Autowired
    private PlotService plotService;

    @Operation(description = "Get all Plots")
    @ApiResponses(@ApiResponse(responseCode = HTTP_OK, description = HTTP_OK_VALUE))
    @GetMapping
    public ResponseEntity getPlots() {
        List<Plot> plotList = plotService.getAll();
        return new ResponseEntity<>(plotList, new HttpHeaders(), HttpStatus.OK);

    }

    @Operation(description = "Get Plot for a specific Id")
    @ApiResponses({
            @ApiResponse(responseCode = HTTP_OK, description = HTTP_OK_VALUE),
            @ApiResponse(responseCode = HTTP_NOT_FOUND, description = HTTP_NOT_FOUND_VALUE),
    })
    @GetMapping("/{plotId}")
    public ResponseEntity getPlot(@PathVariable("plotId") Long plotId) {
        Plot plot = plotService.get(plotId);
        if (plot == null) {
            return new ResponseEntity<>("Plot not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(plot, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @Operation(description = "Create a new Plot")
    @ApiResponses(@ApiResponse(responseCode = HTTP_OK, description = HTTP_OK_VALUE))
    @PostMapping
    public ResponseEntity<Plot> createPlot(@RequestBody Plot plot) {
        Plot saved = plotService.save(plot);
        return new ResponseEntity<>(saved, new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(description = "Update existing Plot for a specific Id")
    @ApiResponses({
            @ApiResponse(responseCode = HTTP_OK, description = HTTP_OK_VALUE),
            @ApiResponse(responseCode = HTTP_NOT_FOUND, description = HTTP_NOT_FOUND_VALUE),
    })
    @PutMapping("/{plotId}")
    public ResponseEntity updatePlot(@PathVariable(value = "plotId") Long plotId, @RequestBody Plot plot) {
        Plot plotObj = plotService.get(plotId);
        if (plotObj != null) {
            if (plot.getPlotName() != null) {
                plotObj.setPlotName(plot.getPlotName());
            }

            if (plot.getAddress() != null) {
                plotObj.setAddress(plot.getAddress());
            }

            if (plot.getTotalArea() != null) {
                plotObj.setTotalArea(plot.getTotalArea());
            }

            Plot saved = plotService.save(plotObj);
            return new ResponseEntity<>(saved, new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Plot not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }
}