package com.icon_plus.logical_test.soal_2.model;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class PesertaResponse {
    private Long id;
    private String name;
    private List<KonsumsiResponse> consumption;

    // Null-safe getter
    public List<KonsumsiResponse> getKonsumsiSafe() {
        return consumption != null ? consumption : Collections.emptyList();
    }

}

