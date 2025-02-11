package com.backoffice.moffice.category.dto;

import com.backoffice.moffice.category.model.Category;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CategoryConverter {
    public Category convertDtoToModel(CategoryDTO categoryDTO){
        return Category.builder()
                .indexNo(categoryDTO.getIndexNo())
                .categoryName(categoryDTO.getCategoryName())
                .createIp(categoryDTO.getCreateIp())
                .updateIp(categoryDTO.getUpdateIp())
                .createDatetime(LocalDateTime.now().toString())
                .updateDatetime(LocalDateTime.now().toString())
                .useYn(categoryDTO.getUseYn())
                .build();
    }
}
