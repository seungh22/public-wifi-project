package com.example.seoulpublicwifi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookmarkDto {
    private int id;             // 번호
    private int gId;            // 북마크 그룹 번호
    private String mgrNo;       // 와이파이 관리 번호
    private Date regDttm;       // 등록일자
}
