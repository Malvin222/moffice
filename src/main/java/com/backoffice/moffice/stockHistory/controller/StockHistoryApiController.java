package com.backoffice.moffice.stockHistory.controller;

import com.backoffice.moffice.common.excel.service.ExcelService;
import com.backoffice.moffice.stockHistory.dto.StockHistorySearchDTO;
import com.backoffice.moffice.stockHistory.model.StockHistoryForExcel;
import com.backoffice.moffice.stockHistory.service.StockHistoryService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/stocks")
@RequiredArgsConstructor
public class StockHistoryApiController {
    private final ExcelService excelService;
    private final StockHistoryService stockHistoryService;

    @GetMapping("/stock_history/excel_download")
    public void downloadExcel(HttpServletResponse response, StockHistorySearchDTO stockHistorySearchDTO) throws IOException{
        List<StockHistoryForExcel> stockHistories = stockHistoryService.selectStockHistoryListForExcel(stockHistorySearchDTO);
        log.info(" ==== excelDownload stockHistories: {} ====", stockHistories);
        String fileName = "입고이력_"+ LocalDate.now() +".xlsx";
        excelService.downloadExcel(response, stockHistories, fileName, "입고이력");
    }
}
