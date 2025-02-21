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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ItemServicePerformanceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemMapper itemMapper;

    @Mock
    private ItemConverter itemConverter;

    @Mock
    private ItemStockService itemStockService;

    @Test
    @DisplayName("500개 상품 등록 및 조회 성능 테스트")
    @Transactional
    void bulkInsertAndSelectPerformanceTest() {
        // 테스트 데이터 준비
        List<ItemDTO> itemDTOs = new ArrayList<>();

        // 테스트 데이터 생성 500개
        for (int i = 1; i <= 500; i++) {
            ItemDTO dto = ItemDTO.builder()
                    .itemName("테스트상품" + i)
                    .barcodeNo("BARCODE" + String.format("%09d", i))
                    .categoryNo(1L)
                    .price(100000L + i)
                    .createDatetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .useYn("Y")
                    .build();
            itemDTOs.add(dto);
        }

        // 저장 성능 측정
        long insertStartTime = System.nanoTime();

        for (ItemDTO dto : itemDTOs) {
            int result = itemService.saveItem(dto);
        }

        long insertEndTime = System.nanoTime();
        long insertDuration = TimeUnit.NANOSECONDS.toMillis(insertEndTime - insertStartTime);

        // 저장된 데이터 수 확인
        log.info("저장 소요 시간: {}ms", insertDuration);
        log.info("상품당 평균 저장시간: {}ms", insertDuration / 500);

        performanceTest1();
    }

    private void performanceTest() {
        // 1. 워밍업 단계
        warmup();

        // 2. 실제 성능 측정
        List<Long> selectTimes = new ArrayList<>();
        int numberOfTests = 10;  // 테스트 반복 횟수

        for (int i = 0; i < numberOfTests; i++) {
            // GC 호출 (선택사항)
            System.gc();

            // 측정 시작
            long startTime = System.nanoTime();

            List<Item> items = itemService.getAllItems();

            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            selectTimes.add(duration);

            log.info("{}번째 조회 - 소요시간: {}ms, 조회된 건수: {}",
                    i + 1,
                    duration / 1_000_000.0,
                    items.size());
        }

        // 3. 통계 계산
        DoubleSummaryStatistics stats = selectTimes.stream()
                .mapToDouble(time -> time / 1_000_000.0)  // nano to milliseconds
                .summaryStatistics();

        log.info("=== 성능 테스트 결과 ===");
        log.info("평균 조회 시간: {}ms", stats.getAverage());
        log.info("최소 조회 시간: {}ms", stats.getMin());
        log.info("최대 조회 시간: {}ms", stats.getMax());
        log.info("표준편차: {}ms", calculateStandardDeviation(selectTimes));
    }

    @Test
    void improvedPerformanceTest() {
        // 워밍업
        warmup();

        int testCount = 10;
        List<Double> executionTimes = new ArrayList<>();

        for (int i = 0; i < testCount; i++) {
            long startTime = System.nanoTime();
            List<Item> items = itemService.getAllItems();
            long endTime = System.nanoTime();
            long durationInNanos = endTime - startTime;

            // 두 가지 방식으로 변환
            long millisWithTimeUnit = TimeUnit.NANOSECONDS.toMillis(durationInNanos);
            double millisWithDivision = durationInNanos / 1_000_000.0;

            executionTimes.add(millisWithDivision);

            log.info("실행 {}번째", i + 1);
            log.info("- TimeUnit 변환: {}ms", millisWithTimeUnit);
            log.info("- 직접 나누기: {}ms", String.format("%.3f", millisWithDivision));
            log.info("- 조회된 건수: {}", items.size());
        }

        // 통계 계산
        DoubleSummaryStatistics stats = executionTimes.stream()
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();

        log.info("\n=== 최종 성능 측정 결과 ===");
        log.info("평균 실행 시간: {}ms", String.format("%.3f", stats.getAverage()));
        log.info("최소 실행 시간: {}ms", String.format("%.3f", stats.getMin()));
        log.info("최대 실행 시간: {}ms", String.format("%.3f", stats.getMax()));
    }

    private void warmup() {
        log.info("워밍업 시작...");
        for (int i = 0; i < 5; i++) {
            itemService.getAllItems();
        }
        log.info("워밍업 완료");

        try {
            Thread.sleep(1000);  // 워밍업 후 잠시 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private double calculateStandardDeviation(List<Long> times) {
        double mean = times.stream().mapToDouble(time -> time / 1_000_000.0).average().orElse(0.0);
        double variance = times.stream()
                .mapToDouble(time -> {
                    double t = time / 1_000_000.0;
                    return Math.pow(t - mean, 2);
                })
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    // 조회 성능 측정 (전체 목록 조회)
    void performanceTest1() {
        long selectStartTime = System.nanoTime();

        List<Item> allItems = itemService.getAllItems();

        long selectEndTime = System.nanoTime();
        long selectDuration = selectEndTime - selectStartTime;

        log.info("전체 상품 조회 소요시간:{}ns", selectDuration);
        log.info("조회된 상품 수: {}", allItems.size());

        // 성능 지표 출력
        log.info("=== 성능 테스트 결과 ===");
        log.info("전체 조회 시간:{}ns", selectDuration);
    }

    /* 500개일때
저장 소요 시간: 697ms
상품당 평균 저장시간: 1ms
전체 상품 조회 소요시간:45800ns
조회된 상품 수: 0
=== 성능 테스트 결과 ===
평균 저장 시간:1ms/건
전체 조회 시간:45800ns
    * */

        /* 1000개일때
저장 소요 시간: 818ns
상품당 평균 저장시간: 1ms
전체 상품 조회 소요시간:47600ns
조회된 상품 수: 0
=== 성능 테스트 결과 ===
평균 저장 시간:1ms/건
전체 조회 시간:47600ms
2025-02-21T12:12:59.298+09:00  INFO 8684 --- [moffice] [    Test worker] c.b.m.items.ItemServicePerformanceTest   : 워밍업 완료
2025-02-21T12:13:00.347+09:00  INFO 8684 --- [moffice] [    Test worker] c.b.m.items.ItemServicePerformanceTest   : 1번째 조회 - 소요시간: 0.095ms, 조회된 건수: 0
2025-02-21T12:13:00.385+09:00  INFO 8684 --- [moffice] [    Test worker] c.b.m.items.ItemServicePerformanceTest   : 2번째 조회 - 소요시간: 0.0827ms, 조회된 건수: 0
2025-02-21T12:13:00.423+09:00  INFO 8684 --- [moffice] [    Test worker] c.b.m.items.ItemServicePerformanceTest   : 3번째 조회 - 소요시간: 0.0892ms, 조회된 건수: 0
2025-02-21T12:13:00.462+09:00  INFO 8684 --- [moffice] [    Test worker] c.b.m.items.ItemServicePerformanceTest   : 4번째 조회 - 소요시간: 0.0915ms, 조회된 건수: 0
2025-02-21T12:13:00.501+09:00  INFO 8684 --- [moffice] [    Test worker] c.b.m.items.ItemServicePerformanceTest   : 5번째 조회 - 소요시간: 0.0953ms, 조회된 건수: 0
2025-02-21T12:13:00.539+09:00  INFO 8684 --- [moffice] [    Test worker] c.b.m.items.ItemServicePerformanceTest   : 6번째 조회 - 소요시간: 0.1045ms, 조회된 건수: 0
2025-02-21T12:13:00.579+09:00  INFO 8684 --- [moffice] [    Test worker] c.b.m.items.ItemServicePerformanceTest   : 7번째 조회 - 소요시간: 0.1112ms, 조회된 건수: 0
2025-02-21T12:13:00.619+09:00  INFO 8684 --- [moffice] [    Test worker] c.b.m.items.ItemServicePerformanceTest   : 8번째 조회 - 소요시간: 0.0881ms, 조회된 건수: 0
2025-02-21T12:13:00.659+09:00  INFO 8684 --- [moffice] [    Test worker] c.b.m.items.ItemServicePerformanceTest   : 9번째 조회 - 소요시간: 0.1006ms, 조회된 건수: 0
2025-02-21T12:13:00.699+09:00  INFO 8684 --- [moffice] [    Test worker] c.b.m.items.ItemServicePerformanceTest   : 10번째 조회 - 소요시간: 0.0945ms, 조회된 건수: 0
2025-02-21T12:13:00.704+09:00  INFO 8684 --- [moffice] [    Test worker] c.b.m.items.ItemServicePerformanceTest   : === 성능 테스트 결과 ===
2025-02-21T12:13:00.705+09:00  INFO 8684 --- [moffice] [    Test worker] c.b.m.items.ItemServicePerformanceTest   : 평균 조회 시간: 0.09526ms
2025-02-21T12:13:00.705+09:00  INFO 8684 --- [moffice] [    Test worker] c.b.m.items.ItemServicePerformanceTest   : 최소 조회 시간: 0.0827ms
2025-02-21T12:13:00.705+09:00  INFO 8684 --- [moffice] [    Test worker] c.b.m.items.ItemServicePerformanceTest   : 최대 조회 시간: 0.1112ms

    * */

}
