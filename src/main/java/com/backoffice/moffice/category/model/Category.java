package com.backoffice.moffice.category.model;

import com.backoffice.moffice.common.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Category extends BaseModel {
    private Long indexNo;
    private String categoryName;
    private String createDatetime;
    private String createIp;
    private String updateDatetime;
    private String updateIp;
    private String useYn;
}
