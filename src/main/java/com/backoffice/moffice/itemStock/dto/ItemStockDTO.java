package com.backoffice.moffice.itemStock.dto;

import com.backoffice.moffice.common.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString
public class ItemStockDTO extends BaseDTO {
    private Long itemNo;
    private Long stockCount;
    private Long receivingCount;
    private Long receivingPrice;
}
