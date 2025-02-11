package com.backoffice.moffice.stockHistory.service;

import com.backoffice.moffice.itemStock.dto.ItemStockDTO;
import com.backoffice.moffice.stockHistory.dto.StockHistoryConverter;
import com.backoffice.moffice.stockHistory.dto.StockHistoryDTO;
import com.backoffice.moffice.stockHistory.dto.StockHistorySearchDTO;
import com.backoffice.moffice.stockHistory.mapper.StockHistoryMapper;
import com.backoffice.moffice.stockHistory.model.StockHistory;
import com.backoffice.moffice.stockHistory.model.StockHistoryForExcel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockHistoryService {
    private final StockHistoryMapper stockHistoryMapper;
    private final StockHistoryConverter stockHistoryConverter;

    public List<StockHistoryForExcel> selectStockHistoryListForExcel(StockHistorySearchDTO stockHistorySearchDTO){
        return stockHistoryMapper.selectStockHistoryListForExcel(stockHistorySearchDTO);
    }

    public StockHistorySearchDTO selectStockHistoryListWithPaging(StockHistorySearchDTO stockHistorySearchDTO){
        int totalCount = stockHistoryMapper.getTotalCount(stockHistorySearchDTO);
        stockHistorySearchDTO.setTotalCount(totalCount);

        stockHistorySearchDTO.setPaging();

        List<StockHistory> list = stockHistoryMapper.selectStockHistoryListWithPaging(stockHistorySearchDTO);
        stockHistorySearchDTO.setList(list);

        return stockHistorySearchDTO;
    }

    @Transactional
    public int createStockHistory(StockHistory stockHistory){
        return stockHistoryMapper.insertStockHistory(stockHistory);
    }

    @Transactional
    public int saveStockHistory(ItemStockDTO itemStockDTO){
        int affectedRowCount;

        StockHistory stockHistory = stockHistoryConverter.converterDtoToModel(itemStockDTO);

        affectedRowCount = createStockHistory(stockHistory);
        return affectedRowCount;

    }

}
