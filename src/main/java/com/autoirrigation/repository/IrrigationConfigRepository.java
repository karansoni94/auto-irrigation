package com.autoirrigation.repository;

import com.autoirrigation.entity.IrrigationConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IrrigationConfigRepository extends JpaRepository<IrrigationConfig, Long> {

    List<IrrigationConfig> findByPlot_Id(Long plotId);

    IrrigationConfig findByPlot_IdAndId(Long plotId, Long configId);


}
