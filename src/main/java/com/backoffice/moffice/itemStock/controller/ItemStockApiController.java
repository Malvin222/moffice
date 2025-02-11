package com.backoffice.moffice.itemStock.controller;

import com.backoffice.moffice.itemStock.dto.ItemStockDTO;
import com.backoffice.moffice.itemStock.model.ItemStock;
import com.backoffice.moffice.itemStock.service.ItemStockService;
import com.backoffice.moffice.stockHistory.service.StockHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class ItemStockApiController {
    private final ItemStockService itemStockService;
    private final StockHistoryService stockHistoryService;

    @PostMapping("item_stock_save")
    public ResponseEntity<String> saveItemStock(HttpServletRequest request,
                                                @RequestBody List<ItemStockDTO> itemStockDTOs){

        int affectedRowCount = 0;
        for(ItemStockDTO itemStockDTO : itemStockDTOs){
            String clientIp = request.getRemoteAddr();
            itemStockDTO.setCreateIp(clientIp);
            log.info("saveItemStockDTO:{}", itemStockDTO);
            affectedRowCount = itemStockService.saveItemStock(itemStockDTO);
        }

        if (affectedRowCount > 0) {
            log.info("재고 등록 성공: {}", affectedRowCount);
            return ResponseEntity.status(HttpStatus.CREATED).body("재고 등록에 성공했습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("재고 등록에 실패하였습니다.");
        }
    }
}
