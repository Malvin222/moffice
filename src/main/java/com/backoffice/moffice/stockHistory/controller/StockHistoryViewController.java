package com.backoffice.moffice.stockHistory.controller;

import com.backoffice.moffice.stockHistory.dto.StockHistorySearchDTO;
import com.backoffice.moffice.stockHistory.service.StockHistoryService;
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
public class StockHistoryViewController {

    private final StockHistoryService stockHistoryService;

    @GetMapping("stock_history")
    public String stockHistoryList(Model model,
                                   StockHistorySearchDTO stockHistorySearchDTO){
        log.info("=====stockHistorySearchDTO: {}",stockHistorySearchDTO);
        StockHistorySearchDTO result = stockHistoryService.selectStockHistoryListWithPaging(stockHistorySearchDTO);
    model.addAttribute("search", result);
    return "stocks/stock_history";
    }
}
