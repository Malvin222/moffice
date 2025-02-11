package com.backoffice.moffice.items.controller;

import com.backoffice.moffice.common.excel.service.ExcelService;
import com.backoffice.moffice.itemStock.model.ItemStock;
import com.backoffice.moffice.itemStock.service.ItemStockService;
import com.backoffice.moffice.items.dto.ItemDTO;
import com.backoffice.moffice.items.dto.ItemSearchDTO;
import com.backoffice.moffice.items.model.Item;
import com.backoffice.moffice.items.model.ItemForExcel;
import com.backoffice.moffice.items.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemApiController {
    private final ItemService itemService;
    private final ExcelService excelService;
    private final ItemStockService itemStockService;

    @PostMapping("item_save")
    public ResponseEntity<String> saveItem(HttpServletRequest request,
                                           @RequestBody ItemDTO itemDTO) {
        //클라이언트 IP 설정
        String clientIp = request.getRemoteAddr();
        itemDTO.setCreateIp(clientIp);

        // 검증
        if(!itemDTO.getBarcodeNo().matches("^[A-Za-z0-9]+$")){
            return ResponseEntity.badRequest().body("바코드 번호는 영어와 숫자만 입력 가능합니다.");
        }

        //서비스 호출
        int affectedRowCount = itemService.saveItem(itemDTO);

        log.info("클라이언트 요청 처리 시작: {}", itemDTO);
        log.info("IP: {}", clientIp);
        log.info("affectedRowCount: {}", affectedRowCount);


        //결과 확인
        if (affectedRowCount > 0) {
            log.info("제품 등록 성공: {}", affectedRowCount);
            return ResponseEntity.status(HttpStatus.CREATED).body("아이템 등록에 성공했습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 제품이 존재합니다.");
        }
    }

    @PostMapping("item_update")
    public ResponseEntity<String> updateItem(HttpServletRequest request,
                                             @RequestBody ItemDTO itemDTO) {
        String clientIp = request.getRemoteAddr();
        itemDTO.setUpdateIp(clientIp);

        int affectedRowCount = itemService.updateItem(itemDTO);
        if (affectedRowCount > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("상품이 수정 되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("상품 수정이 실패하였습니다.");
        }
    }

    @PostMapping("item_delete")
    public ResponseEntity<String> deleteItem(HttpServletRequest request,
                                             @RequestBody Long indexNo) {
        log.info(" controller delete indexNo: {}", indexNo);
        int affectedRowCount = itemService.deleteItem(indexNo);

        if (affectedRowCount > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("상품이 삭제 되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("상품 삭제가 실패하였습니다.");
        }
    }

    @GetMapping("/excel_download")
    public void downloadExcel(HttpServletResponse response, ItemSearchDTO itemSearchDTO) throws IOException {
        List<ItemForExcel> items = itemService.selectItemListForExcel(itemSearchDTO);
        log.info(" ==== excelDownload item: {} ==== ",items);
        String fileName = "상품목록_"+ LocalDate.now() +".xlsx";
        excelService.downloadExcel(response, items, fileName, "상품목록");
    }

}
