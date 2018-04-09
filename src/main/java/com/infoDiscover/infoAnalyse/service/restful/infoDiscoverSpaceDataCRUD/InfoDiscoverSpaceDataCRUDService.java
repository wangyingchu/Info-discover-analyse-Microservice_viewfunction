package com.infoDiscover.infoAnalyse.service.restful.infoDiscoverSpaceDataCRUD;

import com.infoDiscover.infoAnalyse.service.restful.vo.dataCRUD.DataCUDInputVO;
import com.infoDiscover.infoAnalyse.service.restful.vo.dataCRUD.DataCUDResultVO;
import com.infoDiscover.infoAnalyse.service.restful.vo.dataCRUD.DataRetrieveResultVO;
import com.infoDiscover.infoAnalyse.service.restful.vo.dataCRUD.ExploreParametersVO;
import com.infoDiscover.infoAnalyse.service.util.DiscoverSpaceDataCRUDUtil;
import com.infoDiscover.infoAnalyse.service.util.DiscoverSpaceOperationConstant;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import java.util.Date;

@Service
@Path("/infoDiscoverSpaceDataCRUDService")
@Produces("application/json")
public class InfoDiscoverSpaceDataCRUDService {

    @POST
    @Path("/queryMeasurable/")
    @Produces("application/json")
    public DataRetrieveResultVO queryMeasurable(ExploreParametersVO exploreParametersVO){

        DataRetrieveResultVO dataRetrieveResultVO=null;
        if(exploreParametersVO.getTypeName()==null||exploreParametersVO.getType()==null||exploreParametersVO.getDiscoverSpaceName()==null){
            dataRetrieveResultVO=new DataRetrieveResultVO();
            dataRetrieveResultVO.setOperationReturnCode(DataRetrieveResultVO.operationResultCodeValue.INVALID_INPUT);
        }
        if(dataRetrieveResultVO==null){
            String dataTypeKind=exploreParametersVO.getType();
            if(!dataTypeKind.equals(DiscoverSpaceOperationConstant.PAYLOAD_DIMENSION)&
                    !dataTypeKind.equals(DiscoverSpaceOperationConstant.PAYLOAD_FACT)&
                    !dataTypeKind.equals(DiscoverSpaceOperationConstant.PAYLOAD_RELATION)){
                dataRetrieveResultVO=new DataRetrieveResultVO();
                dataRetrieveResultVO.setOperationReturnCode(DataRetrieveResultVO.operationResultCodeValue.INVALID_INPUT);
            }
        }
        if(dataRetrieveResultVO!=null){
            dataRetrieveResultVO.setOperationExecuteTime(new Date().getTime());
            return dataRetrieveResultVO;
        }else{
            dataRetrieveResultVO=DiscoverSpaceDataCRUDUtil.queryMeasurable(exploreParametersVO);
            dataRetrieveResultVO.setOperationExecuteTime(new Date().getTime());
        }
        return dataRetrieveResultVO;
    }

    @PUT
    @Path("/createRelationable/")
    @Produces("application/json")
    public DataCUDResultVO createRelationable(DataCUDInputVO dataCRUDInput){
        DataCUDResultVO dataCUDResultVO =new DataCUDResultVO();
        if(dataCRUDInput.getDiscoverSpaceName()==null||dataCRUDInput.getDataPayload()==null){
            dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.INVALID_INPUT);
        }else{
            DataCUDResultVO.operationResultCodeValue resultCode=DiscoverSpaceDataCRUDUtil.createTypeDataFromJSON(dataCRUDInput.getDiscoverSpaceName(),dataCRUDInput.getDataPayload());
            dataCUDResultVO.setOperationReturnCode(resultCode);
        }
        dataCUDResultVO.setOperationExecuteTime(new Date().getTime());
        return dataCUDResultVO;
    }

    @POST
    @Path("/updateMeasurable/")
    @Produces("application/json")
    public DataCUDResultVO updateMeasurable(DataCUDInputVO dataCRUDInput){
        DataCUDResultVO dataCUDResultVO;
        if(dataCRUDInput.getDiscoverSpaceName()==null||dataCRUDInput.getDataPayload()==null){
            dataCUDResultVO =new DataCUDResultVO();
            dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.INVALID_INPUT);
        }else{
            dataCUDResultVO =DiscoverSpaceDataCRUDUtil.updateTypeData(dataCRUDInput);
        }
        dataCUDResultVO.setOperationExecuteTime(new Date().getTime());
        return dataCUDResultVO;
    }

    @DELETE
    @Path("/deleteMeasurable/")
    @Produces("application/json")
    public DataCUDResultVO deleteMeasurable(DataCUDInputVO dataCRUDInput){
        DataCUDResultVO dataCUDResultVO;
        if(dataCRUDInput.getDiscoverSpaceName()==null||dataCRUDInput.getDataPayload()==null){
            dataCUDResultVO =new DataCUDResultVO();
            dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.INVALID_INPUT);
        }else{
            dataCUDResultVO =DiscoverSpaceDataCRUDUtil.deleteTypeData(dataCRUDInput);
        }
        dataCUDResultVO.setOperationExecuteTime(new Date().getTime());
        return dataCUDResultVO;
    }

    @DELETE
    @Path("/deleteMeasurableProperties/")
    @Produces("application/json")
    public DataCUDResultVO deleteMeasurableProperties(DataCUDInputVO dataCRUDInput){
        DataCUDResultVO dataCUDResultVO;
        if(dataCRUDInput.getDiscoverSpaceName()==null||dataCRUDInput.getDataPayload()==null){
            dataCUDResultVO =new DataCUDResultVO();
            dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.INVALID_INPUT);
        }else{
            dataCUDResultVO =DiscoverSpaceDataCRUDUtil.deleteTypeDataProperties(dataCRUDInput);
        }
        dataCUDResultVO.setOperationExecuteTime(new Date().getTime());
        return dataCUDResultVO;
    }
}
