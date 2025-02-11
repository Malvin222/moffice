$(document).ready(function () {
    // 폼 제출 이벤트 처리
    $('#categoryForm').on('submit', function (event) {
        event.preventDefault(); // 기본 제출 이벤트 방지

        // 입력 필드 값 가져오기
        const categoryName = $('#categoryName').val().trim();
        const useYn = $('#useYn').val().trim();
        const $responseMessage = $('#responseMessage'); // 응답 메시지 DOM 요소

        // 입력 값 검증
        if (!categoryName) {
            displayMessage($responseMessage, '카테고리명을 입력해주세요.', 'red');
            return;
        }

        // AJAX 요청 데이터
        const requestData = {
            categoryName: categoryName,
            useYn: useYn
        };

        // 공통 AJAX 함수 호출
        ajaxRequest(
            '/api/category/category_save',
            'POST',
            requestData,
            $responseMessage,
            function (response){
                alert("저장되었습니다.");
                window.location.href = document.referrer;
            }
        );
    });
});
