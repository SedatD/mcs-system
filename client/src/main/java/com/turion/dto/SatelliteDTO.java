package com.turion.dto;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class SatelliteDTO {

    @Nullable
    private Integer id;
    private String name;
}
