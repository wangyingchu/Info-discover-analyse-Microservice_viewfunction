$(document).ready(function() {
    var discoverSpaceName=getQueryString("discoverSpace");
    var measurableName=getQueryString("measurableName");
    var graphHeight=getQueryString("graphHeight");
    var measurableType=getQueryString("measurableType");
    if(!discoverSpaceName){return;}
    if(!measurableName){return;}
    if(!measurableType){return;}

    var latProperty=getQueryString("latProperty");
    var lngProperty=getQueryString("lngProperty");
    var titleProperty=getQueryString("titleProperty");
    var descProperty=getQueryString("descProperty");
    if(!latProperty){return;}
    if(!lngProperty){return;}
    if(!titleProperty){return;}

    var querySQL=getQueryString("querySQL");

    var restBaseURL;
    var measurableTypeDisplayName="";
    if(measurableType=="FACT"){
        restBaseURL=APPLICATION_REST_SERVICE_CONTEXT+"/ws/typeDataStatisticsAnalyseService/factTypePropertiesDataList/";
        measurableTypeDisplayName="\u4e8b\u5b9e\u6570\u636e";
    }
    if(measurableType=="DIMENSION"){
        restBaseURL=APPLICATION_REST_SERVICE_CONTEXT+"/ws/typeDataStatisticsAnalyseService/dimensionTypePropertiesDataList/";
        measurableTypeDisplayName="\u7ef4\u5ea6\u6570\u636e";
    }
    if(measurableType=="RELATION"){
        restBaseURL=APPLICATION_REST_SERVICE_CONTEXT+"/ws/typeDataStatisticsAnalyseService/relationTypePropertiesDataList/";
        measurableTypeDisplayName="\u5173\u7cfb\u6570\u636e";
    }
    if(!restBaseURL){
        return;
    }
    var propertiesList=latProperty+","+lngProperty+","+titleProperty+","+descProperty;
    var restURL=restBaseURL+discoverSpaceName+"/"+measurableName+"/"+propertiesList+"/";

    if(graphHeight){
        document.getElementById('mapid').style.height=""+(Number(graphHeight)-40)+"px";
    }
    var restConfig={
        url: restURL
    };
    if(querySQL){
        restConfig["type"]="POST";
        restConfig["data"]={
            querySQL:querySQL
        };
    }
    $.ajax(restConfig).then(function(data) {
        var measurableDisplayName=measurableName;
        if(data.measurableAliasName){
            measurableDisplayName=data.measurableAliasName;
        }
        var chartDataSet=data.measurableValues;
        document.getElementById('graphTitle').innerHTML=discoverSpaceName+" "+measurableTypeDisplayName+": "+measurableDisplayName;
        document.getElementById('dataSizeText').innerHTML=chartDataSet.length;
        if(chartDataSet.length==0){
            return;
        }
        var centerLocation=chartDataSet[0];
        var centerLocationCoordinate=[
            getMeasurablePropertyNumberValue(centerLocation,latProperty),
            getMeasurablePropertyNumberValue(centerLocation,lngProperty)
        ];
        var mymap = L.map('mapid').setView(centerLocationCoordinate, 15);
        L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
            maxZoom: 18,
            id: 'mapbox.streets'
        }).addTo(mymap);

        $.each(chartDataSet,function(index,currentDataDetail){
            var centerLocationCoordinate=[
                getMeasurablePropertyNumberValue(currentDataDetail,latProperty),
                getMeasurablePropertyNumberValue(currentDataDetail,lngProperty)
            ];

            var mainDesc=getMeasurablePropertyOriginalValue(currentDataDetail,titleProperty);
            var secondDesc="";
            if(descProperty){
                secondDesc="<br/>"+getMeasurablePropertyOriginalValue(currentDataDetail,descProperty);
            }
            L.marker(centerLocationCoordinate).addTo(mymap).bindPopup("<b>"+mainDesc+"</b>"+secondDesc);
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