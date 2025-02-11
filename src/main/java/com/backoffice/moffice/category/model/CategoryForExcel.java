package com.backoffice.moffice.category.model;

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
public class CategoryForExcel extends BaseModel {
    @ExcelColumn(headerName = "카테고리번호", order = 1)
    private Long indexNo;
    @ExcelColumn(headerName = "카테고리명", order = 2)
    private String categoryName;
    @ExcelColumn(headerName = "생성일", order = 3)
    private String createDatetime;
    @ExcelColumn(headerName = "생성자 IP", order = 4)
    private String createIp;
    @ExcelColumn(headerName = "수정일", order = 5)
    private String updateDatetime;
    @ExcelColumn(headerName = "수정자 IP", order = 6)
    private String updateIp;
    @ExcelColumn(headerName = "사용여부", order = 7)
    private String useYn;
}
