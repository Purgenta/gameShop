package com.purgenta.gameshop.dto;
import lombok.Data;

import java.util.List;

@Data
public class GameFilterDto {

    private String search = "";
    private Double fromPrice = null;
    private Double toPrice = null;
    private Integer page = 0;
    private List<Integer> searchCategories = null;
    private List<Integer> searchPublishers = null;
    private Integer size = 15;
    private Integer fromYear = null;
    private Integer toYear = null;
    private String[] sort = null;
}
