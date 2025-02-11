package com.backoffice.moffice.category.controller;

import com.backoffice.moffice.category.dto.CategoryDTO;
import com.backoffice.moffice.category.dto.CategorySearchDTO;
import com.backoffice.moffice.category.model.CategoryForExcel;
import com.backoffice.moffice.category.service.CategoryService;
import com.backoffice.moffice.common.excel.service.ExcelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoriesApiController {
    private final CategoryService categoryService;
    private final ExcelService excelService;

    @PostMapping("category_save")
    public ResponseEntity<String> saveCategory(HttpServletRequest request,
                                               @RequestBody CategoryDTO categoryDTO) {
        // 클라이언트 IP 설정
        String clientIp = request.getRemoteAddr();
        categoryDTO.setCreateIp(clientIp);

        // 서비스 호출
        int affectedRowCount = categoryService.saveCategory(categoryDTO);

        log.info("클라이언트 요청 처리 시작: {}", categoryDTO);
        log.info("IP: {}", clientIp);
        log.info("affectedRowCount: {}", affectedRowCount);

        // 결과 확인
        if (affectedRowCount > 0) {
            log.info("카테고리 등록 성공: {}", affectedRowCount);
            return ResponseEntity.status(HttpStatus.CREATED).body("카테고리 등록에 성공했습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 카테고리가 존재합니다.");
        }
    }

    @PostMapping("category_delete/{indexNo}")
    public ResponseEntity<String> deleteCategory(HttpServletRequest request,
                                                 @PathVariable Long indexNo) throws NotFoundException {

        int affectedRowCount = categoryService.removeCategory(indexNo);

        if (affectedRowCount > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("카테고리가 삭제 되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("카테고리 삭제가 실패하였습니다.");
        }
    }

    @PostMapping("category_update")
    public ResponseEntity<String> updateCategory(HttpServletRequest request,
                                                 @RequestBody CategoryDTO categoryDTO) {
        String clientIp = request.getRemoteAddr();
        categoryDTO.setUpdateIp(clientIp);

        int affectedRowCount = categoryService.updateCategory(categoryDTO);

        log.info("클라이언트 요청 처리 시작: {}", categoryDTO);
        log.info("IP: {}", clientIp);
        log.info("affectedRowCount: {}", affectedRowCount);

        if (affectedRowCount > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("카테고리가 수정 되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("카테고리 수정이 실패하였습니다.");
        }
    }

    @GetMapping("/excel_download")
    public void downloadExcel(HttpServletResponse response, CategorySearchDTO categorySearchDTO) throws IOException {
        List<CategoryForExcel> category = categoryService.selectCategoryListForExcel(categorySearchDTO);
        log.info(" ==== excelDownload category: {} ==== ", category);
        String fileName = "카테고리목록_" + LocalDate.now() + ".xlsx";
        excelService.downloadExcel(response, category, fileName, "카테고리목록");
    }

}
