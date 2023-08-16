package com.turion.dto;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class ClientDTO {

    @Nullable
    private Integer id;
    private String name;
    private Integer satelliteId;
}
