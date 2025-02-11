package com.backoffice.moffice.items.dto;

import com.backoffice.moffice.common.dto.BaseDTO;
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
public class ItemSearchDTO extends BaseDTO {
    private String categoryNo;
    private String itemCode;
    private String itemName;
    private String barcodeNo;
    private Long price;
    private List<Item> list;

    private String categoryName;
}
