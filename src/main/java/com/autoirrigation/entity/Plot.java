package com.autoirrigation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "plot")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Plot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NonNull
    @Column(name = "plot_name", nullable = false)
    private String plotName;

    @NonNull
    @Column(name = "total_area", nullable = false)
    @Schema(name = "totalArea", description = "Total area of a plot in square meters")
    private Integer totalArea;

    @NonNull
    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "plot")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<IrrigationConfig> irrigationConfigs;
}
