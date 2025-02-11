package com.backoffice.moffice.items.model;

import com.backoffice.moffice.common.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Item extends BaseModel {
    private Long categoryNo;
    private String itemName;
    private String barcodeNo;
    private Long price;
    private String categoryName;
    private String createDatetime;
    private String createIp;
    private String updateDatetime;
    private String updateIp;
    private String useYn;
}
