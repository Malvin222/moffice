package com.backoffice.moffice.itemStock.dto;

import com.backoffice.moffice.common.dto.BaseDTO;
import com.backoffice.moffice.itemStock.model.ItemStock;
import com.backoffice.moffice.items.model.Item;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ItemStockSearchDTO extends BaseDTO {
    private Long itemNo;
    private Long stockCount;
    private String categoryName;
    private String barcodeNo;
    private String itemName;
    private List<ItemStock> list;
}
