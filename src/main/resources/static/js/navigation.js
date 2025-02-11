/**
 * navigation.js - 사이드 네비게이션 제어를 위한 스크립트
 */
$(document).ready(function() {

    // Bootstrap의 기본 반응형 동작 비활성화
    $('.collapse').addClass('show');
    $('.collapse').collapse({
        toggle: false
    });

    // 현재 페이지 경로 가져오기
    const path = window.location.pathname;

    // 상품관리 메뉴 요소
    const $itemsCollapse = $('#collapseItems');
    // 재고관리 메뉴 요소
    const $stocksCollapse = $('#collapseStocks');
    // 통계 메뉴 요소
    const $statisticsCollapse = $('#collapseStatistics');

    // 메뉴 펼치기
    if (path.includes('/items') || path.includes('/category')) {
        const bsCollapse = new bootstrap.Collapse($itemsCollapse[0], {
            toggle: false
        });
        bsCollapse.show();

        // 현재 페이지에 해당하는 메뉴 강조
        if (path.includes('/items')) {
            $('.item-link').addClass('fw-bold');
        } else if (path.includes('/category')) {
            $('.category-link').addClass('fw-bold');
        }
    } else if (path.includes('/stocks')) {
        const bsCollapse = new bootstrap.Collapse($stocksCollapse[0], {
            toggle: false
        });
        bsCollapse.show();

        // 페이지 메뉴 강조
        if (path.includes('/item_stock_list')) {
            $('.item-stock-link').addClass('fw-bold');
        } else if (path.includes('/stock_history')) {
            $('.stock-history-link').addClass('fw-bold');
        }
    } else if (path.includes('/statistics')){
        const bsCollapse = new bootstrap.Collapse($statisticsCollapse[0],{
            toggle:false
        });
        bsCollapse.show();

        //페이지 메뉴 강조
        if (path.includes('/statistics')){
            $('.statistics-link').addClass('fw-bold');
        }
    }


});