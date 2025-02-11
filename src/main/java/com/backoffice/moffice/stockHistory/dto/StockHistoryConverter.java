package com.backoffice.moffice.stockHistory.dto;

import com.backoffice.moffice.itemStock.dto.ItemStockDTO;
import com.backoffice.moffice.stockHistory.model.StockHistory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StockHistoryConverter {
    public StockHistory converterDtoToModel(ItemStockDTO itemStockDTO){
        return StockHistory.builder()
                .indexNo(itemStockDTO.getIndexNo())
                .itemNo(itemStockDTO.getItemNo())
                .stockCount(itemStockDTO.getStockCount())
                .receivingCount(itemStockDTO.getReceivingCount())
                .receivingPrice(itemStockDTO.getReceivingPrice())
                .createDatetime((LocalDateTime.now().toString()))
                .createIp(itemStockDTO.getCreateIp())
                .build();
    }
}
