package com.purgenta.gameshop.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Stats {
    private long gameCount;
    private long userCount;
    private long orderCount;
}
