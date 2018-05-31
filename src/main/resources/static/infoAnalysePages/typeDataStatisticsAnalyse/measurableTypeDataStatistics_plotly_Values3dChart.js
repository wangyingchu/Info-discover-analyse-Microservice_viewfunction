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

    if(!xAxisProperty){return;}
    if(!yAxisProperty){return;}
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
    var layoutViewHeight=$(document).height()-60;
    var layoutViewWidth=$(window).width()-20;

    if(graphHeight){
        layoutViewHeight=Number(graphHeight)-40;
    }
    if(graphWidth){
        layoutViewWidth=Number(graphWidth);
    }
    var restURL=restBaseURL+discoverSpaceName+"/"+measurableName+"/"+xAxisProperty+","+yAxisProperty+","+zAxisProperty+"/";

    var plotlyChartRenderFunction=function(err, data){
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

        document.getElementById('graphTitle').innerHTML=discoverSpaceName+" "+measurableTypeDisplayName+": "+measurableDisplayName;
        document.getElementById('dataSizeText').innerHTML=rows.length;
        function unpack(rows, key) {
            return rows.map(function(row) { return row[key]; });
        }
        var data = [{
            name:measurableDisplayName,
            x: unpack(rows, xAxisProperty),
            y: unpack(rows, yAxisProperty),
            z: unpack(rows, zAxisProperty),
            mode: 'markers',
            type: 'scatter3d',
            marker: {
                //color: 'rgb(23, 190, 207)',
                color: getCurrentGlobalColor(0),
                size: 4,
                symbol: 'circle',
                line: {
                    color: 'rgb(204, 204, 204)',
                    width: 1
                }
            }
        },{
            name:measurableDisplayName,
            alphahull: 7,
            opacity: 0.05,
            type: 'mesh3d',
            color: getCurrentGlobalColor(1),
            x: unpack(rows, xAxisProperty),
            y: unpack(rows, yAxisProperty),
            z: unpack(rows, zAxisProperty)
        }];

        var layout = {
            autosize: true,
            margin: {t: 20,l:20,r:10},
            scene: {
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
                        x: 1.25,
                        y: 1.25,
                        z: 1.25
                    },
                    up: {
                        x: 0,
                        y: 0,
                        z: 1
                    }
                },
                xaxis: {
                    type: 'linear',
                    zeroline: true,
                    title:xAxisPropertyDisplayName+' (x)'
                },
                yaxis: {
                    type: 'linear',
                    zeroline: true,
                    title:yAxisPropertyDisplayName+' (y)'
                },
                zaxis: {
                    type: 'linear',
                    zeroline: true,
                    title:zAxisPropertyDisplayName+' (z)'
                }
            },
            height: layoutViewHeight,
            width: layoutViewWidth
        };
        Plotly.newPlot('myDiv', data, layout);
    };
    if(querySQL){
        Plotly.d3.json(restURL)
            .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            .post("querySQL="+querySQL, plotlyChartRenderFunction);
    }else{
        Plotly.d3.json(restURL, plotlyChartRenderFunction);
    }
});

