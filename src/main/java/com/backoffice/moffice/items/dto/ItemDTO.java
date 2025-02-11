package com.backoffice.moffice.items.dto;

import com.backoffice.moffice.common.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString
public class ItemDTO extends BaseDTO {
    private String itemName;
    private String barcodeNo;
    private Long price;

    private Long categoryNo;
    private String categoryName;
}
