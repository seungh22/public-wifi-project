package com.example.seoulpublicwifi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HistoryDto {
    private int id;         // 번호
    private double lnt;     // X좌표
    private double lat;     // Y좌표
    private Date srcDttm;   // 조회일자
}
