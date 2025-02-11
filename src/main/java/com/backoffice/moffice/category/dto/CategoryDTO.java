package com.backoffice.moffice.category.dto;

import com.backoffice.moffice.common.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString
public class CategoryDTO extends BaseDTO {
    private String categoryName;
}
