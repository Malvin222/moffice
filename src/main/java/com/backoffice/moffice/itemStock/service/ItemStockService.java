package com.backoffice.moffice.itemStock.service;

import com.backoffice.moffice.itemStock.dto.ItemStockConverter;
import com.backoffice.moffice.itemStock.dto.ItemStockDTO;
import com.backoffice.moffice.itemStock.dto.ItemStockSearchDTO;
import com.backoffice.moffice.itemStock.mapper.ItemStockMapper;
import com.backoffice.moffice.itemStock.model.ItemStock;
import com.backoffice.moffice.stockHistory.service.StockHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemStockService {
    private final ItemStockMapper itemStockMapper;
    private final StockHistoryService stockHistoryService;
    private final ItemStockConverter itemStockConverter;

    public ItemStockSearchDTO selectItemStockListWithPaging(ItemStockSearchDTO itemStockSearchDTO){
        // 총 개수 조회
        int totalCount = itemStockMapper.getTotalCount(itemStockSearchDTO);
        itemStockSearchDTO.setTotalCount(totalCount);

        itemStockSearchDTO.setPaging();

        List<ItemStock> list = itemStockMapper.selectItemStockListWithPaging(itemStockSearchDTO);
        itemStockSearchDTO.setList(list);

        return itemStockSearchDTO;
    }

    @Transactional
    public int createItemStock(ItemStock itemStock){
        return itemStockMapper.insertItemStock(itemStock);
    }

    @Transactional
    public void deleteItemStockByItemNo(Long itemNo){
        itemStockMapper.deleteItemStockByItemNo(itemNo);
    }
    @Transactional
    public int updateItemStock(ItemStock itemStock){
        return itemStockMapper.updateItemStock(itemStock);
    }

    @Transactional
    public int saveItemStock(ItemStockDTO itemStockDTO){
        int affectedRowCount;

        ItemStock itemStock = itemStockConverter.converterDtoToModel(itemStockDTO);
        Long presentStockCount = (itemStockDTO.getStockCount() != null) ? itemStockDTO.getStockCount() : 0L;
        Long addCount = itemStockDTO.getReceivingCount();
        itemStock.setStockCount(presentStockCount + addCount);

        itemStockDTO.setStockCount(presentStockCount + addCount);

        affectedRowCount = updateItemStock(itemStock);

        // 재고 변경 성공 후 재고이력 등록
        if (affectedRowCount > 0){
            stockHistoryService.saveStockHistory(itemStockDTO);
        }

        return affectedRowCount;
    }

}
