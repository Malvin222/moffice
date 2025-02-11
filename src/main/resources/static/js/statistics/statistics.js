let statisticsChart = null;

$(document).ready(function() {
    //초기 데이터 로드 (일별 통계)
    loadStatistics('daily');

    //범례 버튼 클릭 이벤트
    $('.period-btn').click(function (){
        $('.period-btn').removeClass('active');
        $(this).addClass('active');

        const periodType = $(this).data('period');
        loadStatistics(periodType);
    });
});

function loadStatistics(periodType){
    $.ajax({
        url:'api/statistics_with',
        method:'GET',
        data: {periodType:periodType},
        beforeSend:function(){
        $('.chart-container').append('<div class="loading">로딩중...</div>')
    },
        success:function (response){
            $('.loading').remove();
            updateChart(response,periodType);
        },
        error: function (xhr, status, error){
            $('.loading').remove();
            $('.chart-container').append('<div class="error">데이터로드 실패</div>')
        }
    });
}

function updateChart(data, periodType){
    $('.error').remove();
    //기존 차트가 있으면 제거
    if(statisticsChart){
        statisticsChart.destroy();
    }

    //차트설정
    const ctx = document.getElementById("statisticsChart").getContext("2d")

    //타이틀 설정
    const titleText = {
        'daily':'일별 입고 현황',
        'monthly':'월별 입고 현황',
        'yearly':'연도별 입고 현황',
    }[periodType];

    statisticsChart = new Chart(ctx,{
        type:"bar",
        data: {
        labels: data.map(statistics => statistics.period),
            datasets: [{
                label:'입고 수량',
                data: data.map(statistics => statistics.totalCount),
                backgroundColor: 'rgba(54, 162, 235, 0.5)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options:{
            responsive:true,
            maintainAspectRatio:false,
            plugins:{
                title:{
                    display:true,
                    text:titleText,
                    fount:{
                        size:16
                    }
                },
                legend:{
                    position:'top'
                }
            },
            scales:{
                y:{
                    beginAtZero:true,
                    ticks:{
                        callback:function (value){
                            return value.toLocaleString() +"개";
                        }
                    }
                }
            },
            animation:{
                duration:500
            },
            onClick:function (e,elements){
                if(elements.length()>0){
                    const index = elements[0].index;
                    const period = data[index].period;
                    const count = data[index].totalCount;
                    alert(`기간: ${period}\n 수량: ${count.toLocaleString()}개`);
                }
            }
        }
    });
}