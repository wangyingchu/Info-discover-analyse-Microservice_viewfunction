var discoverSpaceName;
var network;
var nodes;
var edges;
var sourceTypeInstanceId;
var nodesDataArray=[];
var edgesDataArray=[];
var existRelationIdArray=[];

var dataInstanceTypeShapeMap=[];
dataInstanceTypeShapeMap["TYPEKIND_DIMENSION"]='dot';
dataInstanceTypeShapeMap["TYPEKIND_FACT"]='square';

var dataInstanceTypeDisplayNameMap=[];
dataInstanceTypeDisplayNameMap["TYPEKIND_DIMENSION"]='\u7ef4\u5ea6';
dataInstanceTypeDisplayNameMap["TYPEKIND_FACT"]='\u4e8b\u5b9e';
var currentExploreLevel=0;
var LENGTH_MAIN = 350,
    LENGTH_SERVER = 150,
    LENGTH_SUB = 50,
    WIDTH_SCALE = 2,
    GREEN = 'green',
    RED = '#C5000B',
    ORANGE = 'orange',   
    GRAY = 'gray',
    LIGHT_GRAY='#BBBBBB',
    BLACK = '#333333';

var options = {
    nodes: {
        shape: 'dot',
        size: 20,
        font: {
            size: 15
        },
        borderWidth: 2,
        shadow:true
    },
    edges: {
        width: 2,
        shadow:true,
        font: {
            size: 10
        },
        color: GRAY,
        smooth: {
            type: "vertical",
            forceDirection: "none"
        }
    },
    physics:{
        barnesHut:{gravitationalConstant:-30000},
        stabilization: {iterations:2500}
    },
    interaction:{hover:true}
};

$(document).ready(function() {
    discoverSpaceName=getQueryString("discoverSpace");
    var relationableAId=getQueryString("relationableAId");
	var relationableBId=getQueryString("relationableBId");	
    var graphHeight=getQueryString("graphHeight");
    if(!discoverSpaceName){return;}
    if(!relationableAId){return;}
	if(!relationableBId){return;}	
    if(graphHeight){
        document.getElementById('mynetwork').style.height=""+graphHeight+"px";
    }
    relationableAId=relationableAId.replace(/#/g, "%23");
    relationableAId=relationableAId.replace(/:/g, "%3a");
	relationableBId=relationableBId.replace(/#/g, "%23");
    relationableBId=relationableBId.replace(/:/g, "%3a");

    var restURL=APPLICATION_REST_SERVICE_CONTEXT+"/ws/typeInstanceAnalyseService/typeInstancesShortestPathExplore/"+discoverSpaceName+"/"+relationableAId+"/"+relationableBId+"/";
    $.ajax({
        url: restURL
    }).then(function(data) {		
		var measurableA=data.measurableA;
		var measurableB=data.measurableB;
		var shortestPathInfo=data.pathRelationsDetailInfo;
		
		var measurableAId=measurableA.measurableId;
        var measurableALabelName=measurableA.measurableName;
        if(measurableA.measurableAliasName){
            measurableALabelName=measurableA.measurableAliasName;
        }
        nodesDataArray.push(
            {
                id: measurableAId,
                label: measurableALabelName+"["+measurableAId+"]",
                group: measurableA.measurableType,
 				size: 22, 
                color: BLACK,
                shape: dataInstanceTypeShapeMap[measurableA.measurableType],                
 				title:getDetailTitleForMeasurable(measurableA)
            }
        ); 

		var measurableBId=measurableB.measurableId;
        var measurableBLabelName=measurableB.measurableName;
        if(measurableB.measurableAliasName){
            measurableBLabelName=measurableB.measurableAliasName;
        }
        nodesDataArray.push(
            {
                id: measurableBId,
                label: measurableBLabelName+"["+measurableBId+"]",
                group: measurableB.measurableType,
 				size: 22, 
                color: BLACK,
                shape: dataInstanceTypeShapeMap[measurableB.measurableType],                
 				title:getDetailTitleForMeasurable(measurableB)
            }
        ); 
		
        $.each(shortestPathInfo,function(index,value){
            var relationId=value.id;
            var relationTypeName=value.relationTypeName;
            var fromRelationable=value.fromRelationable;
            var toRelationable=value.toRelationable;

 			var fromTypeInstanceLabelName=fromRelationable.relationableTypeName;
		    if(fromRelationable.relationableTypeAliasName){
		        fromTypeInstanceLabelName=fromRelationable.relationableTypeAliasName;
		    }
			var toTypeInstanceLabelName=toRelationable.relationableTypeName;
		    if(toRelationable.relationableTypeAliasName){
		        toTypeInstanceLabelName=toRelationable.relationableTypeAliasName;
		    }

            if(fromRelationable.id!=toRelationable.id){
                if(fromRelationable.id!=sourceTypeInstanceId){
                    if(!checkNodeDataExistence(fromRelationable.id)){
                        nodesDataArray.push(
                            {
                                id:fromRelationable.id,
                                label: fromTypeInstanceLabelName+" ["+fromRelationable.id+"]",
                                group: fromRelationable.relationableTypeKind,
                                shape: dataInstanceTypeShapeMap[fromRelationable.relationableTypeKind],
                                title: getDetailTitleForRelationable(fromRelationable),
								color: getCurrentGlobalColor(currentExploreLevel)
								
                            }
                        );
                    }
                }
                if(toRelationable.id!=sourceTypeInstanceId){
                    if(!checkNodeDataExistence(toRelationable.id)){
                        nodesDataArray.push(
                            {
                                id:toRelationable.id,
                                label: toTypeInstanceLabelName+" ["+toRelationable.id+"]",
                                group: toRelationable.relationableTypeKind,
                                shape: dataInstanceTypeShapeMap[toRelationable.relationableTypeKind],
                                title: getDetailTitleForRelationable(toRelationable),
								color: getCurrentGlobalColor(currentExploreLevel)
								
                            }
                        );
                    }
                }
            }

			var relationTitle=relationTypeName;
			if(value.relationTypeAliasName){
				relationTitle=relationTypeName+"("+value.relationTypeAliasName+")";
			}
            edgesDataArray.push(
                {
                    from: fromRelationable.id, to:toRelationable.id,
					width: 8,
                	color: getCurrentGlobalColor(currentExploreLevel),
                    arrows:'to',
                    label:relationId,
                    title: '\u5173\u7cfb: '+relationTitle+" ["+relationId+"]"+getPropertiesDetailInfo(value.propertiesValueList)					
                });
            existRelationIdArray.push(relationId);
        });			
 		// create a network
        var container = document.getElementById('mynetwork');
        nodes = new vis.DataSet(nodesDataArray);
        edges = new vis.DataSet(edgesDataArray);
        var data = {
            nodes: nodes,
            edges: edges
        };
        network = new vis.Network(container, data, options);

		network.on("selectNode", function (params) {
            var selectedDataInstanceId=params.nodes[0];            
            var currentClickSourceNodeId=selectedDataInstanceId;
            selectedDataInstanceId=selectedDataInstanceId.replace(/#/g, "%23");
            selectedDataInstanceId=selectedDataInstanceId.replace(/:/g, "%3a");
            var restURL=APPLICATION_REST_SERVICE_CONTEXT+"/ws/typeInstanceAnalyseService/typeInstanceRelationsExplore/"+discoverSpaceName+"/"+selectedDataInstanceId+"/";
            $.ajax({
                url: restURL
            }).then(function(data) {
                appendNewSelectedRelations(data.relationsInfo,currentClickSourceNodeId);
            });           
        });
    });
});

function appendNewSelectedRelations(relationsInfo,selectedSourceNodeId){
    relationsInfo.forEach(function(value,index){
        var relationId=value.id;
        var relationTypeName=value.relationTypeName;
        var fromRelationable=value.fromRelationable;
        var toRelationable=value.toRelationable;

		var fromTypeInstanceLabelName=fromRelationable.relationableTypeName;
		if(fromRelationable.relationableTypeAliasName){
		    fromTypeInstanceLabelName=fromRelationable.relationableTypeAliasName;
		}
		var toTypeInstanceLabelName=toRelationable.relationableTypeName;
		if(toRelationable.relationableTypeAliasName){
		    toTypeInstanceLabelName=toRelationable.relationableTypeAliasName;
		}

        if(fromRelationable.id!=toRelationable.id){
            //if fromRelationable.id == toRelationable.id means they are same node(the selected SourceNode)
            if(selectedSourceNodeId!=fromRelationable.id){
                //fromRelationable is another node
                var isExistNodeFlag=checkNetworkNodeExistence(fromRelationable.id);
                if(!isExistNodeFlag){
                    nodes.add({
                        id: fromRelationable.id,
                        label: fromTypeInstanceLabelName+" ["+fromRelationable.id+"]",
                        group: fromRelationable.relationableTypeKind,
                        shape: dataInstanceTypeShapeMap[fromRelationable.relationableTypeKind],
                        title: getDetailTitleForRelationable(fromRelationable),
                        color: LIGHT_GRAY
                    });
                }
            }
            if(selectedSourceNodeId!=toRelationable.id){
                //toRelationable is another node
                var isExistNodeFlag=checkNetworkNodeExistence(toRelationable.id);
                if(!isExistNodeFlag){
                    nodes.add({
                        id: toRelationable.id,
                        label: toTypeInstanceLabelName+" ["+toRelationable.id+"]",
                        group: toRelationable.relationableTypeKind,
                        shape: dataInstanceTypeShapeMap[toRelationable.relationableTypeKind],
                        title: getDetailTitleForRelationable(toRelationable),				
                        color: LIGHT_GRAY
                    });
                }
            }
        }

        if(!checkEdgeExistence(relationId)){
			var relationTitle=relationTypeName;
			if(value.relationTypeAliasName){
				relationTitle=relationTypeName+"("+value.relationTypeAliasName+")";
			}
            edges.add( {
                from: fromRelationable.id, to:toRelationable.id,
                arrows:'to',
                label:relationId,
                title: '\u5173\u7cfb: '+relationTitle+" ["+relationId+"]"+getPropertiesDetailInfo(value.propertiesValueList)	
            });
            existRelationIdArray.push(relationId);
        }
    });	
}

function checkNetworkNodeExistence(nodeId){
    var existingNodes=network.body.nodes;
    var nodeExistenceResult=false;
    $.each(existingNodes,function(index,value){
        if(value.id==nodeId){
            nodeExistenceResult=true;
            return false;
        }
    });
    return nodeExistenceResult;
}

function checkNodeDataExistence(nodeDataId){
    var existFlag=false
    $.each(nodesDataArray,function(index,value){
        if(value.id==nodeDataId){
            existFlag=true;
            return false;
        }
    });
    return existFlag;
}

function checkEdgeExistence(edgeId){
    var existFlag=false
    $.each(existRelationIdArray,function(index,value){
        if(value==edgeId){
            existFlag=true;
            return false;
        }
    });
    return existFlag;
}

function getDetailTitleForMeasurable(dataTypeInstance){
	var detailTitle="<b>"+dataInstanceTypeDisplayNameMap[dataTypeInstance.measurableType]+': '+dataTypeInstance.measurableName+" ["+dataTypeInstance.measurableId+"]"+"</b>";
	var propertiesList=dataTypeInstance.measurableProperties;
	if(propertiesList){
		$.each(propertiesList,function(index,value){
			var propertyValue=value.propertyValue;
			if(value.propertyType=="DATE"){
				propertyValue=new Date(Number(value.propertyValue)).toLocaleString();
			}
			var propertyTitle=value.propertyName;
			if(value.propertyAliasName){
				propertyTitle=value.propertyName+"("+value.propertyAliasName+")";
			}
			detailTitle=detailTitle+"<br/>"+"<span style='font-size: 0.8em;'>"+"<span style='color:#666666;'>["+value.propertyType+"]</span> <b>"+propertyTitle+"</b> : "+propertyValue+"</span>"
		});
	}
	return detailTitle;
}

function getDetailTitleForRelationable(dataTypeInstance){
	var detailTitle="<b>"+dataInstanceTypeDisplayNameMap[dataTypeInstance.relationableTypeKind]+': '+dataTypeInstance.relationableTypeName+" ["+dataTypeInstance.id+"]"+"</b>";
	var propertiesList=dataTypeInstance.propertiesValueList;
	if(propertiesList){
		$.each(propertiesList,function(index,value){
			var propertyValue=value.propertyValue;
			if(value.propertyType=="DATE"){
				propertyValue=new Date(Number(value.propertyValue)).toLocaleString();
			}
			var propertyTitle=value.propertyName;
			if(value.propertyAliasName){
				propertyTitle=value.propertyName+"("+value.propertyAliasName+")";
			}
			detailTitle=detailTitle+"<br/>"+"<span style='font-size: 0.8em;'>"+"<span style='color:#666666;'>["+value.propertyType+"]</span> <b>"+propertyTitle+"</b> : "+propertyValue+"</span>"
		});
	}
	return detailTitle;
}

function getPropertiesDetailInfo(propertiesList){
	var detailTitle=""	
	if(propertiesList){
		$.each(propertiesList,function(index,value){
			var propertyValue=value.propertyValue;
			if(value.propertyType=="DATE"){
				propertyValue=new Date(Number(value.propertyValue)).toLocaleString();
			}
			var propertyTitle=value.propertyName;
			if(value.propertyAliasName){
				propertyTitle=value.propertyName+"("+value.propertyAliasName+")";
			}
			detailTitle=detailTitle+"<br/>"+"<span style='font-size: 0.8em;'>"+"<span style='color:#666666;'>["+value.propertyType+"]</span> <b>"+propertyTitle+"</b> : "+propertyValue+"</span>"
		});
	}
	return detailTitle;
}































