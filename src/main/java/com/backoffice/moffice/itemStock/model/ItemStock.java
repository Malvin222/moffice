package com.backoffice.moffice.itemStock.model;

import com.backoffice.moffice.common.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ItemStock extends BaseModel {
    private Long itemNo;
    private Long stockCount;

    private String itemName;
    private Long categoryNo;
    private String barcodeNo;
    private Long price;
    private String useYn;
    private String createDatetime;
    private String createIp;
    private String updateDatetime;
    private String updateIp;

    private String categoryName;
}
