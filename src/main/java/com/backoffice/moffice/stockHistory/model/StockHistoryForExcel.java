package com.backoffice.moffice.stockHistory.model;

import com.backoffice.moffice.common.excel.model.ExcelColumn;
import com.backoffice.moffice.common.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StockHistoryForExcel extends BaseModel {
    @ExcelColumn(headerName = "번호", order = 1)
    private Long indexNo;
    @ExcelColumn(headerName = "상품명", order = 2)
    private String itemName;
    @ExcelColumn(headerName = "카테고리명", order = 3)
    private String categoryName;
    @ExcelColumn(headerName = "입고수량", order = 4)
    private Long receivingCount;
    @ExcelColumn(headerName = "입고가격", order = 5)
    private Long receivingPrice;
    @ExcelColumn(headerName = "재고", order = 6)
    private Long stockCount;
    @ExcelColumn(headerName = "입고일", order = 7)
    private String createDatetime;
    @ExcelColumn(headerName = "입고자", order = 8)
    private String createIp;

}
