package com.backoffice.moffice.itemStock.controller;

import com.backoffice.moffice.itemStock.dto.ItemStockSearchDTO;
import com.backoffice.moffice.itemStock.service.ItemStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class ItemStockViewController {
    private final ItemStockService itemStockService;

    @GetMapping("item_stock_list")
    public String itemStockList(Model model,
                                ItemStockSearchDTO itemStockSearchDTO) {
        ItemStockSearchDTO result = itemStockService.selectItemStockListWithPaging(itemStockSearchDTO);

        model.addAttribute("search", result);
        return "stocks/item_stock_list";
    }
}
