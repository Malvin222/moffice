$(document).ready(function () {
    // 삭제 버튼 클릭 이벤트 처리
    $('.delete-item-btn').click(function () {
        const itemName = $(this).data('item-name');
        const indexNo = $(this).data('index-no');

        deleteCategory(itemName, indexNo);
    });
});

/**
 * 카테고리 삭제 함수
 * @param {string} itemName - 삭제할 카테고리명
 * @param {number} indexNo - 삭제할 카테고리 ID
 */
// AJAX 요청 데이터

function deleteCategory(itemName, indexNo) {
    if (confirm(itemName + ' 상품을 삭제하시겠습니까?')) {
        $.ajax({
            url: '/api/items/item_delete',
            type: 'POST',
            contentType: 'application/json',
            data: indexNo.toString(),
            success: function () {
                alert(itemName + ' 상품이 삭제 되었습니다.');
                location.reload();
            },
            error: function (xhr) {
                    alert('카테고리 삭제 중 오류가 발생했습니다.');
            }
        });
    }
}