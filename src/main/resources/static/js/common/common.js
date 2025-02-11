
/**
 * 공통 AJAX 요청 함수
 * @param {string} url - 요청할 URL
 * @param {string} method - HTTP 메서드 (GET, POST 등)
 * @param {Object} data - 요청 데이터
 * @param {Function} $responseMessage - $responseMessage
 */
function ajaxRequest(url, method, data, $responseMessage, successCallback) {
    $.ajax({
        url: url,
        type: method,
        contentType: 'application/json',
        dataType: 'text',
        data: JSON.stringify(data),
        success: function(response, status, xhr) {
            const statusCode = xhr.status;
            console.log("HTTP 상태 코드: ", statusCode);

            if (statusCode === 201) {
                console.log("성공 응답: ", response);
                displayMessage($responseMessage, response, 'green');
                if (typeof successCallback === 'function'){
                    successCallback(response);
                }
            } else {
                console.warn("예상치 못한 성공 상태 코드: ", statusCode);
                displayMessage($responseMessage, '예기치 못한 성공 응답입니다.', 'orange');
            }
        },
        error: function (xhr, status, error) {
                console.log("HTTP 상태 코드: ", xhr.status);
                console.error("오류 응답: ", xhr.responseText);

                // 서버에서 전달된 메시지를 직접 사용
                let errorMessage = xhr.responseText;
                try {
                    const responseObj = JSON.parse(xhr.responseText);
                    errorMessage = responseObj.message || responseObj;
                } catch (e) {
                    // JSON 파싱에 실패하면 원본 텍스트 사용
                    errorMessage = xhr.responseText;
                }
                displayMessage($responseMessage, errorMessage, 'red');
        }
    });
}

/**
 * 메시지 출력
 * @param {jQuery} $element - 메시지를 표시할 DOM 요소
 * @param {string} message - 출력할 메시지
 * @param {string} color - 메시지 색상 (green, red 등)
 */
function displayMessage($element, message, color) {
    $element
        .css({ display: 'block', color: color })
        .text(message);
}

/**
 * 입력 필드 초기화
 * @param {string} selector - 초기화할 입력 필드의 jQuery 셀렉터
 */
function clearInputField(selector) {
    $(selector).val('');
}

/**
 * 검색 조건을 포함하여 엑셀 다운로드를 수행하는 함수
 * @param {string} downloadUrl - 엑셀 다운로드 API URL (예: '/api/items/excel_download')
 * @param {string} formId - 검색 폼의 ID (기본값: 'searchForm')
 */
function downloadExcelWithSearch(downloadUrl, formId = 'searchForm') {
    // 검색 폼의 모든 입력 요소를 가져옴
    const $form = $(`#${formId}`);
    if (!$form.length) {
        console.warn(`Form with id '${formId}' not found`);
    }

    // 폼 데이터를 URLSearchParams 객체로 변환
    const formData = new FormData($form[0]);
    const params = new URLSearchParams();

    // 값이 있는 파라미터만 추가
    for (const [key, value] of formData.entries()) {
        if (value) {
            params.append(key, value);
        }
    }

    // 최종 URL 생성 및 다운로드 실행
    const finalUrl = downloadUrl + (params.toString() ? '?' + params.toString() : '');
    window.location.href = finalUrl;
}

/*function downloadExcel() {
    // 검색 폼에서 현재 입력된 값들을 가져옴
    const itemName = $('input[name="itemName"]').val();
    const categoryName = $('input[name="categoryName"]').val();

    // 검색 조건을 쿼리 파라미터로 구성
    const params = new URLSearchParams();
    if (itemName) params.append('itemName', itemName);
    if (categoryName) params.append('categoryName', categoryName);

    // 검색 조건을 포함한 URL 생성
    const downloadUrl = '/api/items/excel_download?' + params.toString();

    // 페이지 이동
    window.location.href = downloadUrl; */

