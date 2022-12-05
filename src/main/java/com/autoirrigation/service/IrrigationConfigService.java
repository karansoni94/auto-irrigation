package com.autoirrigation.service;

import com.autoirrigation.entity.IrrigationConfig;
import com.autoirrigation.repository.IrrigationConfigRepository;
import com.autoirrigation.scheduler.IrrigationScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IrrigationConfigService {
    @Autowired
    private IrrigationConfigRepository repository;

    @Autowired
    private IrrigationScheduler scheduler;

    public List<IrrigationConfig> getAll() {
        return repository.findAll();
    }

    public IrrigationConfig save(IrrigationConfig config) {
        IrrigationConfig savedConfig = repository.save(config);
        scheduler.addConfig(savedConfig);
        return savedConfig;
    }

    public IrrigationConfig get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(long id) {
        IrrigationConfig config = get(id);
        scheduler.removeConfig(config);
        repository.deleteById(id);
    }

    public List<IrrigationConfig> getByPlotId(Long plotId) {
        return repository.findByPlot_Id(plotId);
    }

    public IrrigationConfig getByPlotIdAndConfigId(Long plotId, Long configId) {
        return repository.findByPlot_IdAndId(plotId, configId);
    }
}
