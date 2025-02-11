package com.backoffice.moffice.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseDTO extends Pagination{
    private Long indexNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-DD HH:mm:ss", timezone = "Asia/Seoul")
    private String createDatetime;
    private String createStartDate;
    private String createEndDate;
    private String createIp;
    private String createIdCode;
    private String createIdName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-DD HH:mm:ss", timezone = "Asia/Seoul")
    private String updateDatetime;
    private String updateStartDate;
    private String updateEndDate;
    private String updateIp;
    private String updateIdCode;
    private String updateIdName;

    private Integer rowCount;
    private Integer rowNum;
    private Map<String,String> orderByMap;

    private String useYn;
}
