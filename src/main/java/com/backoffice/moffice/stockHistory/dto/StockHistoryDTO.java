package com.backoffice.moffice.stockHistory.dto;

import com.backoffice.moffice.common.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString
public class StockHistoryDTO extends BaseDTO {
    private Long itemNo;
    private Long receivingCount;
    private Long receivingPrice;
    private Long stockCount;

}
