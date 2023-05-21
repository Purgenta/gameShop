package com.purgenta.gameshop.response.authentication;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    String accessToken;
    String role;

}
