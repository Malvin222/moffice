package com.backoffice.moffice.items.service;


import com.backoffice.moffice.itemStock.model.ItemStock;
import com.backoffice.moffice.itemStock.service.ItemStockService;
import com.backoffice.moffice.items.dto.ItemConverter;
import com.backoffice.moffice.items.dto.ItemDTO;
import com.backoffice.moffice.items.dto.ItemSearchDTO;
import com.backoffice.moffice.items.mapper.ItemMapper;
import com.backoffice.moffice.items.model.Item;
import com.backoffice.moffice.items.model.ItemForExcel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {
    private static final String ITEMS_CACHE_KEY = "items:all";
    private final ItemMapper itemMapper;
    private final ItemConverter itemConverter;
    private final ItemStockService itemStockService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Cacheable(value = "items", key = "'all'")
    public List<Item> getAllItems() {
        return itemMapper.getAllItems();
    }

    @Cacheable(value = "items", key = "#indexNo")
    public Item selectItemByIndexNo(Long indexNo) {
        log.info("== selectItemById  indexNo== : {}", indexNo);
        Item item = itemMapper.selectItemByIndexNo(indexNo);
        return itemMapper.selectItemByIndexNo(indexNo);
    }

    @Cacheable(value = "items", key = "'all'")
    public void checkIfExistsOrException(Item item) throws NotFoundException {
        log.info("== checkIfExistsOrException == : {}", item);
        if (item == null) {
            throw new NotFoundException("아이템을 찾을 수 없습니다.");
        }
    }

    @Cacheable(value = "items", key = "#itemName")
    public Item selectItemByName(String itemName) {
        return itemMapper.selectItemByName(itemName);
    }

    @Cacheable(value = "items", key = "#barcodeNo")
    public Item selectItemByBarcodeNo(String barcodeNo) {
        return itemMapper.selectItemByBarcodeNo(barcodeNo);
    }

    @Cacheable(value = "items", key = "'all'")
    public List<Item> selectItemList(ItemSearchDTO itemSearchDTO) {
        return itemMapper.selectItemList(itemSearchDTO);
    }

    @Cacheable(value = "items", key = "'all'")
    public List<ItemForExcel> selectItemListForExcel(ItemSearchDTO itemSearchDTO) {
        return itemMapper.selectItemListForExcel(itemSearchDTO);
    }

    @Cacheable(value = "items", key = "'all'")
    public ItemSearchDTO selectItemListWithPaging(ItemSearchDTO itemSearchDTO) {
        //총 개수 조회
        int totalCount = itemMapper.getTotalCount(itemSearchDTO);
        itemSearchDTO.setTotalCount(totalCount);

        //페이징 정보 계산
        itemSearchDTO.setPaging();

        List<Item> list = itemMapper.selectItemListWithPaging(itemSearchDTO);
        itemSearchDTO.setList(list);

        return itemSearchDTO;
    }

    @Transactional
    @CacheEvict(value = "items", key = "'all'")
    public int createItem(Item item) {
        return itemMapper.insertItem(item);
    }

    @Transactional
    @CacheEvict(value = "items", key = "'all'")
    public int saveItem(ItemDTO itemDTO) {
        int affectedRowCount;
        log.info("saveItem service itemDTO: {}", itemDTO);

        // DTO를 모델로 변환
        Item item = itemConverter.converterDtoToModel(itemDTO);

        Item existingItem = selectItemByName(itemDTO.getItemName());
        if (existingItem != null) {
            log.warn("중복된 제품이 존재합니다:{}", itemDTO.getItemName());
            return 0;
        }

        existingItem = selectItemByBarcodeNo(itemDTO.getBarcodeNo());
        if (existingItem != null) {
            log.warn("중복된 바코드가 존재합니다:{}", itemDTO.getBarcodeNo());
            return 0;
        }

        // 중복이 없으면 제품 생성
        affectedRowCount = createItem(item);

        // 제품 생성이 성공한 경우에만 재고 정보 등록
        if (affectedRowCount > 0) {
            ItemStock itemStock = new ItemStock();
            itemStock.setItemNo(item.getIndexNo());
            itemStock.setStockCount(0L);
            itemStockService.createItemStock(itemStock);
        }

        return affectedRowCount;
    }

    @Transactional
    @CacheEvict(value = "items", key = "'all'")
    public int updateItem(ItemDTO itemDTO) {
        log.info("update itemDTO:{}", itemDTO);

        //DTO를 모델로 변환
        Item item = itemConverter.converterDtoToModel(itemDTO);
        return itemMapper.updateItem(item);
    }

    @Transactional
    @CacheEvict(value = "items", key = "'all'")
    public int deleteItem(Long indexNo) {
        log.info(" service delete item indexNo:{}", indexNo);
        itemStockService.deleteItemStockByItemNo(indexNo);
        return itemMapper.deleteItem(indexNo);
    }


}
