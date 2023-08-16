package com.turion.dto;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class RequestDTO {

    @Nullable
    private Integer id;
    private Integer clientId;
    private Integer satelliteId;
    private boolean isActive;
}
