package com.backoffice.moffice.index.controller;

import com.backoffice.moffice.category.dto.CategorySearchDTO;
import com.backoffice.moffice.category.model.Category;
import com.backoffice.moffice.category.service.CategoryService;
import com.backoffice.moffice.items.dto.ItemSearchDTO;
import com.backoffice.moffice.items.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class indexViewController {
    private final CategoryService categoryService;
    private final ItemService itemService;

    @GetMapping("/")
    public String indexList(Model model, CategorySearchDTO
                            categorySearchDTO,
                            ItemSearchDTO itemSearchDTO){
        categorySearchDTO.setPageSize(5);
        categorySearchDTO.setUseYn("Y");
        CategorySearchDTO categoryList = categoryService.selectCategoryListWithPaging(categorySearchDTO);
        model.addAttribute("categoryList",categoryList);

        itemSearchDTO.setPageSize(5);
        itemSearchDTO.setUseYn("Y");
        ItemSearchDTO itemList = itemService.selectItemListWithPaging(itemSearchDTO);
        model.addAttribute("itemList", itemList);

        return "index";
    }
}
