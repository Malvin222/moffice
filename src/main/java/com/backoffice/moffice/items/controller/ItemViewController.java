package com.backoffice.moffice.items.controller;

import com.backoffice.moffice.category.dto.CategorySearchDTO;
import com.backoffice.moffice.category.model.Category;
import com.backoffice.moffice.category.service.CategoryService;
import com.backoffice.moffice.items.dto.ItemSearchDTO;
import com.backoffice.moffice.items.model.Item;
import com.backoffice.moffice.items.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemViewController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @GetMapping("item_list")
    public String itemList(Model model,
                           ItemSearchDTO itemSearchDTO) {

        ItemSearchDTO result = itemService.selectItemListWithPaging(itemSearchDTO);
        model.addAttribute("search", result);

        return "items/item_list";
    }

    @GetMapping("item_save")
    public String registerItem(Model model,
                               CategorySearchDTO categorySearchDTO) {

        List<Category> categoryList = categoryService.selectCategoryListByUseYn(categorySearchDTO);
        model.addAttribute("categoryList", categoryList);

        return "items/item_save";
    }
    @GetMapping("item_detail/{indexNo}")
    public String itemDetail(Model model,
                             @PathVariable Long indexNo,
                             CategorySearchDTO categorySearchDTO){
        Item item = itemService.selectItemByIndexNo(indexNo);
        List<Category> categoryList = categoryService.selectCategoryListByUseYn(categorySearchDTO);
        log.info("item: {}", item);

        model.addAttribute("item",item);
        model.addAttribute("categoryList",categoryList);

        return "items/item_detail";
    }

    @GetMapping("editItem")
    public String modifyItem() {

        return "items/editItem";
    }
}
