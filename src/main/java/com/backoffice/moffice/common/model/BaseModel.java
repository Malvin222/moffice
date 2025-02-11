package com.backoffice.moffice.common.model;

import com.backoffice.moffice.common.dto.Pagination;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseModel extends Pagination {
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
