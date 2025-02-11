package com.backoffice.moffice.category.dto;

import com.backoffice.moffice.category.model.Category;
import com.backoffice.moffice.common.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class CategorySearchDTO extends BaseDTO {
    private String categoryName;
    private List<Category> list;
}
