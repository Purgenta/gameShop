package com.purgenta.gameshop.requests;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RemoveImageRequest {
    @NotNull
    Long imageId;
}
