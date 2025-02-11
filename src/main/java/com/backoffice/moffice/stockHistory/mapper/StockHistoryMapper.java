package com.backoffice.moffice.stockHistory.mapper;

import com.backoffice.moffice.stockHistory.dto.StockHistorySearchDTO;
import com.backoffice.moffice.stockHistory.model.StockHistory;
import com.backoffice.moffice.stockHistory.model.StockHistoryForExcel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StockHistoryMapper {
    int getTotalCount(StockHistorySearchDTO StockHistorySearchDTO);
    List<StockHistory> selectStockHistoryListWithPaging(StockHistorySearchDTO stockHistorySearchDTO);
    List<StockHistoryForExcel> selectStockHistoryListForExcel(StockHistorySearchDTO stockHistorySearchDTO);
    int insertStockHistory(StockHistory stockHistory);
}
