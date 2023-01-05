package com.purgenta.gameshop.authentication;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    String token;
    String role;
}
