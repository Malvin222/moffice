package com.backoffice.moffice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Pagination {
    private int page = 1; // 현재 페이지
    private int pageSize = 10; // 페이지당 게시글 수
    private int totalCount; //전체 게시글 수
    private int totalPages; // 전체 페이지 수
    private int startPage; // 시작 페이지
    private int endPage; // 끝 페이지
    private int offset; // SQL용 offset

    public void setPaging() {
        // 전체 페이지 수 계산
        this.totalPages = (int) Math.ceil((double) totalCount / pageSize);

        //현재 페이지 그룹의 시작과 끝 페이지 계산
        this.startPage = ((page - 1) / 10) * 10 + 1;
        this.endPage = Math.min(startPage + 9, totalPages);

        // MySQL LIMIT 구문에 사용될 offset 계산
        this.offset = (page - 1) * pageSize;
    }

}
