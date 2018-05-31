$(document).ready(function() {
    var discoverSpaceName=getQueryString("discoverSpace");
    var measurableName=getQueryString("measurableName");
    var graphHeight=getQueryString("graphHeight");
    var measurableType=getQueryString("measurableType");
    if(!discoverSpaceName){return;}
    if(!measurableName){return;}
    if(!measurableType){return;}

    var xAxisProperty=getQueryString("x");
    var yAxisProperty=getQueryString("y");
    var scatterProperty=getQueryString("scatter");
    var scatterPropertyValues=getUTF8UrlParam("values");

    if(!xAxisProperty){return;}
    if(!yAxisProperty){return;}
    if(!scatterProperty){return;}
    if(!scatterPropertyValues){return;}

    var xRulerValue=getQueryString("xRuler");
    var yRulerValue=getQueryString("yRuler");
    var querySQL=getQueryString("querySQL");

    var restBaseURL;
    var measurableTypeDisplayName="";
    if(measurableType=="FACT"){
        restBaseURL=APPLICATION_REST_SERVICE_CONTEXT+"typeDataStatisticsAnalyseService/factTypePropertiesDataList/";
        measurableTypeDisplayName="事实数据";
    }
    if(measurableType=="DIMENSION"){
        restBaseURL=APPLICATION_REST_SERVICE_CONTEXT+"typeDataStatisticsAnalyseService/dimensionTypePropertiesDataList/";
        measurableTypeDisplayName="维度数据";
    }
    if(measurableType=="RELATION"){
        restBaseURL=APPLICATION_REST_SERVICE_CONTEXT+"typeDataStatisticsAnalyseService/relationTypePropertiesDataList/";
        measurableTypeDisplayName="关系数据";
    }
    if(!restBaseURL){
        return;
    }
    if(graphHeight){
        document.getElementById('container').style.height=""+graphHeight+"px";
    }
    var propertiesList=scatterProperty+","+xAxisProperty+","+yAxisProperty;
    var restURL=restBaseURL+discoverSpaceName+"/"+measurableName+"/"+propertiesList+"/";

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
        var propertyAliasNamesMap=data.propertiesAliasNameMap;
        var xAxisPropertyDisplayName=xAxisProperty;
        var yAxisPropertyDisplayName=yAxisProperty;
        var scatterPropertyDisplayName=scatterProperty;
        if(propertyAliasNamesMap){
            if(propertyAliasNamesMap[xAxisProperty]){
                xAxisPropertyDisplayName=propertyAliasNamesMap[xAxisProperty];
            }
            if(propertyAliasNamesMap[yAxisProperty]){
                yAxisPropertyDisplayName=propertyAliasNamesMap[yAxisProperty];
            }
            if(propertyAliasNamesMap[scatterProperty]){
                scatterPropertyDisplayName=propertyAliasNamesMap[scatterProperty];
            }
        }

        var chartDataSet=data.measurableValues;
        var dataSeries= [];
        var scatterPropertiesValueArray= scatterPropertyValues.split(",");

        var propertiesDataMap={};
        $.each(scatterPropertiesValueArray,function(index,value){
            propertiesDataMap[value]=[];
            dataSeries.push({
                data: propertiesDataMap[value],
                type: "scatter",
                name:value,
                color:getCurrentGlobalColor(index)
            });
        });

        var currentDataDetail=null;
        for(var i=0;i<chartDataSet.length;i++){
            currentDataDetail=chartDataSet[i];
            var xValue=getMeasurablePropertyNumberValue(currentDataDetail,xAxisProperty);
            var yValue=getMeasurablePropertyNumberValue(currentDataDetail,yAxisProperty);
            var scatterValue=getMeasurablePropertyOriginalValue(currentDataDetail,scatterProperty);
            if($.inArray(scatterValue, scatterPropertiesValueArray)!=-1){
                propertiesDataMap[scatterValue].push([xValue,yValue]);
            }
        }

        var measurableDisplayName=measurableName;
        if(data.measurableAliasName){
            measurableDisplayName=data.measurableAliasName;
        }

        var chartConfig={
            chart: {
                type: 'scatter',
                zoomType: 'xy'
            },
            title: {
                text: discoverSpaceName+" "+measurableTypeDisplayName+": "+measurableDisplayName
            },
            subtitle: {
                text: "数据总量:" +chartDataSet.length
            },
            xAxis: {
                title: {
                    enabled: true,
                    text: xAxisPropertyDisplayName+" (x)"
                },
                startOnTick: true,
                endOnTick: true,
                showLastLabel: true
            },
            yAxis: {
                title: {
                    text: yAxisPropertyDisplayName+" (y)"
                }
            },
            legend: {
                layout: 'vertical',
                align: 'left',
                verticalAlign: 'top',
                x: 100,
                y: 70,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF',
                borderWidth: 1
            },
            plotOptions: {
                scatter: {
                    marker: {
                        radius: 6,
                        states: {
                            hover: {
                                enabled: true,
                                lineColor: 'rgb(100,100,100)'
                            }
                        }
                    },
                    states: {
                        hover: {
                            marker: {
                                enabled: false
                            }
                        }
                    },
                    tooltip: {
                        headerFormat: '<b>{series.name}</b><br>',
                        pointFormat: '<b>'+xAxisPropertyDisplayName+'(x):</b> {point.x}, <br><b>'+yAxisPropertyDisplayName+' (y)</b>: {point.y}'
                    }
                }
            },
            series:dataSeries,
            credits: {
                enabled:false
            }
        };

        if(xRulerValue){
            chartConfig["xAxis"]["plotLines"]= [{
                color: 'black',
                dashStyle: 'dot',
                width: 2,
                value: Number(xRulerValue),
                label: {
                    rotation: 0,
                    y: 15,
                    style: {
                        fontStyle: 'italic'
                    },
                    text: xAxisPropertyDisplayName +'='+Number(xRulerValue)
                },
                zIndex: 3
            }];
        }

        if(yRulerValue){
            chartConfig["yAxis"]["plotLines"]= [{
                color: 'black',
                dashStyle: 'dot',
                width: 2,
                value: Number(yRulerValue),
                label: {
                    align: 'right',
                    style: {
                        fontStyle: 'italic'
                    },
                    text: yAxisPropertyDisplayName +'='+Number(yRulerValue),
                    x: -10
                },
                zIndex: 3
            }];
        }

        Highcharts.chart('container',chartConfig);
    });
});