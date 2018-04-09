package com.infoDiscover.infoAnalyse.service.restful.infoDiscoverSpaceAnalyse;

import com.infoDiscover.infoAnalyse.service.restful.vo.*;
import com.infoDiscover.infoAnalyse.service.util.DiscoverSpaceOperationUtil;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by wangychu on 2/19/17.
 */

@Service
@Path("/infoDiscoverSpaceAnalyseService")
@Produces("application/json")
public class InfoDiscoverSpaceAnalyseService {

    @GET
    @Path("/spaceFactTypesDataCount/{discoverSpaceName}/")
    @Produces("application/json")
    public List<FactTypeInfoVO> infoDiscoverSpaceFactTypeDataCount(@PathParam("discoverSpaceName")String discoverSpaceName){
        return DiscoverSpaceOperationUtil.getDiscoverSpaceFactTypesDataCountInfo(discoverSpaceName);
    }

    @GET
    @Path("/spaceDimensionTypesTreeDataCount/{discoverSpaceName}/")
    @Produces("application/json")
    public List<DimensionTypeInfoVO> infoDiscoverSpaceDimensionTypeTreeDataCount(@PathParam("discoverSpaceName")String discoverSpaceName){
       return new DiscoverSpaceOperationUtil().getDiscoverSpaceDimensionTypesTreeDataCountInfo(discoverSpaceName);
    }

    @GET
    @Path("/spaceRelationTypesTreeDataCount/{discoverSpaceName}/")
    @Produces("application/json")
    public List<RelationTypeInfoVO> infoDiscoverSpaceRelationTypeTreeDataCount(@PathParam("discoverSpaceName")String discoverSpaceName){
        return DiscoverSpaceOperationUtil.getDiscoverSpaceRelationTypesTreeDataCountInfo(discoverSpaceName);
    }

    @GET
    @Path("/spaceDimensionTypeDetailInfo/{discoverSpaceName}/{dimensionTypeName}/")
    @Produces("application/json")
    public DimensionTypeSummaryVO infoDiscoverSpaceDimensionTypeTreeDataCount(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("dimensionTypeName")String dimensionTypeName){
        return DiscoverSpaceOperationUtil.getDimensionTypeDetail(discoverSpaceName,dimensionTypeName);
    }

    @GET
    @Path("/spaceFactTypeDetailInfo/{discoverSpaceName}/{factTypeName}/")
    @Produces("application/json")
    public FactTypeSummaryVO infoDiscoverSpaceFactTypeTreeDataCount(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("factTypeName")String factTypeName){
        return DiscoverSpaceOperationUtil.getFactTypeDetail(discoverSpaceName,factTypeName);
    }

    @GET
    @Path("/spaceRelationTypeDetailInfo/{discoverSpaceName}/{relationTypeName}/")
    @Produces("application/json")
    public RelationTypeSummaryVO infoDiscoverSpaceRelationTypeTreeDataCount(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("relationTypeName")String relationTypeName){
        return DiscoverSpaceOperationUtil.getRelationTypeDetail(discoverSpaceName,relationTypeName);
    }
}
