package com.backoffice.moffice.itemStock.dto;

import com.backoffice.moffice.itemStock.model.ItemStock;
import com.backoffice.moffice.items.dto.ItemDTO;
import com.backoffice.moffice.items.model.Item;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ItemStockConverter {
    public ItemStock converterDtoToModel(ItemStockDTO itemStockDTO){
        return ItemStock.builder()
                .indexNo(itemStockDTO.getIndexNo())
                .itemNo(itemStockDTO.getItemNo())
                .stockCount(itemStockDTO.getStockCount())
                .createDatetime((LocalDateTime.now().toString()))
                .createIp(itemStockDTO.getCreateIp())
                .build();
    }
}
