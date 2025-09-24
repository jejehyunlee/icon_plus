package com.icon_plus.logical_test.soal_2.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class JenisKonsumsiResponse {
    private String id;                    // String, bukan Long
    private String createdAt;
    private String name;
    private BigDecimal maxPrice;

}
