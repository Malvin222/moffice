package com.backoffice.moffice.category.controller;

import com.backoffice.moffice.category.dto.CategorySearchDTO;
import com.backoffice.moffice.category.model.Category;
import com.backoffice.moffice.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoriesViewController {

    private final CategoryService categoryService;

    @GetMapping("category_list")
    public String categoriesList(Model model,
                                 CategorySearchDTO categorySearchDTO) {
        log.info("CategorySearchRequest: {}", categorySearchDTO);

        CategorySearchDTO result = categoryService.selectCategoryListWithPaging(categorySearchDTO);
        model.addAttribute("search", result);
        return "category/category_list";
    }

    @GetMapping("category_detail/{indexNo}")
    public String categoryDetail(Model model,
                                 @PathVariable Long indexNo) {
        Category category = categoryService.selectCategoryById(indexNo);

        log.info("category: {}", category);

        model.addAttribute("category", category);

        return "category/category_detail";
    }

    @GetMapping("category_save")
    public String registerCategory(Model model) {

        return "category/category_save";
    }
}
