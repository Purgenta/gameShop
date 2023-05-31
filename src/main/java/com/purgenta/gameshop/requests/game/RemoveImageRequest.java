package com.purgenta.gameshop.requests.game;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RemoveImageRequest {
    @NotNull
    Long imageId;
}
