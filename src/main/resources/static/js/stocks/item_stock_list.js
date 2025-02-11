let receivingList = [];
$(document).ready(function () {

    // 숫자만 입력 가능하도록 설정
    $(document).on('input', 'input[name="receiving_count"], input[name="receiving_price"]', function (){
        this.value = this.value.replace(/[^0-9]/g,'');
    })

    // 입고처리 버튼 클릭 이벤트
    $('#saveReceiving').click(function (){
        receivingList = [];

        $('tbody tr').each(function (){
            const itemNo = $(this).find('td.item-no').text().trim();
            const receivingCount = $(this).find('input[name="receiving_count"]').val();
            const receivingPrice = $(this).find('input[name="receiving_price"]').val();
            const stockCount = $(this).find('td.stock-count').text().trim();

            if (itemNo && receivingCount) {
                receivingList.push({
                    itemNo: itemNo,
                    receivingCount: receivingCount,
                    receivingPrice: receivingPrice,
                    stockCount: stockCount
                });
            }
        });
        console.log("receivingList:", receivingList);
        console.log("receivingList JSON:", JSON.stringify(receivingList, null, 2));
        //입고할 데이터가 있는 경우 서버전송
        if (receivingList.length > 0 ){
            funSaveReceiving();
        } else {
            alert('입고할 상품의 수량과 가격을 입력해주세요.');
        }
    });
});

function funSaveReceiving(){
    $.ajax({
        url: '/api/stocks/item_stock_save',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(receivingList),
        success: function (response) {
            alert('입고처리가 완료 되었습니다.');
            location.reload();
        },
        error: function (xhr, status, error){
            alert('입고처리 중 오류가 발생했습니다.');
            console.error('Error:',error);
        }
    })
}