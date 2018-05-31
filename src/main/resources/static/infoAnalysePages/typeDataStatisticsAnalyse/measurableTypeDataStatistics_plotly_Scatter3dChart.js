$(document).ready(function() {
    var discoverSpaceName=getQueryString("discoverSpace");
    var measurableName=getQueryString("measurableName");
    var graphHeight=getQueryString("graphHeight");
    var graphWidth=getQueryString("graphWidth");
    var measurableType=getQueryString("measurableType");
    if(!discoverSpaceName){return;}
    if(!measurableName){return;}
    if(!measurableType){return;}

    var xAxisProperty=getQueryString("x");
    var yAxisProperty=getQueryString("y");
    var zAxisProperty=getQueryString("z");

    var scatterProperty=getQueryString("scatter");
    var scatterPropertyValues=getUTF8UrlParam("scatterValues");
    var filterProperty=getQueryString("filter");
    var filterValue=getUTF8UrlParam("filterValue");

    if(!xAxisProperty){return;}
    if(!yAxisProperty){return;}
    if(!scatterProperty){return;}
    if(!scatterPropertyValues){return;}
    if(!zAxisProperty){return;}

    var querySQL=getQueryString("querySQL");

    var restBaseURL;
    var measurableTypeDisplayName="";
    if(measurableType=="FACT"){
        restBaseURL=APPLICATION_REST_SERVICE_CONTEXT+"typeDataStatisticsAnalyseService/factTypePropertiesBriefJSON/";
        measurableTypeDisplayName="\u4e8b\u5b9e\u6570\u636e";
    }
    if(measurableType=="DIMENSION"){
        restBaseURL=APPLICATION_REST_SERVICE_CONTEXT+"typeDataStatisticsAnalyseService/dimensionTypePropertiesBriefJSON/";
        measurableTypeDisplayName="\u7ef4\u5ea6\u6570\u636e";
    }
    if(measurableType=="RELATION"){
        restBaseURL=APPLICATION_REST_SERVICE_CONTEXT+"typeDataStatisticsAnalyseService/relationTypePropertiesBriefJSON/";
        measurableTypeDisplayName="\u5173\u7cfb\u6570\u636e";
    }
    if(!restBaseURL){
        return;
    }
    var csvProperties=xAxisProperty;
    if(yAxisProperty!=xAxisProperty){
        csvProperties=xAxisProperty+","+yAxisProperty
    }
    if(scatterProperty!=xAxisProperty&&scatterProperty!=yAxisProperty){
        csvProperties=csvProperties+","+scatterProperty;
    }
    if(zAxisProperty!=xAxisProperty&&zAxisProperty!=yAxisProperty&&zAxisProperty!=scatterProperty){
        csvProperties=csvProperties+","+zAxisProperty;
    }
    if(filterProperty){
        if(filterProperty!=xAxisProperty&&filterProperty!=yAxisProperty&&filterProperty!=scatterProperty&&filterProperty!=zAxisProperty){
            csvProperties=csvProperties+","+filterProperty;
        }
    }

    var layoutViewHeight=$(document).height()-20;
    var layoutViewWidth=$(window).width()-20;
    if(graphHeight){
        layoutViewHeight=Number(graphHeight)-40;
    }
    if(graphWidth){
        layoutViewWidth=Number(graphWidth);
    }
    var restURL=restBaseURL+discoverSpaceName+"/"+measurableName+"/"+csvProperties+"/";

    var scatterPropertiesValueArray=scatterPropertyValues.split(",");

    var plotlyChartRenderFunction=function(err, data) {
        var rows =data.propertyRowData;

        var propertyAliasNamesMap=data.propertiesAliasNameMap;
        var xAxisPropertyDisplayName=xAxisProperty;
        var yAxisPropertyDisplayName=yAxisProperty;
        var zAxisPropertyDisplayName=zAxisProperty;
        if(propertyAliasNamesMap){
            if(propertyAliasNamesMap[xAxisProperty]){
                xAxisPropertyDisplayName=propertyAliasNamesMap[xAxisProperty];
            }
            if(propertyAliasNamesMap[yAxisProperty]){
                yAxisPropertyDisplayName=propertyAliasNamesMap[yAxisProperty];
            }
            if(propertyAliasNamesMap[zAxisProperty]){
                zAxisPropertyDisplayName=propertyAliasNamesMap[zAxisProperty];
            }
        }

        var measurableDisplayName=measurableName;
        if(data.measurableAliasName){
            measurableDisplayName=data.measurableAliasName;
        }

        document.getElementById('graphTitle').innerHTML = discoverSpaceName + " " + measurableTypeDisplayName + ": " + measurableDisplayName;
        document.getElementById('dataSizeText').innerHTML = rows.length;
        function unpack(rows, key) {
            return rows.map(function (row) {
                return row[key];
            });
        }

        var data = scatterPropertiesValueArray.map(function (scatterValue) {
            var rowsFiltered = rows.filter(function (row) {
                if (filterProperty && filterValue) {
                    return (row[scatterProperty] === scatterValue) && (+row[filterProperty] == filterValue);
                } else {
                    return (row[scatterProperty] === scatterValue);
                }
            });
            var dataConfig = {
                name: scatterValue,
                opacity: 0.6,
                type: 'scatter3d',
                marker: {
                    color: globalColorsLoopArray[scatterPropertiesValueArray.indexOf(scatterValue) + 9],
                    size: 5,
                    symbol: 'circle',
                    line: {
                        color: 'rgb(204, 204, 204)',
                        width: 1
                    }
                },
                x: unpack(rowsFiltered, xAxisProperty),
                y: unpack(rowsFiltered, yAxisProperty),
                z: unpack(rowsFiltered, zAxisProperty)
                //,mode:'markers' //使用　marker mode不会渲染连接线
            };
            return dataConfig;
        });
        var layout = {
            margin: {t: 20, l: 20, r: 10},
            hovermode: 'closest',
            autosize: true,
            height: 1000,
            scene: {
                xaxis: {
                    type: 'linear',
                    zeroline: true,
                    title: xAxisPropertyDisplayName + ' (x)'
                },
                yaxis: {
                    type: 'linear',
                    zeroline: true,
                    title: yAxisPropertyDisplayName + ' (y)'
                },
                zaxis: {
                    type: 'linear',
                    zeroline: true,
                    title: zAxisPropertyDisplayName + ' (z)'
                },
                aspectratio: {
                    x: 2,
                    y: 1,
                    z: 1
                },
                camera: {
                    center: {
                        x: 0,
                        y: 0,
                        z: 0
                    },
                    eye: {
                        x: 1,
                        y: 2,
                        z: 2
                    },
                    up: {
                        x: 0,
                        y: 0,
                        z: 1
                    }
                }
            },
            height: layoutViewHeight,
            width: layoutViewWidth
        };
        Plotly.plot('my-graph', data, layout, {showLink: false});
    };
    if(querySQL){
        Plotly.d3.json(restURL)
            .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            .post("querySQL="+querySQL, plotlyChartRenderFunction);
    }else{
        Plotly.d3.json(restURL, plotlyChartRenderFunction);
    }
});

