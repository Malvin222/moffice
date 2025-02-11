package com.backoffice.moffice.items.dto;

import com.backoffice.moffice.items.model.Item;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ItemConverter {
    public Item converterDtoToModel(ItemDTO itemDTO){
        return Item.builder()
                .indexNo(itemDTO.getIndexNo())
                .categoryNo(itemDTO.getCategoryNo())
                .itemName(itemDTO.getItemName())
                .barcodeNo(itemDTO.getBarcodeNo())
                .price(itemDTO.getPrice())
                .createDatetime(LocalDateTime.now().toString())
                .createIp(itemDTO.getCreateIp())
                .updateDatetime(LocalDateTime.now().toString())
                .updateIp(itemDTO.getUpdateIp())
                .useYn(itemDTO.getUseYn())
                .build();
    }
}
