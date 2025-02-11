package com.backoffice.moffice.itemStock.mapper;

import com.backoffice.moffice.itemStock.dto.ItemStockSearchDTO;
import com.backoffice.moffice.itemStock.model.ItemStock;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemStockMapper {
    int getTotalCount(ItemStockSearchDTO ItemStockSearchDTO);
    List<ItemStock> selectItemStockListWithPaging(ItemStockSearchDTO itemStockSearchDTO);
    int insertItemStock(ItemStock itemStock);
    int updateItemStock(ItemStock itemStock);
    int deleteItemStockByItemNo(Long itemNo);
}
