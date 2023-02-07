package com.purgenta.gameshop.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class GameImageRequest {
    @NotEmpty
    private MultipartFile[] images;

    @NotNull
    private int gameId;
}
