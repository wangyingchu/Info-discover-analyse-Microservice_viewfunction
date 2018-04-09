package com.infoDiscover.infoAnalyse.service.restful.systemManagement;

import com.infoDiscover.infoAnalyse.service.util.DiscoverSpaceOperationUtil;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by wangychu on 5/12/17.
 */

@Service
@Path("/systemManagementService")
@Produces("application/json")
public class SystemManagementService {

    @GET
    @Path("/refreshDiscoverSpacesCacheInfo/")
    @Produces({"application/xml", "application/json"})
    public void refreshDiscoverSpaceDataMetaInfo(){
        DiscoverSpaceOperationUtil.refreshItemAliasNameCache();
    }
}
