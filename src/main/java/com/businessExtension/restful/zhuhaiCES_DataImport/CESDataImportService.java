package com.businessExtension.restful.zhuhaiCES_DataImport;

import com.businessExtension.constructionSupervision.manager.DataImporter4ConstructionSupervision;

import javax.ws.rs.*;
import java.io.UnsupportedEncodingException;

/**
 * Created by wangychu on 6/11/17.
 */
@Path("/cESDataImportService")
@Produces("application/json")
public class CESDataImportService {

    @POST
    @Path("/importProject/{discoverSpaceName}/")
    @Produces("application/xml")
    public boolean importProject(@PathParam("discoverSpaceName")String discoverSpaceName, String projectData){
        if(discoverSpaceName!=null&&projectData!=null) {
            try {
                DataImporter4ConstructionSupervision dataImporter=new DataImporter4ConstructionSupervision(discoverSpaceName);
                dataImporter.importProject(projectData);
                return true;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @POST
    @Path("/importTask/{discoverSpaceName}/")
    @Produces("application/xml")
    public boolean importTask(@PathParam("discoverSpaceName")String discoverSpaceName, String taskData){
        if(discoverSpaceName!=null&&taskData!=null) {
            try {
                DataImporter4ConstructionSupervision dataImporter=new DataImporter4ConstructionSupervision(discoverSpaceName);
                dataImporter.importTask(taskData);
                return true;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @GET
    @Path("/completeProject/{discoverSpaceName}/{projectId}/{projectType}/")
    @Produces("application/xml")
    public boolean completeProject(@PathParam("discoverSpaceName")String discoverSpaceName, @PathParam("projectId")String projectId, @PathParam("projectType")String projectType){
        if(discoverSpaceName!=null&&projectId!=null) {
            DataImporter4ConstructionSupervision dataImporter=new DataImporter4ConstructionSupervision(discoverSpaceName);
            try {
                dataImporter.updateProjectStatus(projectId,projectType,"Completed");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
