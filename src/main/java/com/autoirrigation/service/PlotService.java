package com.autoirrigation.service;

import com.autoirrigation.repository.PlotRepository;
import com.autoirrigation.entity.Plot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlotService {
    @Autowired
    private PlotRepository repository;

    public List<Plot> getAll() {
        return repository.findAll();
    }

    public Plot save(Plot plot) {
        return repository.save(plot);
    }

    public Plot get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
