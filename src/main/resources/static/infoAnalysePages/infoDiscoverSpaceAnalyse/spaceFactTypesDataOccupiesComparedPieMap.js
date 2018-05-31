$(document).ready(function (){
    discoverSpaceName=getQueryString("discoverSpace");
    var graphHeight=getQueryString("graphHeight");
    if(!discoverSpaceName){return;}
    if(graphHeight){
        document.getElementById('container').style.height=""+graphHeight+"px";    }
    var restURL=APPLICATION_REST_SERVICE_CONTEXT+"infoDiscoverSpaceAnalyseService/spaceFactTypesDataCount/"+discoverSpaceName+"/";
    $.ajax({
        url: restURL
    }).then(function(data) {
        var factTypeData=[];
        $.each(data,function(index,value){
            var currentFactDataObj={};
            currentFactDataObj["name"]=value.typeName;
            currentFactDataObj["y"]=value.typeDataRecordCount;
            factTypeData.push(currentFactDataObj);
        });
        // Build the chart
        Highcharts.chart('container', {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: '信息发现空间事实类型数据占比'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b><br/> 数据量: <b>{point.y}</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                name: '数据量占比',
                colorByPoint: true,
                data: factTypeData
            }],
            exporting: {
                enabled:false
            },
            credits: {
                enabled:false
            }
        });
    });
});