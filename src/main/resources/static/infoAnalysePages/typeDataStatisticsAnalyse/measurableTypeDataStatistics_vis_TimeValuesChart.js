var vis_graph2d_groupDataClassNamePerfix="vis_graph2d_groupDataClassName";
var vis_graph2d_groupLabelClassNamePerfix="vis_graph2d_groupLabelClassName";
$(document).ready(function() {
    var discoverSpaceName=getQueryString("discoverSpace");
    var measurableName=getQueryString("measurableName");
    var graphHeight=getQueryString("graphHeight");
    var measurableType=getQueryString("measurableType");
    if(!discoverSpaceName){return;}
    if(!measurableName){return;}
    if(!measurableType){return;}

    var datePropertyName=getQueryString("dateProperty");
    if(!datePropertyName){return;}
    var valuePropertiesName=getQueryString("valueProperties");
    if(!valuePropertiesName){return;}

    var barPropertiesName=getQueryString("barProperties");
    var barChartPropertiesNameArray;
    if(barPropertiesName){
        barChartPropertiesNameArray=barPropertiesName.split(",");
    }

    var startDate=getQueryString("start");
    var endDate=getQueryString("end");
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

    var propertiesList=valuePropertiesName+","+datePropertyName;
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

        var measurableDisplayName=measurableName;
        if(data.measurableAliasName){
            measurableDisplayName=data.measurableAliasName;
        }
        var propertyAliasNamesMap=data.propertiesAliasNameMap;

        var chartDataSet=data.measurableValues;
        document.getElementById('graphTitle').innerHTML=discoverSpaceName+" "+measurableTypeDisplayName+": "+measurableDisplayName;
        document.getElementById('dataSizeText').innerHTML=chartDataSet.length;

        var measurableTypePropertiesNameArray=valuePropertiesName.split(",");
        var groups = new vis.DataSet();

        $.each(measurableTypePropertiesNameArray,function(index,value){
            var isBarChart=false;
            if(barChartPropertiesNameArray){
                if($.inArray(value, barChartPropertiesNameArray)!=-1){
                    isBarChart=true;
                }
            }
            var propertyDisplayName=value;
            if(propertyAliasNamesMap[value]){
                propertyDisplayName=propertyAliasNamesMap[value];
            }
            var currentGroupOption=generateGroupConfiguration(propertyDisplayName,measurableTypePropertiesNameArray.length,index,isBarChart);
            groups.add(currentGroupOption);
        });

        var items = [];

        for(var i=0;i<chartDataSet.length;i++){
            var currentDataDetail=chartDataSet[i];
            var currentDate= new Date(getMeasurablePropertyNumberValue(currentDataDetail,datePropertyName));
            $.each(measurableTypePropertiesNameArray,function(index,value){
                var currentDataValue=getMeasurablePropertyNumberValue(currentDataDetail,value);
                var currentGroupData={
                    x:currentDate,
                    y: currentDataValue,
                    group: index,
                    label:{
                        content:currentDataValue,
                        className: vis_graph2d_groupLabelClassNamePerfix+index,
                        xOffset:8,
                        yOffset: 0
                    }
                };
                items.push(currentGroupData);
            });
        }
        var dataSet = new vis.DataSet(items);
        var options = {
            dataAxis:{
                showMinorLabels:true,
                icons:false
            },
            legend: {
                enabled:true
            }
        };
        if(graphHeight){
            options['height']=""+(Number(graphHeight)-40)+"px";
        }
        if(startDate){
            options["start"] = startDate;
        }
        if(endDate){
            options["end"] = endDate;
        }
        var container = document.getElementById('visualization');
        var graph2d = new vis.Graph2d(container, dataSet, groups, options);
    });
});


function generateGroupConfiguration(groupName,groupsTotalNumber,groupIndex,isBarData){
    var groupDataClassName="."+vis_graph2d_groupDataClassNamePerfix+groupIndex;
    var groupLabelClassName="."+vis_graph2d_groupLabelClassNamePerfix+groupIndex;
    var headElements=$("head").children("style");
    //check if class already exist
    var classAlreadyExistFlag=false;
    $.each(headElements,function(index,value){
        var styleInnerHTML=value.innerHTML;
        if(styleInnerHTML.indexOf(groupDataClassName) >= 0){
            classAlreadyExistFlag=true;
            return true;
        }
    });
    //create class if not exist
    if(!classAlreadyExistFlag){
        var currentGroupBaseColorIndex;
        if(groupIndex<globalColorsLoopArray.length){
            currentGroupBaseColorIndex=groupIndex;
        }else{
            currentGroupBaseColorIndex=groupIndex%globalColorsLoopArray.length;
        }
        var currentGroupBaseColor=globalColorsLoopArray[currentGroupBaseColorIndex];
        $("<style>")
            .prop("type", "text/css")
            .html(""+
                groupLabelClassName+" {\
                stroke: "+currentGroupBaseColor+";\
                stroke-width:1px;\
                font-size:0.7em;\
                }").appendTo("head");
        $("<style>")
            .prop("type", "text/css")
            .html(""+
                groupDataClassName+" {\
                fill: "+currentGroupBaseColor+";\
                fill-opacity: 0 ;\
                stroke-width: 2px;\
                stroke: "+currentGroupBaseColor+";\
                }").appendTo("head");
    }
    var groupOption={
        id: groupIndex,
        content: groupName,
        className:vis_graph2d_groupDataClassNamePerfix+groupIndex,
        options: {
            drawPoints: {
                style: 'square' // square, circle
            }
        }};
    groupOption.options={};
    if(isBarData){
        groupOption.options.style= 'bar';
    }else{
        groupOption.options.style= 'line';
    }
    groupOption.options.drawPoints={};
    if (groupIndex == 0) {
        if(groupsTotalNumber<=5) {
            groupOption.options.shaded={};
            groupOption.options.shaded.orientation = 'bottom';
        }else{
            groupOption.options.shaded=false;
        }
    } else if (groupIndex == 1) {
        if(groupsTotalNumber<=5) {
            groupOption.options.shaded={};
            groupOption.options.shaded.orientation = 'top';
        }else{
            groupOption.options.shaded=false;
        }
    } else {
        groupOption.options.shaded=false;
    }
    if (groupIndex % 2 == 0) {
        groupOption.options.yAxisOrientation = "left";
        groupOption.options.drawPoints.style = 'square';
    } else {
        groupOption.options.yAxisOrientation = "right";
        groupOption.options.drawPoints.style = 'circle';
    }
    return groupOption;
}






