package com.purgenta.gameshop.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RemoveImageDto {
    @NotNull
    Long imageId;
}
