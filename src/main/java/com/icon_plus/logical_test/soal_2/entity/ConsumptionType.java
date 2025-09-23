package com.icon_plus.logical_test.soal_2.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import lombok.Data;

@Data
public class ConsumptionType {
    private String id;
    private String createdAt;
    private String name;
    private Integer maxPrice;

    // Getter untuk maxPrice dengan default value
    public Integer getMaxPrice() {
        return maxPrice != null ? maxPrice : 0;
    }
}