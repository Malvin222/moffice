$(document).ready(function () {
    // 삭제 버튼 클릭 이벤트 처리
    $('.delete-category-btn').click(function () {
        const categoryName = $(this).data('category-name');
        const indexNo = $(this).data('index-no');
        console.log("indexNo:" + indexNo);
        deleteCategory(categoryName, indexNo);
    });
});

/**
 * 카테고리 삭제 함수
 * @param {string} categoryName - 삭제할 카테고리명
 * @param {number} indexNo - 삭제할 카테고리 ID
 */

function deleteCategory(categoryName, indexNo) {
    if (confirm(categoryName + ' 카테고리를 삭제하시겠습니까?')) {
        $.ajax({
            url: '/api/category/category_delete/' + indexNo,
            type: 'POST',
            success: function () {
                alert(categoryName + '카테고리가 삭제 되었습니다.');
                location.reload();
            },
            error: function (xhr) {
                if (xhr.status === 400) {
                    alert('카테고리 삭제 중 오류가 발생했습니다.')
                } else {
                    alert('카테고리 삭제 중 오류가 발생했습니다.');
                }
            }
        });
    }
}