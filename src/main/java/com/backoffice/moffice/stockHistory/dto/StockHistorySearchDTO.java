package com.backoffice.moffice.stockHistory.dto;

import com.backoffice.moffice.common.dto.BaseDTO;
import com.backoffice.moffice.itemStock.model.ItemStock;
import com.backoffice.moffice.stockHistory.model.StockHistory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class StockHistorySearchDTO extends BaseDTO {
    private Long itemNo;
    private Long receivingCount;
    private Long receivingPrice;
    private Long stockCount;
    private String itemName;
    private String categoryName;
    private String createDatetime;
    private String createIp;
    private String barcodeNo;
    private List<StockHistory> list;
}
