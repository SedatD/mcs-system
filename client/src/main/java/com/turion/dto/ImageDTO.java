package com.turion.dto;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class ImageDTO {

    @Nullable
    private Integer id;
    private byte[] data;
    private Integer requestId;
}
