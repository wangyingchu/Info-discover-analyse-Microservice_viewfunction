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

var LENGTH_MAIN = 350,
    LENGTH_SERVER = 150,
    LENGTH_SUB = 50,
    WIDTH_SCALE = 2,
    GREEN = 'green',
    RED = '#C5000B',
    ORANGE = 'orange',
    //GRAY = '#666666',
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
    var dataInstanceId=getQueryString("dataInstanceId");
    var graphHeight=getQueryString("graphHeight");
    if(!discoverSpaceName){return;}
    if(!dataInstanceId){return;}
    if(graphHeight){
        document.getElementById('mynetwork').style.height=""+graphHeight+"px";
    }
    dataInstanceId=dataInstanceId.replace(/#/g, "%23");
    dataInstanceId=dataInstanceId.replace(/:/g, "%3a");
    var restURL=APPLICATION_REST_SERVICE_CONTEXT+"/ws/typeInstanceAnalyseService/typeInstanceRelationsCycle/"+discoverSpaceName+"/"+dataInstanceId+"/";
    $.ajax({
        url: restURL
    }).then(function(data) {
        var sourceTypeInstance=data.sourceTypeInstance;
        var relationInfoList=data.relationsInfo;

        sourceTypeInstanceId=sourceTypeInstance.id;
        var sourceTypeInstanceLabelName=sourceTypeInstance.relationableTypeName;
        if(sourceTypeInstance.relationableTypeAliasName){
            sourceTypeInstanceLabelName=sourceTypeInstance.relationableTypeAliasName;
        }
        nodesDataArray.push(
            {
                id:sourceTypeInstance.id,
                label: sourceTypeInstanceLabelName+"["+sourceTypeInstance.id+"]",
                group: sourceTypeInstance.relationableTypeKind,
                color: BLACK,
                shape: dataInstanceTypeShapeMap[sourceTypeInstance.relationableTypeKind],
                title: dataInstanceTypeDisplayNameMap[sourceTypeInstance.relationableTypeKind]+': '+sourceTypeInstance.relationableTypeName+" ["+sourceTypeInstance.id+"]"
            }
        );      

        $.each(relationInfoList,function(index,value){
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
                                label:fromTypeInstanceLabelName+" ["+fromRelationable.id+"]",
                                group: fromRelationable.relationableTypeKind,
                                shape: dataInstanceTypeShapeMap[fromRelationable.relationableTypeKind],
                                title: dataInstanceTypeDisplayNameMap[fromRelationable.relationableTypeKind]+': '+fromRelationable.relationableTypeName+" ["+fromRelationable.id+"]"
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
                                title: dataInstanceTypeDisplayNameMap[toRelationable.relationableTypeKind]+': '+toRelationable.relationableTypeName+" ["+toRelationable.id+"]"
                            }                            
                        );                        
                    }
                }
            }

			var relationTitle=relationTypeName;
			if(value.relationTypeAliasName){
				relationTitle=value.relationTypeAliasName+"("+relationTypeName+")";
			}
            edgesDataArray.push(
                {
                    from: fromRelationable.id, to:toRelationable.id,
                    arrows:'to',
                    label:relationId,
                    title: '\u5173\u7cfb: '+relationTitle+" ["+relationId+"]"
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
            if(selectedDataInstanceId!=sourceTypeInstanceId){
                var currentClickSourceNodeId=selectedDataInstanceId;
                selectedDataInstanceId=selectedDataInstanceId.replace(/#/g, "%23");
                selectedDataInstanceId=selectedDataInstanceId.replace(/:/g, "%3a");
                var restURL=APPLICATION_REST_SERVICE_CONTEXT+"/ws/typeInstanceAnalyseService/typeInstanceRelationsCycle/"+discoverSpaceName+"/"+selectedDataInstanceId+"/";
                $.ajax({
                    url: restURL
                }).then(function(data) {
                    appendNewSelectedRelations(data.relationsInfo,currentClickSourceNodeId);
                });
            }
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
                        title: dataInstanceTypeDisplayNameMap[fromRelationable.relationableTypeKind]+': '+fromRelationable.relationableTypeName+" ["+fromRelationable.id+"]",
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
                        title: dataInstanceTypeDisplayNameMap[toRelationable.relationableTypeKind]+': '+toRelationable.relationableTypeName+" ["+toRelationable.id+"]",
                        color: LIGHT_GRAY
                    });             
                } 
            }
        }

        if(!checkEdgeExistence(relationId)){
			var relationTitle=relationTypeName;
			if(value.relationTypeAliasName){
				relationTitle=value.relationTypeAliasName+"("+relationTypeName+")";
			}
            edges.add( {
                from: fromRelationable.id, to:toRelationable.id,
                arrows:'to',
                label:relationId,
                title: '\u5173\u7cfb: '+relationTitle+" ["+relationId+"]"
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
