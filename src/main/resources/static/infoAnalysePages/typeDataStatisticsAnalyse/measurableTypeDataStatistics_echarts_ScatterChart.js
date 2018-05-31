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

    var querySQL=getQueryString("querySQL");

    var restBaseURL;
    var measurableTypeDisplayName="";
    if(measurableType=="FACT"){
        restBaseURL=APPLICATION_REST_SERVICE_CONTEXT+"typeDataStatisticsAnalyseService/factTypePropertiesDataList/";
        measurableTypeDisplayName="\u4e8b\u5b9e\u6570\u636e";
    }
    if(measurableType=="DIMENSION"){
        restBaseURL=APPLICATION_REST_SERVICE_CONTEXT+"typeDataStatisticsAnalyseService/dimensionTypePropertiesDataList/";
        measurableTypeDisplayName="\u7ef4\u5ea6\u6570\u636e";
    }
    if(measurableType=="RELATION"){
        restBaseURL=APPLICATION_REST_SERVICE_CONTEXT+"typeDataStatisticsAnalyseService/relationTypePropertiesDataList/";
        measurableTypeDisplayName="\u5173\u7cfb\u6570\u636e";
    }
    if(!restBaseURL){
        return;
    }
    if(graphHeight){
        document.getElementById('main').style.height=""+graphHeight+"px";
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

        var measurableDisplayName=measurableName;
        if(data.measurableAliasName){
            measurableDisplayName=data.measurableAliasName;
        }

        var chartDataSet=data.measurableValues;

        var dataSeries= [];
        var scatterPropertiesValueArray=scatterPropertyValues.split(",");

        var propertiesDataMap={};
        $.each(scatterPropertiesValueArray,function(index,scatterTypeValue){
            var currentDataSeries=generateChartSeriesData(scatterTypeValue);
            propertiesDataMap[scatterTypeValue]=[];
            currentDataSeries["data"]=propertiesDataMap[scatterTypeValue];
            dataSeries.push(currentDataSeries);
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

        var myChart = echarts.init(document.getElementById('main'));
        option = {
            title : {
                text: discoverSpaceName+" "+measurableTypeDisplayName+": "+measurableDisplayName,
                subtext: "数据总量:" +chartDataSet.length
            },
            grid: {
                left: '3%',
                right: '7%',
                bottom: '3%',
                containLabel: true
            },
            tooltip : {
                trigger: 'axis',
                showDelay : 0,
                formatter : function (params) {
                    if (params.value.length > 1) {
                        return params.seriesName + ' :<br/>'
                            + params.value[0] + ''
                            + params.value[1] + '';
                    }
                    else {
                        return params.seriesName + ' :<br/>'
                            + params.name + ' : '
                            + params.value + '';
                    }
                },
                axisPointer:{
                    show: true,
                    type : 'cross',
                    lineStyle: {
                        type : 'dashed',
                        width : 1
                    }
                }
            },
            toolbox: {
                feature: {
                    dataZoom: {},
                    //brush: {
                    //    type: [/*'rect', 'polygon', 'clear'*/]
                    // }
                }
            },
            // brush: {
            // },
            legend: {
                data:scatterPropertiesValueArray,
                left: 'center'
            },
            xAxis : [
                {
                    type : 'value',
                    name : xAxisPropertyDisplayName +" (x)",
                    nameLocation:'middle',
                    scale:true,
                    axisLabel : {
                        formatter: '{value}'
                    },
                    splitLine: {
                        show: true
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name : yAxisPropertyDisplayName +" (y)",
                    nameLocation:'middle',
                    scale:true,
                    axisLabel : {
                        formatter: '{value}'
                    },
                    splitLine: {
                        show: true
                    }
                }
            ],
            color:globalColorsLoopArray,
            series:dataSeries
        };
        myChart.setOption(option);
    });
});

function generateChartSeriesData(scatterDataType){
    var seriesData={
        name:scatterDataType,
        type:'scatter',
        markArea: {
            silent: true,
                itemStyle: {
                    normal: {
                    color: 'transparent',
                    borderWidth: 1,
                    borderType: 'dashed'
                }
            },
            data: [[{
                name: scatterDataType+'分布区间',
                xAxis: 'min',
                yAxis: 'min'
            }, {
                xAxis: 'max',
                yAxis: 'max'
            }]]
        },
        markPoint : {
            data : [
                {type : 'max', name: '最大值'},
                {type : 'min', name: '最小值'}
            ]
        },
        markLine : {
            lineStyle: {
                normal: {
                    type: 'solid'
                }
            },
            data : [
                {type : 'average', name: '平均值'}
                //, { xAxis: 160 }
            ]
        }
    };
    return seriesData;
}

























