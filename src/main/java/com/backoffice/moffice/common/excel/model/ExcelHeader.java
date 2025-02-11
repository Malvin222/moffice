package com.backoffice.moffice.common.excel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExcelHeader {
    private String fieldName;
    private String headerName;
    private int order;
}
