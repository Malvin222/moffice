package com.backoffice.moffice.category.mapper;

import com.backoffice.moffice.category.dto.CategorySearchDTO;
import com.backoffice.moffice.category.model.Category;
import com.backoffice.moffice.category.model.CategoryForExcel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    Category selectCategoryById(Long indexNo);
    Category selectCategoryByName(String categoryName);
    List<Category> selectCategoryList(CategorySearchDTO categorySearchDTO);
    List<Category> selectCategoryListByUseYn(CategorySearchDTO categorySearchDTO);
    List<Category> selectCategoryListWithPaging(CategorySearchDTO categorySearchDTO);
    List<CategoryForExcel> selectCategoryListForExcel(CategorySearchDTO categorySearchDTO);
    int insertCategory(Category category);
    int updateCategory(Category category);
    int deleteCategory(Long indexNo);
    int getTotalCount(CategorySearchDTO categorySearchDTO);
}
