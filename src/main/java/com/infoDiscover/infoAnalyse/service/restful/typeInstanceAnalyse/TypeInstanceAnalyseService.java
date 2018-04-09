package com.infoDiscover.infoAnalyse.service.restful.typeInstanceAnalyse;

import com.infoDiscover.infoAnalyse.service.restful.vo.*;
import com.infoDiscover.infoAnalyse.service.restful.vo.typeInstance.*;
import com.infoDiscover.infoAnalyse.service.util.DiscoverSpaceOperationConstant;
import com.infoDiscover.infoAnalyse.service.util.DiscoverSpaceOperationUtil;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Map;

/**
 * Created by wangychu on 2/13/17.
 */

@Service
@Path("/typeInstanceAnalyseService")
@Produces("application/json")
public class TypeInstanceAnalyseService {

    @GET
    @Path("/typeInstanceRelationsCycle/{discoverSpaceName}/{relationableId}")
    @Produces("application/json")
    public TypeInstanceRelationsCycleVO getTypeInstanceRelationsInfoCycle(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("relationableId")String relationableId){
        TypeInstanceRelationsCycleVO resultInfo=DiscoverSpaceOperationUtil.getRelationableRelationsCycleInfoById(discoverSpaceName,relationableId);
        return resultInfo;
    }

    @GET
    @Path("/typeInstanceRelationsExplore/{discoverSpaceName}/{relationableId}")
    @Produces("application/json")
    public TypeInstanceRelationsDetailVO getTypeInstanceRelationsDetailInfo(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("relationableId")String relationableId){
        TypeInstanceRelationsDetailVO resultInfo=DiscoverSpaceOperationUtil.getRelationableRelationsDetailInfoById(discoverSpaceName,relationableId);
        return resultInfo;
    }

    @GET
    @Path("/relationRelatedTypeInstancesExplore/{discoverSpaceName}/{relationId}")
    @Produces("application/json")
    public RelationDetailInfoVO getRelationRelatedTypeInstancesDetailInfo(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("relationId")String relationId){
        RelationDetailInfoVO resultInfo=DiscoverSpaceOperationUtil.getRelationDetailInfoById(discoverSpaceName,relationId);
        return resultInfo;
    }

    @GET
    @Path("/typeInstanceDetailInfo/{discoverSpaceName}/{measurableInstanceId}")
    @Produces("application/json")
    public MeasurableInstanceDetailInfoVO getTypeInstanceDetailInfo(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("measurableInstanceId")String measurableInstanceId){
        MeasurableInstanceDetailInfoVO resultInfo=DiscoverSpaceOperationUtil.getMeasurableInstanceDetailInfo(discoverSpaceName,measurableInstanceId);
        return resultInfo;
    }

    @GET
    @Path("/typeInstancesInfoOfMeasurableTypes/{discoverSpaceName}/{measurableType}/{measurableTypeNames}")
    @Produces("application/json")
    public Map<String,MeasurableTypeDataInfoVO> getTypeInstancesInfoOfMeasurableTypes(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("measurableType")String measurableType, @PathParam("measurableTypeNames")String measurableTypeNames){
        String measurableTypeStr=null;
        if("FACT".equals(measurableType)){
            measurableTypeStr= DiscoverSpaceOperationConstant.TYPEKIND_FACT;
        }
        if("DIMENSION".equals(measurableType)){
            measurableTypeStr= DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION;
        }
        if("RELATION".equals(measurableType)){
            measurableTypeStr= DiscoverSpaceOperationConstant.TYPEKIND_RELATION;
        }
        Map<String,MeasurableTypeDataInfoVO> resultInfo=DiscoverSpaceOperationUtil.getTypeInstancesInfoOfMeasurableTypes(discoverSpaceName,measurableTypeStr,measurableTypeNames);
        return resultInfo;
    }

    @GET
    @Path("/typeInstancesDetailInfo/{discoverSpaceName}/{measurableInstanceIds}")
    @Produces("application/json")
    public List<MeasurableInstanceDetailInfoVO> getTypeInstancesDetailInfo(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("measurableInstanceIds")String measurableInstanceIds){
        List<MeasurableInstanceDetailInfoVO> resultInfo=DiscoverSpaceOperationUtil.getMeasurableInstancesDetailInfo(discoverSpaceName,measurableInstanceIds);
        return resultInfo;
    }

    @GET
    @Path("/similarTypeInstancesExplore/{discoverSpaceName}/{relationableId}/{dimensionsIdList}/{filteringPattern}")
    @Produces("application/json")
    public TypeInstanceSimilarDataDetailVO getSimilarRelationableInfoConnectedSameDimensions(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("relationableId")String relationableId,
                                                                                             @PathParam("dimensionsIdList")String dimensionsIdList, @PathParam("filteringPattern")String filteringPattern){
        TypeInstanceSimilarDataDetailVO resultInfo=DiscoverSpaceOperationUtil.getSimilarRelationableConnectedSameDimensions(discoverSpaceName,relationableId,dimensionsIdList,filteringPattern);
        return resultInfo;
    }

    @GET
    @Path("/typeInstancesShortestPathExplore/{discoverSpaceName}/{relationableAId}/{relationableBId}")
    @Produces("application/json")
    public ShortestPathBetweenTwoMeasurablesDetailInfoVO getShortestPathBetweenTwoTypeInstances(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("relationableAId")String relationableAId,
                                                                                                @PathParam("relationableBId")String relationableBId){
        ShortestPathBetweenTwoMeasurablesDetailInfoVO resultInfo=DiscoverSpaceOperationUtil.getShortestPathBetweenTwoRelationable(discoverSpaceName,relationableAId,relationableBId);
        return resultInfo;
    }

    @GET
    @Path("/typeInstancesSpecifiedPathExplore/{discoverSpaceName}/{relationableAId}/{relationableBId}/{pathRelationIds}")
    @Produces("application/json")
    public MeasurablesPathInfoVO getSpecifiedPathBetweenTwoTypeInstances(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("relationableAId")String relationableAId,
                                                                                                @PathParam("relationableBId")String relationableBId,@PathParam("pathRelationIds")String pathRelationIds){
        MeasurablesPathInfoVO resultInfo=DiscoverSpaceOperationUtil.getSpecifiedPathBetweenTwoRelationable(discoverSpaceName,relationableAId,relationableBId,pathRelationIds);
        return resultInfo;
    }

    @GET
    @Path("/typeInstancesAllPathsExplore/{discoverSpaceName}/{relationableAId}/{relationableBId}")
    @Produces("application/json")
    public AllPathsBetweenTwoMeasurablesDetailInfoVO getAllPathsBetweenTwoTypeInstances(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("relationableAId")String relationableAId,
                                                                                                           @PathParam("relationableBId")String relationableBId){
        AllPathsBetweenTwoMeasurablesDetailInfoVO resultInfo=DiscoverSpaceOperationUtil.getAllPathsBetweenTwoRelationable(discoverSpaceName,relationableAId,relationableBId);
        return resultInfo;
    }

    @GET
    @Path("/typeInstancesShortestPathsExplore/{discoverSpaceName}/{relationableAId}/{relationableBId}/{pathNumber}")
    @Produces("application/json")
    public PathsBetweenTwoMeasurablesDetailInfoVO getShortestPathsBetweenTwoTypeInstances(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("relationableAId")String relationableAId,
                                                                                          @PathParam("relationableBId")String relationableBId, @PathParam("pathNumber")String pathNumber){
        int pathNumberInt=Integer.parseInt(pathNumber);
        PathsBetweenTwoMeasurablesDetailInfoVO resultInfo=DiscoverSpaceOperationUtil.getPathsBetweenTwoRelationable(discoverSpaceName,relationableAId,relationableBId,"SHORTEST",pathNumberInt);
        return resultInfo;
    }

    @GET
    @Path("/typeInstancesLongestPathsExplore/{discoverSpaceName}/{relationableAId}/{relationableBId}/{pathNumber}")
    @Produces("application/json")
    public PathsBetweenTwoMeasurablesDetailInfoVO getLongestPathsBetweenTwoTypeInstances(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("relationableAId")String relationableAId,
                                                                                        @PathParam("relationableBId")String relationableBId,@PathParam("pathNumber")String pathNumber){
        int pathNumberInt=Integer.parseInt(pathNumber);
        PathsBetweenTwoMeasurablesDetailInfoVO resultInfo=DiscoverSpaceOperationUtil.getPathsBetweenTwoRelationable(discoverSpaceName,relationableAId,relationableBId,"LONGEST",pathNumberInt);
        return resultInfo;
    }

    @GET
    @Path("/typeInstancesPathsContainDatasExplore/{discoverSpaceName}/{relationableAId}/{relationableBId}/{containedDataIds}")
    @Produces("application/json")
    public PathsBetweenTwoMeasurablesDetailInfoVO getPathsContainDatasBetweenTwoTypeInstances(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("relationableAId")String relationableAId,
                                                                                         @PathParam("relationableBId")String relationableBId,@PathParam("containedDataIds")String containedDataIds){
        PathsBetweenTwoMeasurablesDetailInfoVO resultInfo=DiscoverSpaceOperationUtil.getPathsContainPointedDatasBetweenTwoRelationables(discoverSpaceName,relationableAId,relationableBId,containedDataIds);
        return resultInfo;
    }
}
