package com.backoffice.moffice.items.model;

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
public class ItemForExcel extends BaseModel {
    @ExcelColumn(headerName = "상품번호", order = 1)
    private Long indexNo;
    @ExcelColumn(headerName = "상품명", order = 2)
    private String itemName;
    @ExcelColumn(headerName = "분류명",order = 3)
    private String categoryName;
    @ExcelColumn(headerName = "바코드번호", order = 4)
    private String barcodeNo;
    @ExcelColumn(headerName = "가격", order = 5)
    private Long price;
    @ExcelColumn(headerName = "생성일", order = 6)
    private String createDatetime;
    @ExcelColumn(headerName = "생성자Ip", order = 7)
    private String createIp;
    @ExcelColumn(headerName = "변경일", order = 8)
    private String updateDatetime;
    @ExcelColumn(headerName = "변경자Ip", order = 9)
    private String updateIp;
    @ExcelColumn(headerName = "사용여부", order = 10)
    private String useYn;
}
