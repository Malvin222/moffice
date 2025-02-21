package com.backoffice.moffice.items;

import com.backoffice.moffice.itemStock.service.ItemStockService;
import com.backoffice.moffice.items.dto.ItemConverter;
import com.backoffice.moffice.items.dto.ItemDTO;
import com.backoffice.moffice.items.mapper.ItemMapper;
import com.backoffice.moffice.items.model.Item;
import com.backoffice.moffice.items.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RedisItemServicePerformanceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemMapper itemMapper;

    @Mock
    private ItemConverter itemConverter;

    @Mock
    private ItemStockService itemStockService;

    @Test
    @DisplayName("Redis 캐시를 사용한 500개 상품 등록 및 조회 성능 테스트")
    void bulkInsertAndSelectPerformanceTestWithCache() {
        // 데이터 저장 테스트
        List<ItemDTO> itemDTOs = createTestData(500);

        long insertStartTime = System.nanoTime();
        for (ItemDTO dto : itemDTOs) {
            itemService.saveItem(dto);
        }
        long insertEndTime = System.nanoTime();
        double insertDuration = (insertEndTime - insertStartTime) / 1_000_000.0;

        // 첫 번째 조회(DB에서 조회 후 캐시에 저장)
        long firstSelectStartTime = System.nanoTime();
        List<Item> firstSelect = itemService.getAllItems();
        long firstSelectEndTime = System.nanoTime();
        double firstSelectDuration = (firstSelectEndTime - firstSelectStartTime) / 1_000_000.0;

        // 두 번째 조회(캐시에서 조회)
        long secondSelectStartTime = System.nanoTime();
        List<Item> secondSelect = itemService.getAllItems();
        long secondSelectEndTime = System.nanoTime();
        double secondSelectDuration = (secondSelectEndTime - secondSelectStartTime) / 1_000_000.0;

        // 결과 출력
        log.info("=== Redis 캐시 성능 테스트 결과 ===");
        log.info("데이터 저장 소요시간:{} ({}ms/건)", insertDuration, insertDuration / 500.0);
        log.info("첫 번째 조회 소요시간(DB):{}초", firstSelectDuration);
        log.info("두번째 조회 소요시간(Cache):{}초", secondSelectDuration);
        log.info("캐시 사용으로 인한 성능 향상: {}배", firstSelectDuration / secondSelectDuration);
    }

    private List<ItemDTO> createTestData(int count) {
        List<ItemDTO> items = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            items.add(createTestItemDTO(i));
        }
        return items;
    }

    private ItemDTO createTestItemDTO(int index) {
        return ItemDTO.builder()
                .itemName("캐스테스트_" + index)
                .barcodeNo("CACHE" + String.format("%09d", index))
                .categoryNo(1L)
                .price(1000L * index)
                .categoryName("테스트카테고리")
                .useYn("Y")
                .build();
    }
}
