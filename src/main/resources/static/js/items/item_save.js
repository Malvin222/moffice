$(document).ready(function () {

    // 폼 제출 이벤트 처리
    $('#itemForm').on('submit', function (event) {
        event.preventDefault(); // 기본 제출 이벤트 방지

        // 입력 필드 값 가져오기
        const itemName = $('#itemName').val().trim();
        const categoryNo = $('#categoryNo').val().trim();
        const price = $('#price').val().trim();
        const barcodeNo = $('#barcodeNo').val().trim();
        const useYn = $('#useYn').val().trim();
        const $responseMessage = $('#responseMessage'); // 응답 메시지 DOM 요소



        // 입력 값 검증
        if (!itemName) {
            displayMessage($responseMessage, '카테고리명을 입력해주세요.', 'red');
            return;
        }
        if (!price){
            displayMessage($responseMessage, '가격을 입력해주세요','red');
            return;
        }
        if(!barcodeNo){
            displayMessage($responseMessage,'바코드 번호를 입력해주세요','red');
            return;
        }

        let barcodePattern = /^[A-Za-z0-9]+$/;
        if(!barcodePattern.test(barcodeNo)){
            displayMessage($responseMessage,'바코드 번호는 영어와 숫자만 가능합니다.','red');
            $('#barcodeNo').focus()
            return;
        }




        // AJAX 요청 데이터
        const requestData = {
            itemName: itemName,
            categoryNo: categoryNo,
            price: price,
            barcodeNo: barcodeNo,
            useYn: useYn
        };

        // 공통 AJAX 함수 호출
        ajaxRequest(
            '/api/items/item_save',
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
