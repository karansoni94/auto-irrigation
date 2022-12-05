package com.autoirrigation.controller;

import com.autoirrigation.service.IrrigationConfigService;
import com.autoirrigation.entity.IrrigationConfig;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.autoirrigation.utils.Constants.*;

@Tag(name = "Irrigation-Config")
@RestController
@RequestMapping("/plots/{plotId}/irrigation-configs")
public class IrrigationConfigController {

    protected static final String PLOT_ID = "plotId";
    @Autowired
    private PlotService plotService;
    @Autowired
    private IrrigationConfigService irrigationConfigService;

    @Operation(description = "Get all Irrigation-Configs for any plot")
    @ApiResponses({
            @ApiResponse(responseCode = HTTP_OK, description = HTTP_OK_VALUE),
            @ApiResponse(responseCode = HTTP_NOT_FOUND, description = HTTP_NOT_FOUND_VALUE),
    })
    @GetMapping
    public ResponseEntity getConfigs(@PathVariable(PLOT_ID) Long plotId) {
        List<IrrigationConfig> configList = irrigationConfigService.getByPlotId(plotId);
        if (CollectionUtils.isEmpty(configList)) {
            return new ResponseEntity<>("Irrigation-Configs not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(configList, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @Operation(description = "Get Irrigation-Config by Plot-Id and Config-Id")
    @ApiResponses({
            @ApiResponse(responseCode = HTTP_OK, description = HTTP_OK_VALUE),
            @ApiResponse(responseCode = HTTP_NOT_FOUND, description = HTTP_NOT_FOUND_VALUE)
    })
    @GetMapping("/{configId}")
    public ResponseEntity getConfig(@PathVariable(PLOT_ID) Long plotId, @PathVariable("configId") Long configId) {
        IrrigationConfig config = irrigationConfigService.getByPlotIdAndConfigId(plotId, configId);
        if (config == null) {
            return new ResponseEntity<>("Irrigation-Config not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(config, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @Operation(description = "Create a new Irrigation-Config")
    @ApiResponses({
            @ApiResponse(responseCode = HTTP_OK, description = HTTP_OK_VALUE),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    })
    @PostMapping
    public ResponseEntity createConfig(@PathVariable(value = PLOT_ID) Long plotId,
                                       @RequestBody IrrigationConfig config) {
        Plot plot = plotService.get(plotId);
        if (plot == null) {
            return new ResponseEntity<>("Plot not found", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } else {
            config.setPlot(plot);
            IrrigationConfig saved = irrigationConfigService.save(config);
            return new ResponseEntity<>(saved, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @Operation(description = "Update exist Irrigation-Config for for a specific Id")
    @ApiResponses({
            @ApiResponse(responseCode = HTTP_OK, description = HTTP_OK_VALUE),
            @ApiResponse(responseCode = HTTP_NOT_FOUND, description = HTTP_NOT_FOUND_VALUE),
    })
    @PutMapping("/{configId}")
    public ResponseEntity updateConfig(@PathVariable(value = PLOT_ID) Long plotId,
                                       @PathVariable(value = "configId") Long configId,
                                       @RequestBody IrrigationConfig config) {
        IrrigationConfig updatedConfig = irrigationConfigService.get(configId);
        if (updatedConfig != null) {
            IrrigationConfig saved = irrigationConfigService.save(updatedConfig);
            return new ResponseEntity<>(saved, new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Irrigation-Config not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }
}