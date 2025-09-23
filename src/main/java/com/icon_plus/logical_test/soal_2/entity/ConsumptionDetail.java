package com.icon_plus.logical_test.soal_2.entity;

import lombok.Data;

@Data
public class ConsumptionDetail {
    private ConsumptionType consumptionType;
    private Long quantity;
    private Long totalValue;

    // Constructor
    public ConsumptionDetail() {
        this.quantity = 0L;
        this.totalValue = 0L;
    }
}