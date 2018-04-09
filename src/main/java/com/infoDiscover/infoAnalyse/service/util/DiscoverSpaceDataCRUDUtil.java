package com.infoDiscover.infoAnalyse.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoDiscover.infoAnalyse.service.restful.vo.MeasurableQueryResultSetVO;
import com.infoDiscover.infoAnalyse.service.restful.vo.MeasurableVO;
import com.infoDiscover.infoAnalyse.service.restful.vo.dataCRUD.*;
import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.*;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.template.DataImporter;

import java.util.*;

public class DiscoverSpaceDataCRUDUtil {

    /*
    public static String transferTypeDataToJson(String dataTypeKind,String dataTypeName,List<DataTypePropertyInfoVO> dataPropertiesList){
        Map<String, Object> rootMap = new HashMap<String, Object>();
        List<Map> dataList=new ArrayList<Map>();
        rootMap.put("data", dataList);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataList.add(dataMap);

        if(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION.equals(dataTypeKind)){
            dataMap.put("type","Dimension");
        }
        if(DiscoverSpaceOperationConstant.TYPEKIND_FACT.equals(dataTypeKind)){
            dataMap.put("type","Fact");
        }
        dataMap.put("typeName",dataTypeName);

        List<Map> propertiesMapList=new ArrayList<Map>();
        dataMap.put("properties", propertiesMapList);

        if(dataPropertiesList!=null){
            for(DataTypePropertyInfoVO currentProperty:dataPropertiesList){
                String propertyType=getPropertyTypeCode(currentProperty.getPropertyValue());
                if(propertyType!=null){
                    Map<String,Object> projectNameMap=new HashMap<String, Object>();
                    projectNameMap.put("propertyType", propertyType);
                    projectNameMap.put("propertyName", currentProperty.getPropertyName());

                    if(currentProperty.getPropertyValue() instanceof Date){
                        Long longDateValue=((Date)currentProperty.getPropertyValue()).getTime();
                        projectNameMap.put("propertyValue", longDateValue);
                    }else{
                        projectNameMap.put("propertyValue", currentProperty.getPropertyValue());
                    }
                    propertiesMapList.add(projectNameMap);
                }
            }
        }

        ObjectMapper mapper=new ObjectMapper();
        try {
            String jsonString=mapper.writeValueAsString(rootMap);
            return jsonString;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getPropertyTypeCode(Object propertyValue){
        if(propertyValue instanceof String){
            return "String";
        }
        if(propertyValue instanceof Integer){
            return "Int";
        }
        if(propertyValue instanceof Long){
            return "Long";
        }
        if(propertyValue instanceof Double){
            return "Double";
        }
        if(propertyValue instanceof Float){
            return "Float";
        }
        if(propertyValue instanceof Boolean){
            return "Boolean";
        }
        if(propertyValue instanceof Date){
            return "Date";
        }
        if(propertyValue instanceof Short){
            return "Short";
        }
        return null;
    }
    */

    private static Map<String,Object> getPropertyValueMap( List<DataPropertyPayloadVO> propertyValuePayloadList){
        Map<String,Object> propertyValueMap=new HashMap<>();
        if(propertyValuePayloadList!=null){
            for(DataPropertyPayloadVO currentDataPropertyPayloadVO:propertyValuePayloadList){
                String propertyName=currentDataPropertyPayloadVO.getPropertyName();
                String propertyType=currentDataPropertyPayloadVO.getPropertyType();
                Object propertyValue=currentDataPropertyPayloadVO.getPropertyValue();
                if("String".equals(propertyType)){
                    propertyValueMap.put(propertyName,propertyValue.toString());
                }
                if("Int".equals(propertyType)){
                    propertyValueMap.put(propertyName,(Integer)propertyValue);
                }
                if("Long".equals(propertyType)){
                    Long targetPropertyValue=null;
                    if(propertyValue instanceof Integer){
                        targetPropertyValue=new Long((Integer)propertyValue);
                    }else if(propertyValue instanceof Long){
                        targetPropertyValue=(Long)propertyValue;
                    }
                    if(targetPropertyValue!=null){
                        propertyValueMap.put(propertyName,targetPropertyValue);
                    }
                }
                if("Double".equals(propertyType)){
                    propertyValueMap.put(propertyName,(Double)propertyValue);
                }
                if("Float".equals(propertyType)){
                    Float  targetPropertyValue=null;
                    if(propertyValue instanceof Double){
                        targetPropertyValue=new Float((Double)propertyValue);
                    }else if(propertyValue instanceof Float){
                        targetPropertyValue=(Float)propertyValue;
                    }
                    if(targetPropertyValue!=null){
                        propertyValueMap.put(propertyName,targetPropertyValue);
                    }
                }
                if("Boolean".equals(propertyType)){
                    propertyValueMap.put(propertyName,(Boolean)propertyValue);
                }
                if("Date".equals(propertyType)){
                    Date dateValue=new Date((Long)propertyValue);
                    propertyValueMap.put(propertyName,dateValue);
                }
                if("Short".equals(propertyType)){
                    Short targetPropertyValue=null;
                    if(propertyValue instanceof Integer){
                        targetPropertyValue=((Integer)propertyValue).shortValue();
                    }else if(propertyValue instanceof Short){
                        targetPropertyValue=(Short)propertyValue;
                    }
                    if(targetPropertyValue!=null){
                        propertyValueMap.put(propertyName,targetPropertyValue);
                    }
                }
            }
        }
        return propertyValueMap;
    }
/*
    public static boolean createTypeDate(String discoverSpaceName,String dataTypeKind,String dataTypeName,List<DataTypePropertyInfoVO> dataPropertiesList){
        DataImporter importer = new DataImporter(discoverSpaceName);
        String jsonContent=transferTypeDataToJson(dataTypeKind,dataTypeName,dataPropertiesList);
        try {
            importer.importData(jsonContent,true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
*/
    public static DataCUDResultVO.operationResultCodeValue createTypeDataFromJSON(String discoverSpaceName, DataPayloadWrapperVO dataPayloadWrapper){
        DataImporter importer = new DataImporter(discoverSpaceName);
        try {
            ObjectMapper mapper=new ObjectMapper();
            String jsonDataContent=mapper.writeValueAsString(dataPayloadWrapper);
            importer.importData(jsonDataContent,false);
            return DataCUDResultVO.operationResultCodeValue.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return DataCUDResultVO.operationResultCodeValue.FAILURE;
        }
    }

    public static DataCUDResultVO updateTypeData(DataCUDInputVO dataCRUDInput){
        DataCUDResultVO dataCUDResultVO =new DataCUDResultVO();
        String discoverSpaceName=dataCRUDInput.getDiscoverSpaceName();
        List<DataPayloadVO> dataPayLoadList=dataCRUDInput.getDataPayload().getData();
        int modifiedDataCount=0;
        if(dataPayLoadList!=null){
            InfoDiscoverSpace targetSpace=null;
            try {
                targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(discoverSpaceName);
                for(DataPayloadVO currentDataPayload:dataPayLoadList){
                    String payloadType=currentDataPayload.getType();
                    //String currentPayloadTypeName=currentDataPayload.getTypeName();
                    String recordId=currentDataPayload.getRecordId();
                    if(recordId!=null){
                        List<DataPropertyPayloadVO> propertyList=currentDataPayload.getProperties();
                        if(propertyList!=null){
                            Map<String,Object> propertyValueMap=getPropertyValueMap(propertyList);
                            if(DiscoverSpaceOperationConstant.PAYLOAD_DIMENSION.equals(payloadType)){
                                Dimension targetDimension=targetSpace.getDimensionById(recordId);
                                targetDimension.addNewOrUpdateProperties(propertyValueMap);
                                modifiedDataCount++;
                            }
                            if(DiscoverSpaceOperationConstant.PAYLOAD_FACT.equals(payloadType)){
                                Fact targetFact=targetSpace.getFactById(recordId);
                                targetFact.addNewOrUpdateProperties(propertyValueMap);
                                modifiedDataCount++;
                            }
                            if(DiscoverSpaceOperationConstant.PAYLOAD_RELATION.equals(payloadType)){
                                Relation targetRelation=targetSpace.getRelationById(recordId);
                                targetRelation.addNewOrUpdateProperties(propertyValueMap);
                                modifiedDataCount++;
                            }
                        }
                    }
                }
                if(modifiedDataCount>0){
                    dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.SUCCESS);
                    dataCUDResultVO.setModifiedDataCount(modifiedDataCount);
                }else{
                    dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.DATA_NOT_MODIFIED);
                }
            } catch (InfoDiscoveryEngineRuntimeException e) {
                e.printStackTrace();
                dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.FAILURE);
            } finally {
                if(targetSpace!=null){
                    targetSpace.closeSpace();
                }
            }
        }else {
            dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.INVALID_INPUT);
        }
        return dataCUDResultVO;
    }

    public static DataCUDResultVO deleteTypeData(DataCUDInputVO dataCRUDInput){
        DataCUDResultVO dataCUDResultVO =new DataCUDResultVO();
        String discoverSpaceName=dataCRUDInput.getDiscoverSpaceName();
        List<DataPayloadVO> dataPayLoadList=dataCRUDInput.getDataPayload().getData();
        int modifiedDataCount=0;
        if(dataPayLoadList!=null){
            InfoDiscoverSpace targetSpace=null;
            try {
                targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(discoverSpaceName);
                for(DataPayloadVO currentDataPayload:dataPayLoadList){
                    String payloadType=currentDataPayload.getType();
                    String recordId=currentDataPayload.getRecordId();
                    if(recordId!=null){
                        if(DiscoverSpaceOperationConstant.PAYLOAD_DIMENSION.equals(payloadType)){
                            boolean removeResult=targetSpace.removeDimension(recordId);
                            if(removeResult){
                                modifiedDataCount++;
                            }
                        }
                        if(DiscoverSpaceOperationConstant.PAYLOAD_FACT.equals(payloadType)){
                            boolean removeResult=targetSpace.removeFact(recordId);
                            if(removeResult){
                                modifiedDataCount++;
                            }
                        }
                        if(DiscoverSpaceOperationConstant.PAYLOAD_RELATION.equals(payloadType)){
                            boolean removeResult=targetSpace.removeRelation(recordId);
                            if(removeResult){
                                modifiedDataCount++;
                            }
                        }
                    }
                }
                if(modifiedDataCount>0){
                    dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.SUCCESS);
                    dataCUDResultVO.setModifiedDataCount(modifiedDataCount);
                }else{
                    dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.DATA_NOT_MODIFIED);
                }
            } catch (InfoDiscoveryEngineRuntimeException e) {
                e.printStackTrace();
                dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.FAILURE);
            } finally {
                if(targetSpace!=null){
                    targetSpace.closeSpace();
                }
            }
        }else {
            dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.INVALID_INPUT);
        }
        return dataCUDResultVO;
    }

    public static DataCUDResultVO deleteTypeDataProperties(DataCUDInputVO dataCRUDInput){
        DataCUDResultVO dataCUDResultVO =new DataCUDResultVO();
        String discoverSpaceName=dataCRUDInput.getDiscoverSpaceName();
        List<DataPayloadVO> dataPayLoadList=dataCRUDInput.getDataPayload().getData();
        int modifiedDataCount=0;
        if(dataPayLoadList!=null){
            InfoDiscoverSpace targetSpace=null;
            try {
                targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(discoverSpaceName);
                for(DataPayloadVO currentDataPayload:dataPayLoadList){
                    //String payloadType=currentDataPayload.getType();
                    String recordId=currentDataPayload.getRecordId();
                    String propertyNames=currentDataPayload.getPropertyNames();
                    if(recordId!=null&&propertyNames!=null){
                        Measurable targetMeasurable=targetSpace.getMeasurableById(recordId);
                        if(targetMeasurable!=null){
                            String[] propertyNameArray=propertyNames.split(",");
                            for(String currentProperty:propertyNameArray){
                                if(targetMeasurable.hasProperty(currentProperty)){
                                    boolean removeResult=targetMeasurable.removeProperty(currentProperty);
                                    if(removeResult){
                                        modifiedDataCount++;
                                    }
                                }
                            }
                        }
                    }
                }
                if(modifiedDataCount>0){
                    dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.SUCCESS);
                    dataCUDResultVO.setModifiedDataCount(modifiedDataCount);
                }else{
                    dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.DATA_NOT_MODIFIED);
                }
            } catch (InfoDiscoveryEngineRuntimeException e) {
                e.printStackTrace();
                dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.FAILURE);
            } finally {
                if(targetSpace!=null){
                    targetSpace.closeSpace();
                }
            }
        }else {
            dataCUDResultVO.setOperationReturnCode(DataCUDResultVO.operationResultCodeValue.INVALID_INPUT);
        }
        return dataCUDResultVO;
    }

    public static DataRetrieveResultVO queryMeasurable(ExploreParametersVO exploreParametersVO){
        DataRetrieveResultVO dataRetrieveResultVO=new DataRetrieveResultVO();

        if(exploreParametersVO.getTypeName()==null||exploreParametersVO.getType()==null||exploreParametersVO.getDiscoverSpaceName()==null){
            dataRetrieveResultVO.setOperationReturnCode(DataRetrieveResultVO.operationResultCodeValue.INVALID_INPUT);
            return dataRetrieveResultVO;
        }
        String dataTypeKind=exploreParametersVO.getType();
        if(!dataTypeKind.equals(DiscoverSpaceOperationConstant.PAYLOAD_DIMENSION)&
                !dataTypeKind.equals(DiscoverSpaceOperationConstant.PAYLOAD_FACT)&
            !dataTypeKind.equals(DiscoverSpaceOperationConstant.PAYLOAD_RELATION)){
            dataRetrieveResultVO.setOperationReturnCode(DataRetrieveResultVO.operationResultCodeValue.INVALID_INPUT);
            return dataRetrieveResultVO;
        }

        ExploreParameters exploreParameters=new ExploreParameters();
        exploreParameters.setType(exploreParametersVO.getTypeName());

        MeasurableQueryResultSetVO measurableQueryResultSetVO=new MeasurableQueryResultSetVO();
        measurableQueryResultSetVO.setDiscoverSpaceName(exploreParametersVO.getDiscoverSpaceName());
        measurableQueryResultSetVO.setMeasurableName(exploreParametersVO.getTypeName());
        measurableQueryResultSetVO.setCurrentPage(1);

        if(exploreParametersVO.getStartPage()!=0){
            exploreParameters.setStartPage(exploreParametersVO.getStartPage());
        }
        if(exploreParametersVO.getEndPage()!=0){
            exploreParameters.setEndPage(exploreParametersVO.getEndPage());
        }
        if(exploreParametersVO.getPageSize()!=0){
            exploreParameters.setPageSize(exploreParametersVO.getPageSize());
            measurableQueryResultSetVO.setPageSize(exploreParametersVO.getPageSize());
        }else{
            measurableQueryResultSetVO.setPageSize(50);
        }
        if(exploreParametersVO.getResultNumber()!=0){
            exploreParameters.setResultNumber(exploreParametersVO.getResultNumber());
        }
        FilteringItemVO defaultFilteringItemVO=exploreParametersVO.getDefaultFilteringItem();
        if(defaultFilteringItemVO!=null){
            FilteringItem defaultFilteringItem=getFilteringItem(defaultFilteringItemVO);
            exploreParameters.setDefaultFilteringItem(defaultFilteringItem);

            List<FilteringItemVO> andFilteringItemListVO=exploreParametersVO.getAndFilteringItemList();
            if(andFilteringItemListVO!=null&&andFilteringItemListVO.size()>0){
                for(FilteringItemVO currentFilteringItemVO:andFilteringItemListVO){
                    FilteringItem currentFilteringItem=getFilteringItem(currentFilteringItemVO);
                    if(currentFilteringItem!=null){
                        exploreParameters.addFilteringItem(currentFilteringItem, ExploreParameters.FilteringLogic.AND);
                    }
                }
            }

            List<FilteringItemVO> orFilteringItemList=exploreParametersVO.getOrFilteringItemList();
            if(orFilteringItemList!=null&&orFilteringItemList.size()>0){
                for(FilteringItemVO currentFilteringItemVO:orFilteringItemList){
                    FilteringItem currentFilteringItem=getFilteringItem(currentFilteringItemVO);
                    if(currentFilteringItem!=null){
                        exploreParameters.addFilteringItem(currentFilteringItem, ExploreParameters.FilteringLogic.OR);
                    }
                }
            }
        }

        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(exploreParametersVO.getDiscoverSpaceName());
            if(targetSpace!=null){
                InformationExplorer ie=targetSpace.getInformationExplorer();
                if(DiscoverSpaceOperationConstant.PAYLOAD_DIMENSION.equals(dataTypeKind)){
                    if(targetSpace.hasDimensionType(exploreParametersVO.getTypeName())){
                        DimensionType targetDimensionType=targetSpace.getDimensionType(exploreParametersVO.getTypeName());
                        measurableQueryResultSetVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION);
                        List<TypeProperty> typePropertyList=targetDimensionType.getTypeProperties();
                        measurableQueryResultSetVO.setTypeProperties(DiscoverSpaceOperationUtil.loadTypePropertyVOList(typePropertyList));

                        List<Dimension> resultDimensions=ie.discoverDimensions(exploreParameters);
                        List<MeasurableVO> resultMeasurableVOList=new ArrayList<>();
                        if(resultDimensions!=null){
                            for(Measurable currentMeasurable:resultDimensions){
                                MeasurableVO measurableVO =DiscoverSpaceOperationUtil.getMeasurableVO(exploreParametersVO.getDiscoverSpaceName(),currentMeasurable);
                                resultMeasurableVOList.add(measurableVO);
                            }
                        }
                        measurableQueryResultSetVO.setMeasurableValues(resultMeasurableVOList);
                        measurableQueryResultSetVO.setMeasurableRecordCount(resultDimensions.size());
                        dataRetrieveResultVO.setMeasurableQueryResultSet(measurableQueryResultSetVO);
                        dataRetrieveResultVO.setOperationReturnCode(DataRetrieveResultVO.operationResultCodeValue.SUCCESS);
                    }else{
                        dataRetrieveResultVO.setOperationReturnCode(DataRetrieveResultVO.operationResultCodeValue.INVALID_INPUT);
                    }
                }
                if(DiscoverSpaceOperationConstant.PAYLOAD_FACT.equals(dataTypeKind)){
                    if(targetSpace.hasFactType(exploreParametersVO.getTypeName())){
                        FactType targetFactType=targetSpace.getFactType(exploreParametersVO.getTypeName());
                        measurableQueryResultSetVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_FACT);
                        List<TypeProperty> typePropertyList=targetFactType.getTypeProperties();
                        measurableQueryResultSetVO.setTypeProperties(DiscoverSpaceOperationUtil.loadTypePropertyVOList(typePropertyList));

                        List<Fact> resultFacts=ie.discoverFacts(exploreParameters);
                        List<MeasurableVO> resultMeasurableVOList=new ArrayList<>();
                        if(resultFacts!=null){
                            for(Measurable currentMeasurable:resultFacts){
                                MeasurableVO measurableVO =DiscoverSpaceOperationUtil.getMeasurableVO(exploreParametersVO.getDiscoverSpaceName(),currentMeasurable);
                                resultMeasurableVOList.add(measurableVO);
                            }
                        }
                        measurableQueryResultSetVO.setMeasurableValues(resultMeasurableVOList);
                        measurableQueryResultSetVO.setMeasurableRecordCount(resultFacts.size());
                        dataRetrieveResultVO.setMeasurableQueryResultSet(measurableQueryResultSetVO);
                        dataRetrieveResultVO.setOperationReturnCode(DataRetrieveResultVO.operationResultCodeValue.SUCCESS);
                    }else{
                        dataRetrieveResultVO.setOperationReturnCode(DataRetrieveResultVO.operationResultCodeValue.INVALID_INPUT);
                    }
                }
                if(DiscoverSpaceOperationConstant.PAYLOAD_RELATION.equals(dataTypeKind)){
                    if(targetSpace.hasRelationType(exploreParametersVO.getTypeName())){
                        RelationType targetRelationType=targetSpace.getRelationType(exploreParametersVO.getTypeName());
                        measurableQueryResultSetVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_RELATION);
                        List<TypeProperty> typePropertyList=targetRelationType.getTypeProperties();
                        measurableQueryResultSetVO.setTypeProperties(DiscoverSpaceOperationUtil.loadTypePropertyVOList(typePropertyList));

                        List<Relation> resultRelations=ie.discoverRelations(exploreParameters);
                        List<MeasurableVO> resultMeasurableVOList=new ArrayList<>();
                        if(resultRelations!=null){
                            for(Measurable currentMeasurable:resultRelations){
                                MeasurableVO measurableVO =DiscoverSpaceOperationUtil.getMeasurableVO(exploreParametersVO.getDiscoverSpaceName(),currentMeasurable);
                                resultMeasurableVOList.add(measurableVO);
                            }
                        }
                        measurableQueryResultSetVO.setMeasurableValues(resultMeasurableVOList);
                        measurableQueryResultSetVO.setMeasurableRecordCount(resultRelations.size());
                        dataRetrieveResultVO.setMeasurableQueryResultSet(measurableQueryResultSetVO);
                        dataRetrieveResultVO.setOperationReturnCode(DataRetrieveResultVO.operationResultCodeValue.SUCCESS);
                    }else{
                        dataRetrieveResultVO.setOperationReturnCode(DataRetrieveResultVO.operationResultCodeValue.INVALID_INPUT);
                    }
                }
            }else{
                dataRetrieveResultVO.setOperationReturnCode(DataRetrieveResultVO.operationResultCodeValue.INVALID_INPUT);
                return dataRetrieveResultVO;
            }
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
        return dataRetrieveResultVO;
    }

    private static FilteringItem getFilteringItem(FilteringItemVO filteringItemVO){
        FilteringItem targetFilteringItem=null;

        String attributeName=filteringItemVO.getAttributeName();
        String attributeType=filteringItemVO.getAttributeType()!=null?filteringItemVO.getAttributeType():"String";
        boolean isReverseCondition=filteringItemVO.getReverseCondition();
        Object attributeValue=getAttributeValue(attributeType,filteringItemVO.getAttributeValue());

        FilteringItemVO.filteringItemType targetFilteringItemType=filteringItemVO.getFilteringItemType();
        switch(targetFilteringItemType){
            case BETWEEN:
                Object fromValue=getAttributeValue(attributeType,filteringItemVO.getAttributeFromValue());
                Object toValue=getAttributeValue(attributeType,filteringItemVO.getAttributeToValue());
                BetweenFilteringItem betweenFilteringItem=new BetweenFilteringItem(attributeName,fromValue,toValue);
                if(isReverseCondition){
                    betweenFilteringItem.reverseCondition();
                }
                targetFilteringItem=betweenFilteringItem;
                break;
            case EQUAL:
                EqualFilteringItem equalFilteringItem=new EqualFilteringItem(attributeName,attributeValue);
                if(isReverseCondition){
                    equalFilteringItem.reverseCondition();
                }
                targetFilteringItem=equalFilteringItem;
                break;
            case GREATER_THAN_EQUAL:
                GreaterThanEqualFilteringItem greaterThanEqualFilteringItem=new GreaterThanEqualFilteringItem(attributeName,attributeValue);
                if(isReverseCondition){
                    greaterThanEqualFilteringItem.reverseCondition();
                }
                targetFilteringItem=greaterThanEqualFilteringItem;
                break;
            case GREATER_THAN:
                GreaterThanFilteringItem greaterThanFilteringItem=new GreaterThanFilteringItem(attributeName,attributeValue);
                if(isReverseCondition){
                    greaterThanFilteringItem.reverseCondition();
                }
                targetFilteringItem=greaterThanFilteringItem;
                break;
            case IN_VALUE:
                List<Object> attributeValues=filteringItemVO.getAttributeValues();
                List<Object> realAttributeValuesList=new ArrayList<>();
                for(Object currentObject:attributeValues){
                    Object realValueObj=getAttributeValue(attributeType,currentObject);
                    if(realValueObj!=null){
                        realAttributeValuesList.add(realValueObj);
                    }
                }
                InValueFilteringItem inValueFilteringItem=new InValueFilteringItem(attributeName,realAttributeValuesList);
                if(isReverseCondition){
                    inValueFilteringItem.reverseCondition();
                }
                targetFilteringItem=inValueFilteringItem;
                break;
            case LESS_THAN_EQUAL:
                LessThanEqualFilteringItem lessThanEqualFilteringItem=new LessThanEqualFilteringItem(attributeName,attributeValue);
                if(isReverseCondition){
                    lessThanEqualFilteringItem.reverseCondition();
                }
                targetFilteringItem=lessThanEqualFilteringItem;
                break;
            case LESS_THAN:
                LessThanFilteringItem lessThanFilteringItem=new LessThanFilteringItem(attributeName,attributeValue);
                if(isReverseCondition){
                    lessThanFilteringItem.reverseCondition();
                }
                targetFilteringItem=lessThanFilteringItem;
                break;
            case NOT_EQUAL:
                NotEqualFilteringItem notEqualFilteringItem=new NotEqualFilteringItem(attributeName,attributeValue);
                if(isReverseCondition){
                    notEqualFilteringItem.reverseCondition();
                }
                targetFilteringItem=notEqualFilteringItem;
                break;
            case NULL_VALUE:
                NullValueFilteringItem nullValueFilteringItem=new NullValueFilteringItem(attributeName);
                if(isReverseCondition){
                    nullValueFilteringItem.reverseCondition();
                }
                targetFilteringItem=nullValueFilteringItem;
                break;
            case REGULAR_MATCH:
                RegularMatchFilteringItem regularMatchFilteringItem=new RegularMatchFilteringItem(attributeName,attributeValue.toString());
                if(isReverseCondition){
                    regularMatchFilteringItem.reverseCondition();
                }
                targetFilteringItem=regularMatchFilteringItem;
                break;
            case SIMILAR:
                FilteringItemVO.matchingType similarMatchType=filteringItemVO.getMatchingType();
                String matchingTypeString=similarMatchType.toString();
                SimilarFilteringItem similarFilteringItem=null;
                if(matchingTypeString.equals(""+SimilarFilteringItem.MatchingType.BeginWith)){
                    similarFilteringItem=new SimilarFilteringItem(attributeName,attributeValue.toString(),SimilarFilteringItem.MatchingType.BeginWith);
                }
                if(matchingTypeString.equals(""+SimilarFilteringItem.MatchingType.Contain)){
                    similarFilteringItem=new SimilarFilteringItem(attributeName,attributeValue.toString(),SimilarFilteringItem.MatchingType.Contain);
                }

                if(matchingTypeString.equals(""+SimilarFilteringItem.MatchingType.EndWith)){
                    similarFilteringItem=new SimilarFilteringItem(attributeName,attributeValue.toString(),SimilarFilteringItem.MatchingType.EndWith);
                }
                if(similarFilteringItem!=null){
                    if(isReverseCondition){
                        similarFilteringItem.reverseCondition();
                    }
                    targetFilteringItem=similarFilteringItem;
                }
                break;
        }
        return targetFilteringItem;
    }

    private static Object getAttributeValue(String attributeType, Object attributeValueObj){
        if(attributeValueObj==null){
            return null;
        }
        Object attributeValue=null;
        if("String".equals(attributeType)){
            attributeValue=attributeValueObj.toString();
        }
        if("Int".equals(attributeType)){
            attributeValue=(Integer)attributeValueObj;
        }
        if("Long".equals(attributeType)){
            Long targetPropertyValue=null;
            if(attributeValueObj instanceof Integer){
                targetPropertyValue=new Long((Integer)attributeValueObj);
            }else if(attributeValueObj instanceof Long){
                targetPropertyValue=(Long)attributeValueObj;
            }
            attributeValue=targetPropertyValue;
        }
        if("Double".equals(attributeType)){
            attributeValue=(Double)attributeValueObj;
        }
        if("Float".equals(attributeType)){
            Float targetPropertyValue=null;
            if(attributeValueObj instanceof Double){
                targetPropertyValue=new Float((Double)attributeValueObj);
            }else if(attributeValueObj instanceof Float){
                targetPropertyValue=(Float)attributeValueObj;
            }
            attributeValue=targetPropertyValue;
        }
        if("Boolean".equals(attributeType)){
            attributeValue=(Boolean)attributeValueObj;
        }
        if("Date".equals(attributeType)){
            Date dateValue=new Date((Long)attributeValueObj);
            attributeValue=dateValue;
        }
        if("Short".equals(attributeType)){
            Short targetPropertyValue=null;
            if(attributeValueObj instanceof Integer){
                targetPropertyValue=((Integer)attributeValueObj).shortValue();
            }else if(attributeValueObj instanceof Short){
                targetPropertyValue=(Short)attributeValueObj;
            }
            attributeValue=targetPropertyValue;
        }
        return attributeValue;
    }
}
