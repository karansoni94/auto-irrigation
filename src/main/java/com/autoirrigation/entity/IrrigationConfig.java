package com.autoirrigation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Entity(name = "irrigation_config")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class IrrigationConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NonNull
    @Column(name = "crop_name", nullable = false)
    private String cropName;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "plotId")
    @JsonIgnore
    private Plot plot;

    @NonNull
    @Column(name = "start_time", nullable = false)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(name = "startTime", implementation = String.class, example = "HH:mm:ss")
    private LocalTime startTime;

    @NonNull
    @Column(name = "end_time", nullable = false)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(name = "endTime", implementation = String.class, example = "HH:mm:ss")
    private LocalTime endTime;

    @NonNull
    @Column(name = "interval_value", nullable = false)
    private int intervalValue;

    @NonNull
    @Column(name = "interval_unit", nullable = false)
    private TimeUnit intervalUnit;

}
