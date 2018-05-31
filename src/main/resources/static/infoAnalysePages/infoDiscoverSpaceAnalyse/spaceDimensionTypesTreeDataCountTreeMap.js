$(document).ready(function() {
    discoverSpaceName=getQueryString("discoverSpace");  
    var graphHeight=getQueryString("graphHeight");
    if(!discoverSpaceName){return;}  
    if(graphHeight){
        document.getElementById('container').style.height=""+graphHeight+"px";
    }    
    var restURL=APPLICATION_REST_SERVICE_CONTEXT+"infoDiscoverSpaceAnalyseService/spaceDimensionTypesTreeDataCount/"+discoverSpaceName+"/";
    $.ajax({
        url: restURL
    }).then(function(data) {        
        var treeMapData=[];
        generateTreeMapData(treeMapData,data);
        Highcharts.chart('container', {
            series: [{
                type: 'treemap',
                layoutAlgorithm: 'squarified',
                allowDrillToNode: true,
                animationLimit: 1000,
                dataLabels: {
                    enabled: false
                },
                levelIsConstant: false,
                levels: [{
                    level: 1,
                    dataLabels: {
                        enabled: true
                    },
                    borderWidth: 3
                }],           
                //data: data
                data:treeMapData
            }],
            subtitle: {
                text: '点击维度数据单元格可下钻获取子维度数据分布详细信息.'
            },
            title: {
                text: '信息发现空间维度数据分布'
            },
            credits: {
                enabled:false
            }
        });
    });
});

function generateTreeMapData(treeMapData,dataArray,parentDataId,parentDataName,parentDavaValue){  
    var parentSelfDavaValue=0;
    if (parentDavaValue!=0){
        parentSelfDavaValue=parentDavaValue;
    }

    $.each(dataArray,function(index,value){        
        var dataName=value.typeName;
        var dataValue=value.typeDataRecordCount;
        var childrenDatasArray=value.childDimensionTypeInfosVOList;
        var currrentDataObj={};
        currrentDataObj['name']=dataName;
        currrentDataObj['value']=dataValue;
        if(parentDataId){
            currrentDataObj['parent']=parentDataId;
            currrentDataObj['id']=parentDataId+"_"+dataName;
        }else{
             currrentDataObj['id']=dataName;
             currrentDataObj['color']=getCurrentGlobalColor(index);
        }

        if(parentSelfDavaValue!=0){
            parentSelfDavaValue=parentSelfDavaValue-dataValue;
        }

        treeMapData.push(currrentDataObj);
        generateTreeMapData(treeMapData,childrenDatasArray,currrentDataObj['id'],dataName,dataValue);
    });  

    if(parentSelfDavaValue!=0){
        if(parentDataName){
            var parentSelfDataObj={};
            parentSelfDataObj['name']=parentDataName;
            parentSelfDataObj['value']=parentSelfDavaValue;
            parentSelfDataObj['parent']=parentDataId;
            treeMapData.push(parentSelfDataObj);
        }
    }
}