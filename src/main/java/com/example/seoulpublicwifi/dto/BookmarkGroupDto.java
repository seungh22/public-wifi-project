package com.example.seoulpublicwifi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookmarkGroupDto {
    private int id;             // 번호
    private String name;        // 그룹명
    private int seq;            // 순서
    private Date regDttm;       // 등록일자
    private Date uptDttm;       // 수정일자
}
