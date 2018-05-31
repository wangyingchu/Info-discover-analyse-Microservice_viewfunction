$(document).ready(function() {
    var discoverSpaceName=getQueryString("discoverSpace");
    var measurableIds=getQueryString("measurableIds");
    var graphHeight=getQueryString("graphHeight");
    var lineProperties=getQueryString("lineProperties");
    var chartType=getQueryString("chartType");
    if(!discoverSpaceName){return;}
    if(!measurableIds){return;}
    if(!lineProperties){return;}
    var restBaseURL=APPLICATION_REST_SERVICE_CONTEXT+"typeInstanceAnalyseService/typeInstancesDetailInfo/";
    if(graphHeight){
        document.getElementById('container').style.height=""+graphHeight+"px";
    }
    measurableIds=measurableIds.replace(/#/g, "%23");
    measurableIds=measurableIds.replace(/:/g, "%3a");
    var restURL=restBaseURL+discoverSpaceName+"/"+measurableIds+"/";
    $.ajax({
        url: restURL
    }).then(function(data) {
        var linePropertiesNameArray=lineProperties.split(",");
        var dataSeries= [];
        $.each(data,function(index,currentData){
            var seriesData={};
            seriesData.name=currentData.measurableName+"-"+currentData.measurableId;
            seriesData.pointPlacement='on';
            seriesData.color=getCurrentGlobalColor(index);
            var data=[];
            $.each(linePropertiesNameArray,function(index,value){
                data.push(getMeasurablePropertyNumberValue(currentData,value));
            });
            seriesData.data=data;
            dataSeries.push(seriesData);
        });
        var isPolarChart=false;
        if(chartType){
            if(chartType=="polar"){
                isPolarChart=true;
            }
        }
        Highcharts.chart('container', {
            chart: {
                polar: isPolarChart,
                type: 'line'
            },
            title: {
                text: discoverSpaceName+" 数据比较",
                x: -80
            },
            pane: {
                size: '80%'
            },
            xAxis: {
                categories: linePropertiesNameArray,
                tickmarkPlacement: 'on',
                lineWidth: 0,
                title: {
                    enabled: false
                }
            },
            yAxis: {
                gridLineInterpolation: 'polygon',
                lineWidth: 0,
                //min: 0,
                title: {
                    enabled: false
                }
            },
            tooltip: {
                //split: false,
                //shared: true,
                pointFormat: '<span style="color:{series.color}">{series.name}: <b>{point.y:,.0f}</b><br/>'
            },
            legend: {
                align: 'right',
                verticalAlign: 'top',
                y: 70,
                floating: true,
                layout: 'vertical'
            },
            series:dataSeries,
            credits: {
                enabled:false
            }
        });
    });
});















































/*
 Highcharts.chart('container', {
 chart: {
 plotBackgroundColor: null,
 plotBorderWidth: null,
 plotShadow: false,
 type: 'pie'
 },
 title: {
 text: 'Browser market shares January, 2015 to May, 2015'
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
 format: '<b>{point.name}</b>: {point.percentage:.1f} %',
 style: {
 color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
 }
 }
 }
 },
 series: [{
 name: 'Brands',
 colorByPoint: true,
 data: [{
 name: 'Microsoft Internet Explorer',
 y: 56.33,
 color:'#CE0000'
 }, {
 name: 'Chrome',
 y: 24.03,
 sliced: true,
 selected: true
 }, {
 name: 'Firefox',
 y: 10.38
 }, {
 name: 'Safari',
 y: 4.77
 }, {
 name: 'Opera',
 y: 0.91
 }, {
 name: 'Proprietary or Undetectable',
 y: 0.2
 }]
 }]
 });

 */