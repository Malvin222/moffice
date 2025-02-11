package com.backoffice.moffice.items.mapper;

import com.backoffice.moffice.items.dto.ItemSearchDTO;
import com.backoffice.moffice.items.model.Item;
import com.backoffice.moffice.items.model.ItemForExcel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    int getTotalCount(ItemSearchDTO itemSearchDTO);
    Item selectItemByIndexNo(Long indexNo);
    Item selectItemByName(String itemName);
    Item selectItemByBarcodeNo(String barcodeNo);
    List<Item> selectItemList(ItemSearchDTO itemSearchDTO);
    List<Item> selectItemListWithPaging(ItemSearchDTO itemSearchDTO);
    List<ItemForExcel> selectItemListForExcel(ItemSearchDTO itemSearchDTO);
    int insertItem(Item item);
    int updateItem(Item item);
    int deleteItem(Long indexNo);
}
