package com.backoffice.moffice.common.excel.service;

import com.backoffice.moffice.common.excel.model.ExcelColumn;
import com.backoffice.moffice.common.excel.model.ExcelHeader;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelService {

    /**
     * 엑셀 워크북 생성
     */
    public <T> XSSFWorkbook createExcelWorkbook(List<T> data, String sheetName) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(sheetName);

            // 헤더 스타일 생성
            CellStyle headerStyle = createHeaderStyle(workbook);

            // 헤더 정보 가져오기
            List<ExcelHeader> headers = getHeaderInfo(data.get(0).getClass());

            // 헤더 생성
            createHeader(sheet, headerStyle, headers);

            // 데이터 입력
            fillData(sheet, data, headers);

            // 컬럼 너비 자동조정
            adjustColumns(sheet, headers.size());

            return workbook;
        } catch (Exception e) {
            log.error("Excel 생성 중 오류 발생:", e);
            throw new RuntimeException("Excel 생성 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 엑셀 다운로드 처리
     */
    public <T> void downloadExcel(HttpServletResponse response, List<T> data, String fileName, String sheetName) throws IOException {
        XSSFWorkbook workbook = createExcelWorkbook(data, sheetName);
        try {
            setResponseHeaders(response, fileName);
            workbook.write(response.getOutputStream());
        } finally {
            workbook.close();
        }
    }

    private CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setWrapText(true);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        return headerStyle;
    }

    private List<ExcelHeader> getHeaderInfo(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ExcelColumn.class))
                .map(field -> {
                    ExcelColumn column = field.getAnnotation(ExcelColumn.class);
                    return new ExcelHeader(field.getName(), column.headerName(), column.order());
                })
                .sorted(Comparator.comparingInt(ExcelHeader::getOrder))
                .collect(Collectors.toList());
    }

    private void createHeader(XSSFSheet sheet, CellStyle headerStyle, List<ExcelHeader> headers) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i).getHeaderName());
            cell.setCellStyle(headerStyle);
        }
    }

    private <T> void fillData(XSSFSheet sheet, List<T> data, List<ExcelHeader> headers) {

        CellStyle dataStyle = sheet.getWorkbook().createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);  // 가로 중앙정렬

        int rowNum = 1;
        for (T item : data) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < headers.size(); i++) {
                try {
                    Field field = item.getClass().getDeclaredField(headers.get(i).getFieldName());
                    field.setAccessible(true);
                    Object value = field.get(item);
                    Cell cell = row.createCell(i);
                    setCellValue(cell, value);
                    cell.setCellStyle(dataStyle);
                } catch (Exception e) {
                    log.error("엑셀 데이터 입력 중 오류: ", e);
                }
            }
        }
    }

    private void setCellValue(Cell cell, Object value) {
        if (value == null) {
            cell.setCellValue("");
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else if (value instanceof LocalDateTime) {
            cell.setCellValue(((LocalDateTime) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } else if (value instanceof LocalDate) {
            cell.setCellValue(((LocalDate) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        } else {
            cell.setCellValue(value.toString());
        }
    }

    private void adjustColumns(XSSFSheet sheet, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);
            if (sheet.getColumnWidth(i) < 3000) {
                sheet.setColumnWidth(i, 3000);
            }
        }
    }

    private void setResponseHeaders(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        String encodedFilename = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + encodedFilename);
    }

}
