$(document).ready(function () {
    let isEditing = false;
    let originalValues = {};

    // 원래 값 저장
    function saveOriginalValues() {
        originalValues = {
            indexNo: $("#indexNo").val(),
            categoryName: $("#categoryName").val(),
            useYn: $("#useYn").val()
        };
    }

    // 원래 값들로 되돌리는 함수
    function restoreOriginalValues() {
        $("#categoryName").val(originalValues.categoryName);
        $("#useYn").val(originalValues.useYn);
    }

    // 수정 모드로 전환하는 함수
    function enableEditMode() {
        isEditing = true;
        $("#headerName").text("분류 수정")
        $("#editButton").text("저장").removeClass("btn-primary").addClass("btn-success");
        $("#cancelButton").text("취소");
        $("#categoryName").removeAttr("readonly");
        $("#useYn").prop("disabled", false)
        saveOriginalValues();
    }

    // 읽기 모드로 전환하는 함수
    function disableEditMode() {
        isEditing = false;
        $("#headerName").text("분류 상세");
        $("#editButton").text("수정").removeClass("btn-success").addClass("btn-primary")
        $("#cancelButton").text("뒤로가기");
        $("#categoryName").attr("readonly", "readonly");
        $("#useYn").prop("disabled", true)
    }

    // 수정/저장 버튼 클릭 이벤트
    $("#editButton").click(function () {
        if (!isEditing) {
            // 수정 모드로 전환
            enableEditMode();
        } else {
            // 저장 처리
            if (confirm("변경사항을 저장하시겠습니까?")) {
                // 폼 데이터 수집
                const formData = {
                    indexNo: $("#indexNo").val(),
                    categoryName: $("#categoryName").val(),
                    useYn: $("#useYn").val()
                };

                // AJAX 요청
                $.ajax({
                    url: '/api/category/category_update',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(formData),
                    success: function (response) {
                        alert("저장되었습니다.");
                        disableEditMode();
                        location.reload();
                    },
                    error: function (xhr, status, error) {
                        alert("저장 중 오류가 발생했습니다." + error);
                    }
                });
            }
        }
    });

    // 취소 버튼 클릭 이벤트
    $("#cancelButton").click(function () {
        if (!isEditing) {
            history.back();
        } else {
            if (confirm("변경사항을 취소하시겠습니까?")) {
                restoreOriginalValues();
                disableEditMode();
            }
            // 초기 상태 설정
            disableEditMode();
        }
    })
});

