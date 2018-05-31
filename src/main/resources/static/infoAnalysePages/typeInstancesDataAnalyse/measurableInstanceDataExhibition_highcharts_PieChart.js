$(document).ready(function() {
    var discoverSpaceName=getQueryString("discoverSpace");
    var measurableId=getQueryString("measurableId");
    var graphHeight=getQueryString("graphHeight");
    var pieProperties=getQueryString("pieProperties");
    if(!discoverSpaceName){return;}
    if(!measurableId){return;}
    if(!pieProperties){return;}
    var restBaseURL=APPLICATION_REST_SERVICE_CONTEXT+"typeInstanceAnalyseService/typeInstanceDetailInfo/";
    if(graphHeight){
        document.getElementById('container').style.height=""+graphHeight+"px";
    }
    measurableId=measurableId.replace(/#/g, "%23");
    measurableId=measurableId.replace(/:/g, "%3a");
    var restURL=restBaseURL+discoverSpaceName+"/"+measurableId+"/";
    $.ajax({
        url: restURL
    }).then(function(data) {
        var discoverSpaceName=data.discoverSpaceName;
        var measurableName=data.measurableName;
        var measurableType=data.measurableType;
        var measurableTypeStr=measurableType.replace("TYPEKIND_","");
        var measurableIdStr=measurableId.replace("%23","#");
        measurableIdStr=measurableIdStr.replace("%3a",":");
        var piePropertiesNameArray=pieProperties.split(",");
        var dataSeries= [];

        $.each(piePropertiesNameArray,function(index,value){
            dataSeries.push({
                name:value,
                y: getMeasurablePropertyNumberValue(data,value),
                color:getCurrentGlobalColor(index)
            });
        });
        Highcharts.chart('container', {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text:discoverSpaceName+" "+measurableTypeStr+" "+measurableName+" "+measurableIdStr
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>:{point.y}<br> {point.percentage:.1f} %',
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    },
                    showInLegend: true
                }
            },
            series: [{
                name: '占比',
                colorByPoint: true,
                data:dataSeries
            }],
            credits: {
                enabled:false
            }
        });
    });
});