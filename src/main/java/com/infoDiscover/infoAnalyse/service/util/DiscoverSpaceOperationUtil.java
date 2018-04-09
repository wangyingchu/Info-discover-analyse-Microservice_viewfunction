package com.infoDiscover.infoAnalyse.service.util;

import com.infoDiscover.infoAnalyse.service.restful.vo.*;
import com.infoDiscover.infoAnalyse.service.restful.vo.typeInstance.*;
import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationType;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.SQLBuilder;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.infoDiscoverEngine.util.helper.DataTypeStatisticMetrics;
import com.infoDiscover.infoDiscoverEngine.util.helper.DiscoverSpaceStatisticHelper;
import com.infoDiscover.infoDiscoverEngine.util.helper.DiscoverSpaceStatisticMetrics;

import java.util.*;

/**
 * Created by wangychu on 2/15/17.
 */
public class DiscoverSpaceOperationUtil {

    public static TypeInstanceRelationsCycleVO getRelationableRelationsCycleInfoById(String spaceName, String relationableId){
        TypeInstanceRelationsCycleVO typeInstanceRelationsCycleVO=new TypeInstanceRelationsCycleVO();
        List<RelationInfoVO> resultRelationValueList=new ArrayList<RelationInfoVO>();
        typeInstanceRelationsCycleVO.setRelationsInfo(resultRelationValueList);
        RelationableValueVO sourceTypeInstanceVO=new RelationableValueVO();
        typeInstanceRelationsCycleVO.setSourceTypeInstance(sourceTypeInstanceVO);

        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            Measurable targetMeasurable= targetSpace.getMeasurableById(relationableId);
            List<Relation> relationsList=null;
            Relationable targetRelationable=null;

            if(targetMeasurable instanceof Dimension){
                targetRelationable=(Dimension)targetMeasurable;
                sourceTypeInstanceVO.setDiscoverSpaceName(spaceName);
                sourceTypeInstanceVO.setId(targetRelationable.getId());
                sourceTypeInstanceVO.setRelationableTypeName(((Dimension)targetRelationable).getType());
                sourceTypeInstanceVO.setRelationableTypeAliasName(getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION,((Dimension)targetRelationable).getType()));
                sourceTypeInstanceVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION);
            }
            if(targetMeasurable instanceof Fact){
                targetRelationable=(Fact)targetMeasurable;
                sourceTypeInstanceVO.setDiscoverSpaceName(spaceName);
                sourceTypeInstanceVO.setId(targetRelationable.getId());
                sourceTypeInstanceVO.setRelationableTypeName(((Fact)targetRelationable).getType());
                sourceTypeInstanceVO.setRelationableTypeAliasName(getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_FACT,((Fact)targetRelationable).getType()));
                sourceTypeInstanceVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_FACT);
            }
            if(targetRelationable!=null){
                relationsList= targetRelationable.getAllRelations();
            }
            if(relationsList!=null){
                for(Relation currentRelation:relationsList){
                    RelationInfoVO currentRelationValueVO=new RelationInfoVO();
                    currentRelationValueVO.setId(currentRelation.getId());
                    currentRelationValueVO.setRelationTypeName(currentRelation.getType());
                    currentRelationValueVO.setDiscoverSpaceName(spaceName);
                    currentRelationValueVO.setRelationTypeAliasName(getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_RELATION,(currentRelation.getType())));

                    Relationable fromRelationable=currentRelation.getFromRelationable();
                    if(fromRelationable!=null){
                        RelationableValueVO fromRelationableValueVO=new RelationableValueVO();
                        fromRelationableValueVO.setDiscoverSpaceName(spaceName);
                        fromRelationableValueVO.setId(fromRelationable.getId());
                        if(fromRelationable instanceof Dimension){
                            Dimension dimensionRelationable=(Dimension)fromRelationable;
                            fromRelationableValueVO.setRelationableTypeName(dimensionRelationable.getType());
                            fromRelationableValueVO.setRelationableTypeAliasName(getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION,(dimensionRelationable.getType())));
                            fromRelationableValueVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION);
                        }
                        if(fromRelationable instanceof Fact){
                            Fact factRelationable=(Fact)fromRelationable;
                            fromRelationableValueVO.setRelationableTypeName(factRelationable.getType());
                            fromRelationableValueVO.setRelationableTypeAliasName(getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_FACT,(factRelationable.getType())));
                            fromRelationableValueVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_FACT);
                        }
                        currentRelationValueVO.setFromRelationable(fromRelationableValueVO);
                    }

                    Relationable toRelationable=currentRelation.getToRelationable();
                    if(toRelationable!=null){
                        RelationableValueVO toRelationableValueVO=new RelationableValueVO();
                        toRelationableValueVO.setDiscoverSpaceName(spaceName);
                        toRelationableValueVO.setId(toRelationable.getId());
                        if(toRelationable instanceof Dimension){
                            Dimension dimensionRelationable=(Dimension)toRelationable;
                            toRelationableValueVO.setRelationableTypeName(dimensionRelationable.getType());
                            toRelationableValueVO.setRelationableTypeAliasName(getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION,(dimensionRelationable.getType())));
                            toRelationableValueVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION);
                        }
                        if(toRelationable instanceof Fact){
                            Fact factRelationable=(Fact)toRelationable;
                            toRelationableValueVO.setRelationableTypeName(factRelationable.getType());
                            toRelationableValueVO.setRelationableTypeAliasName(getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_FACT,(factRelationable.getType())));
                            toRelationableValueVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_FACT);
                        }
                        currentRelationValueVO.setToRelationable(toRelationableValueVO);
                    }
                    resultRelationValueList.add(currentRelationValueVO);
                }
            }
        } finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
        return typeInstanceRelationsCycleVO;
    }

    public static TypeInstanceRelationsDetailVO getRelationableRelationsDetailInfoById(String spaceName, String relationableId){
        TypeInstanceRelationsDetailVO typeInstanceRelationsDetailVO=new TypeInstanceRelationsDetailVO();
        List<RelationDetailInfoVO> resultRelationValueList=new ArrayList<RelationDetailInfoVO>();
        typeInstanceRelationsDetailVO.setRelationsInfo(resultRelationValueList);
        RelationableValueDetailVO sourceTypeInstanceVO=new RelationableValueDetailVO();
        typeInstanceRelationsDetailVO.setSourceTypeInstance(sourceTypeInstanceVO);

        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            Measurable targetMeasurable= targetSpace.getMeasurableById(relationableId);
            List<Relation> relationsList=null;
            Relationable targetRelationable=null;

            if(targetMeasurable instanceof Dimension){
                targetRelationable=(Dimension)targetMeasurable;
                sourceTypeInstanceVO.setDiscoverSpaceName(spaceName);
                sourceTypeInstanceVO.setId(targetRelationable.getId());
                sourceTypeInstanceVO.setRelationableTypeName(((Dimension)targetRelationable).getType());
                sourceTypeInstanceVO.setRelationableTypeAliasName(getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION,((Dimension)targetRelationable).getType()));
                sourceTypeInstanceVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION);
            }
            if(targetMeasurable instanceof Fact){
                targetRelationable=(Fact)targetMeasurable;
                sourceTypeInstanceVO.setDiscoverSpaceName(spaceName);
                sourceTypeInstanceVO.setId(targetRelationable.getId());
                sourceTypeInstanceVO.setRelationableTypeName(((Fact)targetRelationable).getType());
                sourceTypeInstanceVO.setRelationableTypeAliasName(getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_FACT,((Fact)targetRelationable).getType()));
                sourceTypeInstanceVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_FACT);
            }

            List measurableProperties=targetMeasurable.getProperties();
            if(measurableProperties!=null){
                List<PropertyVO> propertiesValueList=loadMeasurablePropertyVOList(spaceName,targetMeasurable,measurableProperties);
                sourceTypeInstanceVO.setPropertiesValueList(propertiesValueList);
            }

            if(targetRelationable!=null){
                relationsList= targetRelationable.getAllRelations();
            }
            if(relationsList!=null){
                for(Relation currentRelation:relationsList){
                    RelationDetailInfoVO currentRelationValueVO=new RelationDetailInfoVO();
                    currentRelationValueVO.setId(currentRelation.getId());
                    currentRelationValueVO.setRelationTypeName(currentRelation.getType());
                    currentRelationValueVO.setRelationTypeAliasName(getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_RELATION,currentRelation.getType()));
                    currentRelationValueVO.setDiscoverSpaceName(spaceName);

                    List relationProperties=currentRelation.getProperties();
                    if(relationProperties!=null){
                        List<PropertyVO> propertiesValueList=loadMeasurablePropertyVOList(spaceName,currentRelation,relationProperties);
                        currentRelationValueVO.setPropertiesValueList(propertiesValueList);
                    }

                    Relationable fromRelationable=currentRelation.getFromRelationable();
                    if(fromRelationable!=null){
                        RelationableValueDetailVO fromRelationableValueVO=new RelationableValueDetailVO();
                        fromRelationableValueVO.setDiscoverSpaceName(spaceName);
                        fromRelationableValueVO.setId(fromRelationable.getId());
                        if(fromRelationable instanceof Dimension){
                            Dimension dimensionRelationable=(Dimension)fromRelationable;
                            fromRelationableValueVO.setRelationableTypeName(dimensionRelationable.getType());
                            fromRelationableValueVO.setRelationableTypeAliasName(getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION,dimensionRelationable.getType()));
                            fromRelationableValueVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION);
                        }
                        if(fromRelationable instanceof Fact){
                            Fact factRelationable=(Fact)fromRelationable;
                            fromRelationableValueVO.setRelationableTypeName(factRelationable.getType());
                            fromRelationableValueVO.setRelationableTypeAliasName(getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_FACT,factRelationable.getType()));
                            fromRelationableValueVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_FACT);
                        }

                        List fromProperties=fromRelationable.getProperties();
                        if(fromProperties!=null){
                            List<PropertyVO> propertiesValueList=loadMeasurablePropertyVOList(spaceName,fromRelationable,fromProperties);
                            fromRelationableValueVO.setPropertiesValueList(propertiesValueList);
                        }
                        currentRelationValueVO.setFromRelationable(fromRelationableValueVO);
                    }

                    Relationable toRelationable=currentRelation.getToRelationable();
                    if(toRelationable!=null){
                        RelationableValueDetailVO toRelationableValueVO=new RelationableValueDetailVO();
                        toRelationableValueVO.setDiscoverSpaceName(spaceName);
                        toRelationableValueVO.setId(toRelationable.getId());
                        if(toRelationable instanceof Dimension){
                            Dimension dimensionRelationable=(Dimension)toRelationable;
                            toRelationableValueVO.setRelationableTypeName(dimensionRelationable.getType());
                            toRelationableValueVO.setRelationableTypeAliasName(getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION,dimensionRelationable.getType()));
                            toRelationableValueVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION);
                        }
                        if(toRelationable instanceof Fact){
                            Fact factRelationable=(Fact)toRelationable;
                            toRelationableValueVO.setRelationableTypeName(factRelationable.getType());
                            toRelationableValueVO.setRelationableTypeAliasName(getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_FACT,factRelationable.getType()));
                            toRelationableValueVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_FACT);
                        }
                        List toProperties=toRelationable.getProperties();
                        if(toProperties!=null){
                            List<PropertyVO> propertiesValueList=loadMeasurablePropertyVOList(spaceName,toRelationable,toProperties);
                            toRelationableValueVO.setPropertiesValueList(propertiesValueList);
                        }
                        currentRelationValueVO.setToRelationable(toRelationableValueVO);
                    }

                    resultRelationValueList.add(currentRelationValueVO);
                }
            }
        } finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
        return typeInstanceRelationsDetailVO;
    }

    public static RelationDetailInfoVO getRelationDetailInfoById(String spaceName, String relationId){
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            RelationDetailInfoVO targetRelationValueVO=getRelationDetailInfoById(targetSpace,relationId);
            return targetRelationValueVO;
        } finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
    }

    private static RelationDetailInfoVO getRelationDetailInfoById(InfoDiscoverSpace targetSpace, String relationId){
        Relation targetRelation=targetSpace.getRelationById(relationId);
        if(targetRelation==null){
            return null;
        }

        RelationDetailInfoVO targetRelationValueVO=new RelationDetailInfoVO();
        targetRelationValueVO.setId(targetRelation.getId());
        targetRelationValueVO.setRelationTypeName(targetRelation.getType());
        targetRelationValueVO.setRelationTypeAliasName(getTypeKindAliasName(targetSpace.getSpaceName(),DiscoverSpaceOperationConstant.TYPEKIND_RELATION,targetRelation.getType()));
        targetRelationValueVO.setDiscoverSpaceName(targetSpace.getSpaceName());

        List relationProperties=targetRelation.getProperties();
        if(relationProperties!=null){
            List<PropertyVO> propertiesValueList=loadMeasurablePropertyVOList(targetSpace.getSpaceName(),targetRelation,relationProperties);
            targetRelationValueVO.setPropertiesValueList(propertiesValueList);
        }

        Relationable fromRelationable=targetRelation.getFromRelationable();
        if(fromRelationable!=null){
            RelationableValueDetailVO fromRelationableValueVO=new RelationableValueDetailVO();
            fromRelationableValueVO.setDiscoverSpaceName(targetSpace.getSpaceName());
            fromRelationableValueVO.setId(fromRelationable.getId());
            if(fromRelationable instanceof Dimension){
                Dimension dimensionRelationable=(Dimension)fromRelationable;
                fromRelationableValueVO.setRelationableTypeName(dimensionRelationable.getType());
                fromRelationableValueVO.setRelationableTypeAliasName(getTypeKindAliasName(targetSpace.getSpaceName(),DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION,dimensionRelationable.getType()));
                fromRelationableValueVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION);
            }
            if(fromRelationable instanceof Fact){
                Fact factRelationable=(Fact)fromRelationable;
                fromRelationableValueVO.setRelationableTypeName(factRelationable.getType());
                fromRelationableValueVO.setRelationableTypeAliasName(getTypeKindAliasName(targetSpace.getSpaceName(),DiscoverSpaceOperationConstant.TYPEKIND_FACT,factRelationable.getType()));
                fromRelationableValueVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_FACT);
            }

            List fromProperties=fromRelationable.getProperties();
            if(fromProperties!=null){
                List<PropertyVO> propertiesValueList=loadMeasurablePropertyVOList(targetSpace.getSpaceName(),fromRelationable,fromProperties);
                fromRelationableValueVO.setPropertiesValueList(propertiesValueList);
            }
            targetRelationValueVO.setFromRelationable(fromRelationableValueVO);
        }

        Relationable toRelationable=targetRelation.getToRelationable();
        if(toRelationable!=null) {
            RelationableValueDetailVO toRelationableValueVO = new RelationableValueDetailVO();
            toRelationableValueVO.setDiscoverSpaceName(targetSpace.getSpaceName());
            toRelationableValueVO.setId(toRelationable.getId());
            if (toRelationable instanceof Dimension) {
                Dimension dimensionRelationable = (Dimension) toRelationable;
                toRelationableValueVO.setRelationableTypeName(dimensionRelationable.getType());
                toRelationableValueVO.setRelationableTypeAliasName(getTypeKindAliasName(targetSpace.getSpaceName(),DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION,dimensionRelationable.getType()));
                toRelationableValueVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION);
            }
            if (toRelationable instanceof Fact) {
                Fact factRelationable = (Fact) toRelationable;
                toRelationableValueVO.setRelationableTypeName(factRelationable.getType());
                toRelationableValueVO.setRelationableTypeAliasName(getTypeKindAliasName(targetSpace.getSpaceName(),DiscoverSpaceOperationConstant.TYPEKIND_FACT,factRelationable.getType()));
                toRelationableValueVO.setRelationableTypeKind(DiscoverSpaceOperationConstant.TYPEKIND_FACT);
            }
            List toProperties = toRelationable.getProperties();
            if (toProperties != null) {
                List<PropertyVO> propertiesValueList = loadMeasurablePropertyVOList(targetSpace.getSpaceName(),toRelationable,toProperties);
                toRelationableValueVO.setPropertiesValueList(propertiesValueList);
            }
            targetRelationValueVO.setToRelationable(toRelationableValueVO);
        }
        return targetRelationValueVO;
    }

    public static List<FactTypeInfoVO> getDiscoverSpaceFactTypesDataCountInfo(String spaceName){
        List<FactTypeInfoVO> factTypeInfoVOList=new ArrayList<>();
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);

            List<String> factTypesList=targetSpace.getFactTypesList();
            if(factTypesList!=null) {
                DiscoverSpaceStatisticHelper discoverSpaceStatisticHelper = DiscoverEngineComponentFactory.getDiscoverSpaceStatisticHelper();
                DiscoverSpaceStatisticMetrics discoverSpaceStatisticMetrics = discoverSpaceStatisticHelper.getDiscoverSpaceStatisticMetrics(spaceName);
                List<DataTypeStatisticMetrics> factsStatisticMetrics = discoverSpaceStatisticMetrics.getFactsStatisticMetrics();
                for(String currentFactTypeName:factTypesList){
                    FactTypeInfoVO currentFactTypeInfoVO=new FactTypeInfoVO();
                    currentFactTypeInfoVO.setTypeName(currentFactTypeName);
                    currentFactTypeInfoVO.setTypeDataRecordCount(getFactTypeRecordNumber(currentFactTypeName,factsStatisticMetrics));
                    factTypeInfoVOList.add(currentFactTypeInfoVO);
                }
            }
        }finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
        return factTypeInfoVOList;
    }

    private static long getFactTypeRecordNumber(String targetFactTypeName, List<DataTypeStatisticMetrics> factsStatisticMetricsList ){
        for(DataTypeStatisticMetrics currentMetrics:factsStatisticMetricsList){
            String factTypeName=currentMetrics.getDataTypeName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_FACT,"");
            if(factTypeName.equals(targetFactTypeName)){
                return currentMetrics.getTypeDataCount();
            }
        }
        return 0;
    }

    public List<DimensionTypeInfoVO> getDiscoverSpaceDimensionTypesTreeDataCountInfo(String spaceName){
        List<DimensionTypeInfoVO> dimensionTypeInfoVOList=new ArrayList<>();
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);

            List<String> rootDimensionTypesList=targetSpace.getRootDimensionTypesList();
            if(rootDimensionTypesList!=null){
                DiscoverSpaceStatisticHelper discoverSpaceStatisticHelper= DiscoverEngineComponentFactory.getDiscoverSpaceStatisticHelper();
                DiscoverSpaceStatisticMetrics discoverSpaceStatisticMetrics=discoverSpaceStatisticHelper.getDiscoverSpaceStatisticMetrics(spaceName);
                List<DataTypeStatisticMetrics> dimensionsStatisticMetrics=discoverSpaceStatisticMetrics.getDimensionsStatisticMetrics();
                for(String currentDimensionTypeName:rootDimensionTypesList){
                    DimensionType currentDimensionType=targetSpace.getDimensionType(currentDimensionTypeName);
                    DimensionTypeInfoVO currentDimensionTypeInfoVO=new DimensionTypeInfoVO();
                    currentDimensionTypeInfoVO.setTypeName(currentDimensionTypeName);
                    currentDimensionTypeInfoVO.setDescendantDimensionTypesNumber(currentDimensionType.getDescendantDimensionTypes().size());
                    currentDimensionTypeInfoVO.setTypeDataRecordCount(getDimensionTypeRecordNumber(currentDimensionTypeName,dimensionsStatisticMetrics));
                    List<DimensionTypeInfoVO> childDimensionTypeInfoVOList=new ArrayList<>();
                    currentDimensionTypeInfoVO.setChildDimensionTypeInfosVOList(childDimensionTypeInfoVOList);
                    List<DimensionType> childDimensionTypeList=currentDimensionType.getChildDimensionTypes();
                    if(childDimensionTypeList!=null){
                        for(DimensionType currentRootDimensionType:childDimensionTypeList){
                            DimensionTypeInfoVO currentChildDimensionTypeInfoVO=getDimensionTypeInfoVO(currentRootDimensionType,dimensionsStatisticMetrics);
                            childDimensionTypeInfoVOList.add(currentChildDimensionTypeInfoVO);
                        }
                    }
                    dimensionTypeInfoVOList.add(currentDimensionTypeInfoVO);
                }
            }
        }finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
        return dimensionTypeInfoVOList;
    }

    private static long getDimensionTypeRecordNumber(String targetDimensionTypeName, List<DataTypeStatisticMetrics> dimensionsStatisticMetricsList ){
        for(DataTypeStatisticMetrics currentMetrics:dimensionsStatisticMetricsList){
            String dimensionTypeName=currentMetrics.getDataTypeName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION,"");
            if(dimensionTypeName.equals(targetDimensionTypeName)){
                return currentMetrics.getTypeDataCount();
            }
        }
        return 0;
    }

    private static DimensionTypeInfoVO getDimensionTypeInfoVO(DimensionType targetDimensionType,List<DataTypeStatisticMetrics> dimensionsStatisticMetrics){
        DimensionTypeInfoVO currentDimensionTypeInfoVO=new DimensionTypeInfoVO();
        currentDimensionTypeInfoVO.setTypeName(targetDimensionType.getTypeName());
        currentDimensionTypeInfoVO.setDescendantDimensionTypesNumber(targetDimensionType.getDescendantDimensionTypes().size());
        currentDimensionTypeInfoVO.setTypeDataRecordCount(getDimensionTypeRecordNumber(targetDimensionType.getTypeName(),dimensionsStatisticMetrics));
        List<DimensionTypeInfoVO> childDimensionTypeInfoVOList=new ArrayList<>();
        currentDimensionTypeInfoVO.setChildDimensionTypeInfosVOList(childDimensionTypeInfoVOList);
        List<DimensionType> childDimensionTypeList=targetDimensionType.getChildDimensionTypes();
        if(childDimensionTypeList!=null){
            for(DimensionType currentChildDimensionType:childDimensionTypeList){
                DimensionTypeInfoVO currentChildDimensionTypeInfoVO=getDimensionTypeInfoVO(currentChildDimensionType,dimensionsStatisticMetrics);
                childDimensionTypeInfoVOList.add(currentChildDimensionTypeInfoVO);
            }
        }
        return currentDimensionTypeInfoVO;
    }

    public static List<RelationTypeInfoVO> getDiscoverSpaceRelationTypesTreeDataCountInfo(String spaceName){
        List<RelationTypeInfoVO> relationTypeInfoVOList=new ArrayList<>();
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            List<String> rootRelationTypesList=targetSpace.getRootRelationTypesList();
            if(rootRelationTypesList!=null){
                DiscoverSpaceStatisticHelper discoverSpaceStatisticHelper= DiscoverEngineComponentFactory.getDiscoverSpaceStatisticHelper();
                DiscoverSpaceStatisticMetrics discoverSpaceStatisticMetrics=discoverSpaceStatisticHelper.getDiscoverSpaceStatisticMetrics(spaceName);
                List<DataTypeStatisticMetrics> relationsStatisticMetrics=discoverSpaceStatisticMetrics.getRelationsStatisticMetrics();

                for(String currentRelationTypeName:rootRelationTypesList){
                    RelationType currentRelationType=targetSpace.getRelationType(currentRelationTypeName);
                    RelationTypeInfoVO currentRelationTypeInfoVO=new RelationTypeInfoVO();
                    currentRelationTypeInfoVO.setTypeName(currentRelationTypeName);
                    currentRelationTypeInfoVO.setDescendantRelationTypesNumber(currentRelationType.getDescendantRelationTypes().size());
                    currentRelationTypeInfoVO.setTypeDataRecordCount(getRelationTypeRecordNumber(currentRelationTypeName,relationsStatisticMetrics));
                    List<RelationTypeInfoVO> childRelationTypeInfoVOList=new ArrayList<>();
                    currentRelationTypeInfoVO.setChildRelationTypeInfosVOList(childRelationTypeInfoVOList);
                    List<RelationType> childRelationTypeList=currentRelationType.getChildRelationTypes();
                    if(childRelationTypeList!=null){
                        for(RelationType currentRootRelationType:childRelationTypeList){
                            RelationTypeInfoVO currentChildRelationTypeInfoVO=getRelationTypeInfoVO(currentRootRelationType,relationsStatisticMetrics);
                            childRelationTypeInfoVOList.add(currentChildRelationTypeInfoVO);
                        }
                    }
                    relationTypeInfoVOList.add(currentRelationTypeInfoVO);
                }
            }
        }finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
        return relationTypeInfoVOList;
    }

    private static long getRelationTypeRecordNumber(String targetRelationTypeName, List<DataTypeStatisticMetrics> relationsStatisticMetricsList ){
        for(DataTypeStatisticMetrics currentMetrics:relationsStatisticMetricsList){
            String relationTypeName=currentMetrics.getDataTypeName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION,"");
            if(relationTypeName.equals(targetRelationTypeName)){
                return currentMetrics.getTypeDataCount();
            }
        }
        return 0;
    }

    private static RelationTypeInfoVO getRelationTypeInfoVO(RelationType targetRelationType,List<DataTypeStatisticMetrics> relationsStatisticMetrics){
        RelationTypeInfoVO currentRelationTypeInfoVO=new RelationTypeInfoVO();
        currentRelationTypeInfoVO.setTypeName(targetRelationType.getTypeName());
        currentRelationTypeInfoVO.setDescendantRelationTypesNumber(targetRelationType.getDescendantRelationTypes().size());
        currentRelationTypeInfoVO.setTypeDataRecordCount(getRelationTypeRecordNumber(targetRelationType.getTypeName(),relationsStatisticMetrics));
        List<RelationTypeInfoVO> childRelationTypeInfoVOList=new ArrayList<>();
        currentRelationTypeInfoVO.setChildRelationTypeInfosVOList(childRelationTypeInfoVOList);
        List<RelationType> childRelationTypeList=targetRelationType.getChildRelationTypes();
        if(childRelationTypeList!=null){
            for(RelationType currentChildRelationType:childRelationTypeList){
                RelationTypeInfoVO currentChildRelationTypeInfoVO=getRelationTypeInfoVO(currentChildRelationType,relationsStatisticMetrics);
                childRelationTypeInfoVOList.add(currentChildRelationTypeInfoVO);
            }
        }
        return currentRelationTypeInfoVO;
    }

    public static List<TypePropertyVO> loadTypePropertyVOList(List<TypeProperty> typePropertyList){
        List<TypePropertyVO> typePropertyVOList=new ArrayList<>();
        if(typePropertyList!=null){
            for(TypeProperty currentTypeProperty:typePropertyList){
                TypePropertyVO currentTypePropertyVO=new TypePropertyVO();
                currentTypePropertyVO.setPropertyName(currentTypeProperty.getPropertyName());
                currentTypePropertyVO.setPropertyType(""+currentTypeProperty.getPropertyType());
                currentTypePropertyVO.setPropertySourceOwner(currentTypeProperty.getPropertySourceOwner());
                currentTypePropertyVO.setMandatoryProperty(currentTypeProperty.isMandatory());
                currentTypePropertyVO.setNullableProperty(currentTypeProperty.isNullable());
                currentTypePropertyVO.setReadOnlyProperty(currentTypeProperty.isReadOnly());
                typePropertyVOList.add(currentTypePropertyVO);
            }
        }
        return typePropertyVOList;
    }

    public static DimensionTypeSummaryVO getDimensionTypeDetail(String spaceName,String dimensionTypeName){
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean dimensionTypeExist=targetSpace.hasDimensionType(dimensionTypeName);
            if(dimensionTypeExist){
                DimensionType targetDimensionType=targetSpace.getDimensionType(dimensionTypeName);
                List<TypeProperty> typePropertyList=targetDimensionType.getTypeProperties();
                long allDataCount=targetDimensionType.countContainedDimensions(true);
                long selfDataCount=targetDimensionType.countContainedDimensions(false);

                DimensionTypeSummaryVO typePropertyableVO=new DimensionTypeSummaryVO();
                typePropertyableVO.setTypeName(targetDimensionType.getTypeName());
                typePropertyableVO.setDimensionSelfDataCount(selfDataCount);
                typePropertyableVO.setDimensionDescendantDataCount(allDataCount-selfDataCount);

                DimensionType parentDimensionType=targetDimensionType.getParentDimensionType();
                if(parentDimensionType!=null) {
                    typePropertyableVO.setParentDimensionTypeName(parentDimensionType.getTypeName());
                }

                List<String> childDimensionTypesList=new ArrayList<>();
                typePropertyableVO.setChildDimensionTypes(childDimensionTypesList);

                List<DimensionType> childDimensionTypeList=targetDimensionType.getChildDimensionTypes();
                if(childDimensionTypeList!=null){
                    for(DimensionType currentDimensionType:childDimensionTypeList){
                        childDimensionTypesList.add(currentDimensionType.getTypeName());

                    }
                }
                typePropertyableVO.setTypePropertyList(loadTypePropertyVOList(typePropertyList));
                return typePropertyableVO;
            }else{
                return null;
            }
        }finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
    }

    public static FactTypeSummaryVO getFactTypeDetail(String spaceName,String factTypeName){
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean factTypeExist=targetSpace.hasFactType(factTypeName);
            if(factTypeExist){
                FactType targetFactType=targetSpace.getFactType(factTypeName);
                List<TypeProperty> typePropertyList=targetFactType.getTypeProperties();
                long allDataCount=targetFactType.countContainedFacts();

                FactTypeSummaryVO typePropertyableVO=new FactTypeSummaryVO();
                typePropertyableVO.setTypeName(targetFactType.getTypeName());
                typePropertyableVO.setFactDataCount(allDataCount);
                typePropertyableVO.setTypePropertyList(loadTypePropertyVOList(typePropertyList));
                return typePropertyableVO;

            }else{
                return null;
            }
        }finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
    }

    public static RelationTypeSummaryVO getRelationTypeDetail(String spaceName,String relationTypeName){
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean relationTypeExist=targetSpace.hasRelationType(relationTypeName);
            if(relationTypeExist){
                RelationType targetRelationType=targetSpace.getRelationType(relationTypeName);
                List<TypeProperty> typePropertyList=targetRelationType.getTypeProperties();
                long allDataCount=targetRelationType.countContainedRelations(true);
                long selfDataCount=targetRelationType.countContainedRelations(false);

                RelationTypeSummaryVO typePropertyableVO=new RelationTypeSummaryVO();
                typePropertyableVO.setTypeName(targetRelationType.getTypeName());
                typePropertyableVO.setRelationSelfDataCount(selfDataCount);
                typePropertyableVO.setRelationDescendantDataCount(allDataCount-selfDataCount);

                RelationType parentRelationType=targetRelationType.getParentRelationType();
                if(parentRelationType!=null) {
                    typePropertyableVO.setParentRelationTypeName(parentRelationType.getTypeName());
                }

                List<String> childRelationTypesList=new ArrayList<>();
                typePropertyableVO.setChildRelationTypes(childRelationTypesList);

                List<RelationType> childRelationTypeList=targetRelationType.getChildRelationTypes();
                if(childRelationTypeList!=null){
                    for(RelationType currentRelationType:childRelationTypeList){
                        childRelationTypesList.add(currentRelationType.getTypeName());
                    }
                }
                typePropertyableVO.setTypePropertyList(loadTypePropertyVOList(typePropertyList));
                return typePropertyableVO;
            }else{
                return null;
            }
        }finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
    }

    public static MeasurableVO getMeasurableVO(String spaceName,Measurable typeMeasurable){
        MeasurableVO currentMeasurableVO=new MeasurableVO();
        if(typeMeasurable instanceof Fact){
            currentMeasurableVO.setRecordId(((Fact)typeMeasurable).getId());
        }
        if(typeMeasurable instanceof Dimension){
            currentMeasurableVO.setRecordId(((Dimension)typeMeasurable).getId());
        }
        if(typeMeasurable instanceof Relation){
            currentMeasurableVO.setRecordId(((Relation)typeMeasurable).getId());
        }
        List<Property> currentMeasurableProperties=typeMeasurable.getProperties();
        currentMeasurableVO.setMeasurableProperties(loadMeasurablePropertyVOList(spaceName,typeMeasurable,currentMeasurableProperties));
        return currentMeasurableVO;
    }

    private static MeasurableVO getMeasurableVO(String spaceName,Measurable typeMeasurable,String[] targetProperties,Map<String,String> propertiesAliasNameMap){
        MeasurableVO currentMeasurableVO=new MeasurableVO();
        if(typeMeasurable instanceof Fact){
            currentMeasurableVO.setRecordId(((Fact)typeMeasurable).getId());
        }
        if(typeMeasurable instanceof Dimension){
            currentMeasurableVO.setRecordId(((Dimension)typeMeasurable).getId());
        }
        if(typeMeasurable instanceof Relation){
            currentMeasurableVO.setRecordId(((Relation)typeMeasurable).getId());
        }
        List<Property> currentMeasurableProperties=new ArrayList<>();
        if(targetProperties!=null){
            for(String currentTargetPropertyName:targetProperties){
                Property currentProperty=typeMeasurable.getProperty(currentTargetPropertyName);
                if(currentProperty!=null){
                    currentMeasurableProperties.add(currentProperty);
                    if(propertiesAliasNameMap!=null) {
                        String currentPropertyAliasName = propertiesAliasNameMap.get(currentTargetPropertyName);
                        if(currentPropertyAliasName==null){
                            String propertyAliasName=null;
                            if(typeMeasurable instanceof Dimension){
                                propertyAliasName = getTypePropertyAliasName(
                                        spaceName,DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION,((Dimension) typeMeasurable).getType(),currentProperty.getPropertyName());
                                if(propertyAliasName==null||propertyAliasName.equals("")){
                                    propertyAliasName=getCustomPropertyAliasName(spaceName,currentProperty.getPropertyName(),currentProperty.getPropertyType().toString());
                                }
                            }
                            if(typeMeasurable instanceof Fact){
                                propertyAliasName = getTypePropertyAliasName(
                                        spaceName,DiscoverSpaceOperationConstant.TYPEKIND_FACT,((Fact) typeMeasurable).getType(),currentProperty.getPropertyName());
                                if(propertyAliasName==null||propertyAliasName.equals("")){
                                    propertyAliasName=getCustomPropertyAliasName(spaceName,currentProperty.getPropertyName(),currentProperty.getPropertyType().toString());
                                }
                            }
                            if(typeMeasurable instanceof Relation){
                                propertyAliasName = getTypePropertyAliasName(
                                        spaceName,DiscoverSpaceOperationConstant.TYPEKIND_RELATION,((Relation) typeMeasurable).getType(),currentProperty.getPropertyName());
                                if(propertyAliasName==null||propertyAliasName.equals("")){
                                    propertyAliasName=getCustomPropertyAliasName(spaceName,currentProperty.getPropertyName(),currentProperty.getPropertyType().toString());
                                }
                            }
                            if(propertyAliasName!=null&&!propertyAliasName.equals("")) {
                                propertiesAliasNameMap.put(currentTargetPropertyName, propertyAliasName);
                            }
                        }
                    }
                }
            }
        }
        currentMeasurableVO.setMeasurableProperties(loadMeasurablePropertyVOList(spaceName,typeMeasurable,currentMeasurableProperties));
        return currentMeasurableVO;
    }

    public static MeasurableQueryResultSetVO queryDimensionTypeData(String spaceName,String dimensionTypeName) {
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasDimensionType(dimensionTypeName);
            if(!hasTargetType){
                return null;
            }else{
                MeasurableQueryResultSetVO measurableQueryResultSetVO=new MeasurableQueryResultSetVO();
                measurableQueryResultSetVO.setCurrentPage(1);
                measurableQueryResultSetVO.setPageSize(-1);
                measurableQueryResultSetVO.setDiscoverSpaceName(spaceName);
                measurableQueryResultSetVO.setMeasurableName(dimensionTypeName);
                measurableQueryResultSetVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION);

                DimensionType targetDimensionType=targetSpace.getDimensionType(dimensionTypeName);
                measurableQueryResultSetVO.setMeasurableRecordCount(targetDimensionType.countContainedDimensions(true));

                List<TypeProperty> typePropertyList=targetDimensionType.getTypeProperties();
                measurableQueryResultSetVO.setTypeProperties(loadTypePropertyVOList(typePropertyList));

                InformationExplorer ie=targetSpace.getInformationExplorer();
                ExploreParameters ep=new ExploreParameters();
                ep.setType(dimensionTypeName);

                ep.setPageSize(1000);
                ep.setStartPage(1);
                ep.setEndPage(6);

                List<Dimension> resultDimensionList=ie.discoverDimensions(ep);

                List<MeasurableVO> resultMeasurableVOList=new ArrayList<>();
                if(resultDimensionList!=null){
                    for(Measurable currentMeasurable:resultDimensionList){
                        MeasurableVO measurableVO =getMeasurableVO(spaceName,currentMeasurable);
                        resultMeasurableVOList.add(measurableVO);
                    }
                }
                measurableQueryResultSetVO.setMeasurableValues(resultMeasurableVOList);

                String sql= SQLBuilder.buildQuerySQL(InformationType.DIMENSION, ep);
                measurableQueryResultSetVO.setQuerySQL(sql);

                return measurableQueryResultSetVO;
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
        return null;
    }

    public static MeasurableQueryResultSetVO queryDimensionDataByQuerySQL(String spaceName,String dimensionTypeName,String querySQL) {
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasDimensionType(dimensionTypeName);
            if(!hasTargetType){
                return null;
            }else{
                MeasurableQueryResultSetVO measurableQueryResultSetVO=new MeasurableQueryResultSetVO();
                measurableQueryResultSetVO.setCurrentPage(1);
                measurableQueryResultSetVO.setPageSize(-1);
                measurableQueryResultSetVO.setDiscoverSpaceName(spaceName);
                measurableQueryResultSetVO.setMeasurableName(dimensionTypeName);
                measurableQueryResultSetVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION);

                DimensionType targetDimensionType=targetSpace.getDimensionType(dimensionTypeName);
                measurableQueryResultSetVO.setMeasurableRecordCount(targetDimensionType.countContainedDimensions(true));

                List<TypeProperty> typePropertyList=targetDimensionType.getTypeProperties();
                measurableQueryResultSetVO.setTypeProperties(loadTypePropertyVOList(typePropertyList));

                InformationExplorer ie=targetSpace.getInformationExplorer();
                List<Measurable> resultMeasurableList= ie.discoverMeasurablesByQuerySQL(InformationType.DIMENSION,dimensionTypeName,querySQL);
                List<MeasurableVO> resultMeasurableVOList=new ArrayList<>();
                if(resultMeasurableList!=null){
                    for(Measurable currentMeasurable:resultMeasurableList){
                        MeasurableVO measurableVO =getMeasurableVO(spaceName,currentMeasurable);
                        resultMeasurableVOList.add(measurableVO);
                    }
                }
                measurableQueryResultSetVO.setMeasurableValues(resultMeasurableVOList);
                measurableQueryResultSetVO.setQuerySQL(querySQL);
                return measurableQueryResultSetVO;
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
        return null;
    }

    public static MeasurableQueryResultSetVO queryRelationTypeData(String spaceName,String relationTypeName) {
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasRelationType(relationTypeName);
            if(!hasTargetType){
                return null;
            }else{
                MeasurableQueryResultSetVO measurableQueryResultSetVO=new MeasurableQueryResultSetVO();
                measurableQueryResultSetVO.setCurrentPage(1);
                measurableQueryResultSetVO.setPageSize(-1);
                measurableQueryResultSetVO.setDiscoverSpaceName(spaceName);
                measurableQueryResultSetVO.setMeasurableName(relationTypeName);
                measurableQueryResultSetVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_RELATION);

                RelationType targetRelationType=targetSpace.getRelationType(relationTypeName);
                measurableQueryResultSetVO.setMeasurableRecordCount(targetRelationType.countContainedRelations(true));

                List<TypeProperty> typePropertyList=targetRelationType.getTypeProperties();
                measurableQueryResultSetVO.setTypeProperties(loadTypePropertyVOList(typePropertyList));

                InformationExplorer ie=targetSpace.getInformationExplorer();
                ExploreParameters ep=new ExploreParameters();
                ep.setType(relationTypeName);

                ep.setPageSize(1000);
                ep.setStartPage(1);
                ep.setEndPage(6);

                List<Relation> resultRelationList=ie.discoverRelations(ep);

                List<MeasurableVO> resultMeasurableVOList=new ArrayList<>();
                if(resultRelationList!=null){
                    for(Measurable currentMeasurable:resultRelationList){
                        MeasurableVO measurableVO =getMeasurableVO(spaceName,currentMeasurable);
                        resultMeasurableVOList.add(measurableVO);
                    }
                }
                measurableQueryResultSetVO.setMeasurableValues(resultMeasurableVOList);

                String sql= SQLBuilder.buildQuerySQL(InformationType.RELATION, ep);
                measurableQueryResultSetVO.setQuerySQL(sql);

                return measurableQueryResultSetVO;
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
        return null;
    }

    public static MeasurableQueryResultSetVO queryRelationDataByQuerySQL(String spaceName,String relationTypeName,String querySQL) {
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasRelationType(relationTypeName);
            if(!hasTargetType){
                return null;
            }else{
                MeasurableQueryResultSetVO measurableQueryResultSetVO=new MeasurableQueryResultSetVO();
                measurableQueryResultSetVO.setCurrentPage(1);
                measurableQueryResultSetVO.setPageSize(-1);
                measurableQueryResultSetVO.setDiscoverSpaceName(spaceName);
                measurableQueryResultSetVO.setMeasurableName(relationTypeName);
                measurableQueryResultSetVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_RELATION);

                RelationType targetRelationType=targetSpace.getRelationType(relationTypeName);
                measurableQueryResultSetVO.setMeasurableRecordCount(targetRelationType.countContainedRelations(true));

                List<TypeProperty> typePropertyList=targetRelationType.getTypeProperties();
                measurableQueryResultSetVO.setTypeProperties(loadTypePropertyVOList(typePropertyList));

                InformationExplorer ie=targetSpace.getInformationExplorer();
                List<Measurable> resultMeasurableList= ie.discoverMeasurablesByQuerySQL(InformationType.RELATION,relationTypeName,querySQL);
                List<MeasurableVO> resultMeasurableVOList=new ArrayList<>();
                if(resultMeasurableList!=null){
                    for(Measurable currentMeasurable:resultMeasurableList){
                        MeasurableVO measurableVO =getMeasurableVO(spaceName,currentMeasurable);
                        resultMeasurableVOList.add(measurableVO);
                    }
                }
                measurableQueryResultSetVO.setMeasurableValues(resultMeasurableVOList);
                measurableQueryResultSetVO.setQuerySQL(querySQL);
                return measurableQueryResultSetVO;
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
        return null;
    }

    public static MeasurableQueryResultSetVO queryFactTypeData(String spaceName,String factTypeName) {
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasFactType(factTypeName);
            if(!hasTargetType){
                return null;
            }else{
                MeasurableQueryResultSetVO measurableQueryResultSetVO=new MeasurableQueryResultSetVO();
                measurableQueryResultSetVO.setCurrentPage(1);
                measurableQueryResultSetVO.setPageSize(-1);
                measurableQueryResultSetVO.setDiscoverSpaceName(spaceName);
                measurableQueryResultSetVO.setMeasurableName(factTypeName);
                measurableQueryResultSetVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_FACT);

                FactType targetFactType=targetSpace.getFactType(factTypeName);
                measurableQueryResultSetVO.setMeasurableRecordCount(targetFactType.countContainedFacts());

                List<TypeProperty> typePropertyList=targetFactType.getTypeProperties();
                measurableQueryResultSetVO.setTypeProperties(loadTypePropertyVOList(typePropertyList));

                InformationExplorer ie=targetSpace.getInformationExplorer();
                ExploreParameters ep=new ExploreParameters();
                ep.setType(factTypeName);

                ep.setPageSize(1000);
                ep.setStartPage(1);
                ep.setEndPage(6);

                List<Fact> resultFactList=ie.discoverFacts(ep);

                List<MeasurableVO> resultMeasurableVOList=new ArrayList<>();
                if(resultFactList!=null){
                    for(Measurable currentMeasurable:resultFactList){
                        MeasurableVO measurableVO =getMeasurableVO(spaceName,currentMeasurable);
                        resultMeasurableVOList.add(measurableVO);
                    }
                }
                measurableQueryResultSetVO.setMeasurableValues(resultMeasurableVOList);

                String sql= SQLBuilder.buildQuerySQL(InformationType.FACT, ep);
                measurableQueryResultSetVO.setQuerySQL(sql);

                return measurableQueryResultSetVO;
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
        return null;
    }

    public static MeasurableQueryResultSetVO queryFactDataByQuerySQL(String spaceName,String factTypeName,String querySQL) {
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasFactType(factTypeName);
            if(!hasTargetType){
                return null;
            }else{
                MeasurableQueryResultSetVO measurableQueryResultSetVO=new MeasurableQueryResultSetVO();
                measurableQueryResultSetVO.setCurrentPage(1);
                measurableQueryResultSetVO.setPageSize(-1);
                measurableQueryResultSetVO.setDiscoverSpaceName(spaceName);
                measurableQueryResultSetVO.setMeasurableName(factTypeName);
                measurableQueryResultSetVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_FACT);

                FactType targetFactType=targetSpace.getFactType(factTypeName);
                measurableQueryResultSetVO.setMeasurableRecordCount(targetFactType.countContainedFacts());

                List<TypeProperty> typePropertyList=targetFactType.getTypeProperties();
                measurableQueryResultSetVO.setTypeProperties(loadTypePropertyVOList(typePropertyList));

                InformationExplorer ie=targetSpace.getInformationExplorer();
                List<Measurable> resultMeasurableList= ie.discoverMeasurablesByQuerySQL(InformationType.FACT,factTypeName,querySQL);
                List<MeasurableVO> resultMeasurableVOList=new ArrayList<>();
                if(resultMeasurableList!=null){
                    for(Measurable currentMeasurable:resultMeasurableList){
                        MeasurableVO measurableVO =getMeasurableVO(spaceName,currentMeasurable);
                        resultMeasurableVOList.add(measurableVO);
                    }
                }
                measurableQueryResultSetVO.setMeasurableValues(resultMeasurableVOList);
                measurableQueryResultSetVO.setQuerySQL(querySQL);
                return measurableQueryResultSetVO;
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
        return null;
    }

    public static MeasurablePropertiesQueryResultSetVO queryFactPropertiesDataByQuerySQL(String spaceName,String factTypeName,String querySQL,String propertiesString) {
        String[] targetPropertiesArray=propertiesString.split(",");
        if(targetPropertiesArray==null){
            return null;
        }
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasFactType(factTypeName);
            if(!hasTargetType){
                return null;
            }else{
                MeasurablePropertiesQueryResultSetVO measurableQueryResultSetVO=new MeasurablePropertiesQueryResultSetVO();
                measurableQueryResultSetVO.setCurrentPage(1);
                measurableQueryResultSetVO.setPageSize(-1);
                measurableQueryResultSetVO.setDiscoverSpaceName(spaceName);
                measurableQueryResultSetVO.setMeasurableName(factTypeName);
                String aliasName=getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_FACT,factTypeName);
                if(aliasName!=null&&!aliasName.equals("")){
                    measurableQueryResultSetVO.setMeasurableAliasName(aliasName);
                }
                measurableQueryResultSetVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_FACT);

                FactType targetFactType=targetSpace.getFactType(factTypeName);
                measurableQueryResultSetVO.setMeasurableRecordCount(targetFactType.countContainedFacts());

                Map<String,String> propertiesAliasNameMap=new HashMap<>();
                measurableQueryResultSetVO.setPropertiesAliasNameMap(propertiesAliasNameMap);

                InformationExplorer ie=targetSpace.getInformationExplorer();
                List<Measurable> resultMeasurableList= ie.discoverMeasurablesByQuerySQL(InformationType.FACT,factTypeName,querySQL);
                List<MeasurableVO> resultMeasurableVOList=new ArrayList<>();
                if(resultMeasurableList!=null){
                    for(Measurable currentMeasurable:resultMeasurableList){
                        MeasurableVO measurableVO =getMeasurableVO(spaceName,currentMeasurable,targetPropertiesArray,propertiesAliasNameMap);
                        resultMeasurableVOList.add(measurableVO);
                    }
                }
                measurableQueryResultSetVO.setMeasurableValues(resultMeasurableVOList);
                measurableQueryResultSetVO.setQuerySQL(querySQL);
                return measurableQueryResultSetVO;
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
        return null;
    }

    public static MeasurablePropertiesQueryResultSetVO queryDimensionPropertiesDataByQuerySQL(String spaceName,String dimensionTypeName,String querySQL,String propertiesString) {
        String[] targetPropertiesArray=propertiesString.split(",");
        if(targetPropertiesArray==null){
            return null;
        }
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasDimensionType(dimensionTypeName);
            if(!hasTargetType){
                return null;
            }else{
                MeasurablePropertiesQueryResultSetVO measurableQueryResultSetVO=new MeasurablePropertiesQueryResultSetVO();
                measurableQueryResultSetVO.setCurrentPage(1);
                measurableQueryResultSetVO.setPageSize(-1);
                measurableQueryResultSetVO.setDiscoverSpaceName(spaceName);
                measurableQueryResultSetVO.setMeasurableName(dimensionTypeName);
                String aliasName=getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION,dimensionTypeName);
                if(aliasName!=null&&!aliasName.equals("")){
                    measurableQueryResultSetVO.setMeasurableAliasName(aliasName);
                }
                measurableQueryResultSetVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION);

                DimensionType targetDimensionType=targetSpace.getDimensionType(dimensionTypeName);
                measurableQueryResultSetVO.setMeasurableRecordCount(targetDimensionType.countContainedDimensions(true));

                Map<String,String> propertiesAliasNameMap=new HashMap<>();
                measurableQueryResultSetVO.setPropertiesAliasNameMap(propertiesAliasNameMap);

                InformationExplorer ie=targetSpace.getInformationExplorer();
                List<Measurable> resultMeasurableList= ie.discoverMeasurablesByQuerySQL(InformationType.DIMENSION,dimensionTypeName,querySQL);
                List<MeasurableVO> resultMeasurableVOList=new ArrayList<>();
                if(resultMeasurableList!=null){
                    for(Measurable currentMeasurable:resultMeasurableList){
                        MeasurableVO measurableVO =getMeasurableVO(spaceName,currentMeasurable,targetPropertiesArray,propertiesAliasNameMap);
                        resultMeasurableVOList.add(measurableVO);
                    }
                }
                measurableQueryResultSetVO.setMeasurableValues(resultMeasurableVOList);
                measurableQueryResultSetVO.setQuerySQL(querySQL);
                return measurableQueryResultSetVO;
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
        return null;
    }

    public static MeasurablePropertiesQueryResultSetVO queryRelationPropertiesDataByQuerySQL(String spaceName,String relationTypeName,String querySQL,String propertiesString) {
        String[] targetPropertiesArray=propertiesString.split(",");
        if(targetPropertiesArray==null){
            return null;
        }
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasRelationType(relationTypeName);
            if(!hasTargetType){
                return null;
            }else{
                MeasurablePropertiesQueryResultSetVO measurableQueryResultSetVO=new MeasurablePropertiesQueryResultSetVO();
                measurableQueryResultSetVO.setCurrentPage(1);
                measurableQueryResultSetVO.setPageSize(-1);
                measurableQueryResultSetVO.setDiscoverSpaceName(spaceName);
                measurableQueryResultSetVO.setMeasurableName(relationTypeName);
                String aliasName=getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_RELATION,relationTypeName);
                if(aliasName!=null&&!aliasName.equals("")){
                    measurableQueryResultSetVO.setMeasurableAliasName(aliasName);
                }
                measurableQueryResultSetVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_RELATION);

                RelationType targetRelationType=targetSpace.getRelationType(relationTypeName);
                measurableQueryResultSetVO.setMeasurableRecordCount(targetRelationType.countContainedRelations(true));

                Map<String,String> propertiesAliasNameMap=new HashMap<>();
                measurableQueryResultSetVO.setPropertiesAliasNameMap(propertiesAliasNameMap);

                InformationExplorer ie=targetSpace.getInformationExplorer();
                List<Measurable> resultMeasurableList= ie.discoverMeasurablesByQuerySQL(InformationType.RELATION,relationTypeName,querySQL);
                List<MeasurableVO> resultMeasurableVOList=new ArrayList<>();
                if(resultMeasurableList!=null){
                    for(Measurable currentMeasurable:resultMeasurableList){
                        MeasurableVO measurableVO =getMeasurableVO(spaceName,currentMeasurable,targetPropertiesArray,propertiesAliasNameMap);
                        resultMeasurableVOList.add(measurableVO);
                    }
                }
                measurableQueryResultSetVO.setMeasurableValues(resultMeasurableVOList);
                measurableQueryResultSetVO.setQuerySQL(querySQL);
                return measurableQueryResultSetVO;
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
        return null;
    }

    private static List<PropertyVO> loadMeasurablePropertyVOList(String spaceName,Measurable targetMeasurable,List<Property> measurableProperties){
        List<PropertyVO> currentMeasurableVOPropertiesList=new ArrayList<>();
        if(measurableProperties!=null){
            for(Property currentProperty:measurableProperties){
                PropertyVO currentPropertyVO=new PropertyVO();
                currentMeasurableVOPropertiesList.add(currentPropertyVO);
                currentPropertyVO.setPropertyType(""+currentProperty.getPropertyType());
                currentPropertyVO.setPropertyName(currentProperty.getPropertyName());
                if(targetMeasurable instanceof Dimension){
                    String propertyAliasName = getTypePropertyAliasName(
                            spaceName,DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION,((Dimension) targetMeasurable).getType(),currentProperty.getPropertyName());
                    if(propertyAliasName==null||propertyAliasName.equals("")){
                        propertyAliasName=getCustomPropertyAliasName(spaceName,currentProperty.getPropertyName(),currentProperty.getPropertyType().toString());
                    }
                    currentPropertyVO.setPropertyAliasName(propertyAliasName);
                }
                if(targetMeasurable instanceof Fact){
                    String propertyAliasName = getTypePropertyAliasName(
                            spaceName,DiscoverSpaceOperationConstant.TYPEKIND_FACT,((Fact) targetMeasurable).getType(),currentProperty.getPropertyName());
                    if(propertyAliasName==null||propertyAliasName.equals("")){
                        propertyAliasName=getCustomPropertyAliasName(spaceName,currentProperty.getPropertyName(),currentProperty.getPropertyType().toString());
                    }
                    currentPropertyVO.setPropertyAliasName(propertyAliasName);
                }
                if(targetMeasurable instanceof Relation){
                    String propertyAliasName = getTypePropertyAliasName(
                            spaceName,DiscoverSpaceOperationConstant.TYPEKIND_RELATION,((Relation) targetMeasurable).getType(),currentProperty.getPropertyName());
                    if(propertyAliasName==null||propertyAliasName.equals("")){
                        propertyAliasName=getCustomPropertyAliasName(spaceName,currentProperty.getPropertyName(),currentProperty.getPropertyType().toString());
                    }
                    currentPropertyVO.setPropertyAliasName(propertyAliasName);
                }

                Object currentPropertyValue=currentProperty.getPropertyValue();
                switch(currentProperty.getPropertyType()) {
                    case DATE:
                        Date propertyValue=(Date)currentPropertyValue;
                        currentPropertyVO.setPropertyValue(""+propertyValue.getTime());
                        break;
                    default:
                        currentPropertyVO.setPropertyValue(currentPropertyValue.toString());
                        break;
                }
            }
        }
        return currentMeasurableVOPropertiesList;
    }

    public static MeasurableInstanceDetailInfoVO getMeasurableInstanceDetailInfo(String spaceName,String measurableId){
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            return getMeasurableInstanceDetailInfoVO(targetSpace,measurableId);
        } finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
    }

    private static MeasurableInstanceDetailInfoVO getMeasurableInstanceDetailInfoVO(InfoDiscoverSpace targetSpace,String measurableId){
        Measurable targetMeasurable=targetSpace.getMeasurableById(measurableId);
        if(targetMeasurable==null){
            return null;
        }
        MeasurableInstanceDetailInfoVO measurableInstanceDetailInfoVO=new MeasurableInstanceDetailInfoVO();
        measurableInstanceDetailInfoVO.setDiscoverSpaceName(targetSpace.getSpaceName());
        measurableInstanceDetailInfoVO.setMeasurableId(measurableId);

        List<Property> currentMeasurableProperties=targetMeasurable.getProperties();
        measurableInstanceDetailInfoVO.setMeasurableProperties(loadMeasurablePropertyVOList(targetSpace.getSpaceName(),targetMeasurable,currentMeasurableProperties));

        if(targetMeasurable instanceof Fact){
            measurableInstanceDetailInfoVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_FACT);
            String measurableType=((Fact)targetMeasurable).getType();
            measurableInstanceDetailInfoVO.setMeasurableName(measurableType);
            measurableInstanceDetailInfoVO.setMeasurableAliasName(getTypeKindAliasName(targetSpace.getSpaceName(),DiscoverSpaceOperationConstant.TYPEKIND_FACT,measurableType));
            FactType measurableFactType=targetSpace.getFactType(measurableType);
            List<TypeProperty> typePropertyList=measurableFactType.getTypeProperties();
            measurableInstanceDetailInfoVO.setTypePropertyList(loadTypePropertyVOList(typePropertyList));

        }
        if(targetMeasurable instanceof Dimension){
            measurableInstanceDetailInfoVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION);
            String measurableType=((Dimension)targetMeasurable).getType();
            measurableInstanceDetailInfoVO.setMeasurableName(measurableType);
            measurableInstanceDetailInfoVO.setMeasurableAliasName(getTypeKindAliasName(targetSpace.getSpaceName(),DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION,measurableType));
            DimensionType measurableDimensionType=targetSpace.getDimensionType(measurableType);
            List<TypeProperty> typePropertyList=measurableDimensionType.getTypeProperties();
            measurableInstanceDetailInfoVO.setTypePropertyList(loadTypePropertyVOList(typePropertyList));

        }
        if(targetMeasurable instanceof Relation){
            measurableInstanceDetailInfoVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_RELATION);
            String measurableType=((Relation)targetMeasurable).getType();
            measurableInstanceDetailInfoVO.setMeasurableName(measurableType);
            measurableInstanceDetailInfoVO.setMeasurableAliasName(getTypeKindAliasName(targetSpace.getSpaceName(),DiscoverSpaceOperationConstant.TYPEKIND_RELATION,measurableType));
            RelationType measurableRelationType=targetSpace.getRelationType(measurableType);
            List<TypeProperty> typePropertyList=measurableRelationType.getTypeProperties();
            measurableInstanceDetailInfoVO.setTypePropertyList(loadTypePropertyVOList(typePropertyList));
        }
        return measurableInstanceDetailInfoVO;
    }

    public static List<MeasurableInstanceDetailInfoVO> getMeasurableInstancesDetailInfo(String spaceName,String measurableIds){
        InfoDiscoverSpace targetSpace=null;
        List<MeasurableInstanceDetailInfoVO> measurableInstanceDetailInfoVOList=new ArrayList<>();
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            String[] measurableIdArray=measurableIds.split(",");
            for(String currentIdString:measurableIdArray){
                MeasurableInstanceDetailInfoVO currentMeasurableInstanceDetailInfoVO=getMeasurableInstanceDetailInfoVO(targetSpace,currentIdString);
                if(currentMeasurableInstanceDetailInfoVO!=null){
                    measurableInstanceDetailInfoVOList.add(currentMeasurableInstanceDetailInfoVO);
                }
            }
            return measurableInstanceDetailInfoVOList;
        } finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
    }

    public static Map<String,MeasurableTypeDataInfoVO> getTypeInstancesInfoOfMeasurableTypes(String spaceName,String measurableType,String measurableTypeNames){
        Map<String,MeasurableTypeDataInfoVO> resultMap=new HashMap<>();
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            String[] measurableTypeArray=measurableTypeNames.split(",");
            for(String currentMeasurableType:measurableTypeArray){
                MeasurableTypeDataInfoVO currentMeasurableTypeDataInfoVO=getMeasureTypeInstancesInfoList(targetSpace,measurableType,currentMeasurableType);
                if(currentMeasurableTypeDataInfoVO!=null){
                    resultMap.put(currentMeasurableType,currentMeasurableTypeDataInfoVO);
                }
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
        return resultMap;
    }

    private static MeasurableTypeDataInfoVO getMeasureTypeInstancesInfoList(InfoDiscoverSpace targetSpace,String measurableType,String measurableTypeName) throws InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        MeasurableTypeDataInfoVO measurableTypeDataInfoVO=new MeasurableTypeDataInfoVO();

        InformationExplorer ie=targetSpace.getInformationExplorer();
        ExploreParameters ep=new ExploreParameters();
        ep.setType(measurableTypeName);
        ep.setResultNumber(5000);
        if(DiscoverSpaceOperationConstant.TYPEKIND_FACT.equals(measurableType)){
            FactType targetFactType=targetSpace.getFactType(measurableTypeName);
            List<Fact> resultList=ie.discoverFacts(ep);

            measurableTypeDataInfoVO.setDiscoverSpaceName(targetSpace.getSpaceName());
            measurableTypeDataInfoVO.setMeasurableName(measurableTypeName);
            measurableTypeDataInfoVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_FACT);
            measurableTypeDataInfoVO.setMeasurableRecordCount(resultList.size());

            List<TypeProperty> typePropertyList=targetFactType.getTypeProperties();
            measurableTypeDataInfoVO.setTypeProperties(loadTypePropertyVOList(typePropertyList));

            List<MeasurableTypeValueVO> measurableTypeValueList=new ArrayList<>();
            for(Fact currentMeasurable:resultList){
                MeasurableTypeValueVO currentMeasurableTypeValueVO=loadMeasurableTypeValueVO(targetSpace.getSpaceName(),currentMeasurable,typePropertyList);
                measurableTypeValueList.add(currentMeasurableTypeValueVO);
            }
            measurableTypeDataInfoVO.setMeasurableValues(measurableTypeValueList);
        }
        if(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION.equals(measurableType)){
            DimensionType targetDimensionType=targetSpace.getDimensionType(measurableTypeName);
            List<Dimension> resultList=ie.discoverDimensions(ep);

            measurableTypeDataInfoVO.setDiscoverSpaceName(targetSpace.getSpaceName());
            measurableTypeDataInfoVO.setMeasurableName(measurableTypeName);
            measurableTypeDataInfoVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION);
            measurableTypeDataInfoVO.setMeasurableRecordCount(resultList.size());

            List<TypeProperty> typePropertyList=targetDimensionType.getTypeProperties();
            measurableTypeDataInfoVO.setTypeProperties(loadTypePropertyVOList(typePropertyList));

            List<MeasurableTypeValueVO> measurableTypeValueList=new ArrayList<>();
            for(Dimension currentMeasurable:resultList){
                MeasurableTypeValueVO currentMeasurableTypeValueVO=loadMeasurableTypeValueVO(targetSpace.getSpaceName(),currentMeasurable,typePropertyList);
                measurableTypeValueList.add(currentMeasurableTypeValueVO);
            }
            measurableTypeDataInfoVO.setMeasurableValues(measurableTypeValueList);
        }
        if(DiscoverSpaceOperationConstant.TYPEKIND_RELATION.equals(measurableType)){
            RelationType targetRelationType=targetSpace.getRelationType(measurableTypeName);
            List<Relation> resultList=ie.discoverRelations(ep);

            measurableTypeDataInfoVO.setDiscoverSpaceName(targetSpace.getSpaceName());
            measurableTypeDataInfoVO.setMeasurableName(measurableTypeName);
            measurableTypeDataInfoVO.setMeasurableType(DiscoverSpaceOperationConstant.TYPEKIND_RELATION);
            measurableTypeDataInfoVO.setMeasurableRecordCount(resultList.size());

            List<TypeProperty> typePropertyList=targetRelationType.getTypeProperties();
            measurableTypeDataInfoVO.setTypeProperties(loadTypePropertyVOList(typePropertyList));

            List<MeasurableTypeValueVO> measurableTypeValueList=new ArrayList<>();
            for(Relation currentMeasurable:resultList){
                MeasurableTypeValueVO currentMeasurableTypeValueVO=loadMeasurableTypeValueVO(targetSpace.getSpaceName(),currentMeasurable,typePropertyList);
                measurableTypeValueList.add(currentMeasurableTypeValueVO);
            }
            measurableTypeDataInfoVO.setMeasurableValues(measurableTypeValueList);
        }

        return measurableTypeDataInfoVO;
    }

    private static MeasurableTypeValueVO loadMeasurableTypeValueVO(String spaceName,Measurable targetMeasurable,List<TypeProperty> typePropertyList){
        MeasurableTypeValueVO measurableTypeValueVO=new MeasurableTypeValueVO();
        List<PropertyVO> currentMeasurableVOPropertiesList=new ArrayList<>();
        measurableTypeValueVO.setMeasurableProperties(currentMeasurableVOPropertiesList);

        if(typePropertyList!=null){
            for(TypeProperty currentTypeProperty:typePropertyList){
                Property currentProperty=targetMeasurable.getProperty(currentTypeProperty.getPropertyName());
                if(currentProperty!=null){
                    PropertyVO currentPropertyVO=new PropertyVO();
                    currentMeasurableVOPropertiesList.add(currentPropertyVO);

                    currentPropertyVO.setPropertyType(""+currentProperty.getPropertyType());
                    currentPropertyVO.setPropertyName(currentProperty.getPropertyName());
                    if(targetMeasurable instanceof Dimension){
                        String propertyAliasName = getTypePropertyAliasName(
                                spaceName,DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION,((Dimension) targetMeasurable).getType(),currentProperty.getPropertyName());
                        if(propertyAliasName==null||propertyAliasName.equals("")){
                            propertyAliasName=getCustomPropertyAliasName(spaceName,currentProperty.getPropertyName(),currentProperty.getPropertyType().toString());
                        }
                        currentPropertyVO.setPropertyAliasName(propertyAliasName);
                        measurableTypeValueVO.setMeasurableId(((Dimension) targetMeasurable).getId());
                    }
                    if(targetMeasurable instanceof Fact){
                        String propertyAliasName = getTypePropertyAliasName(
                                spaceName,DiscoverSpaceOperationConstant.TYPEKIND_FACT,((Fact) targetMeasurable).getType(),currentProperty.getPropertyName());
                        if(propertyAliasName==null||propertyAliasName.equals("")){
                            propertyAliasName=getCustomPropertyAliasName(spaceName,currentProperty.getPropertyName(),currentProperty.getPropertyType().toString());
                        }
                        currentPropertyVO.setPropertyAliasName(propertyAliasName);
                        measurableTypeValueVO.setMeasurableId(((Fact) targetMeasurable).getId());
                    }
                    if(targetMeasurable instanceof Relation){
                        String propertyAliasName = getTypePropertyAliasName(
                                spaceName,DiscoverSpaceOperationConstant.TYPEKIND_RELATION,((Relation) targetMeasurable).getType(),currentProperty.getPropertyName());
                        if(propertyAliasName==null||propertyAliasName.equals("")){
                            propertyAliasName=getCustomPropertyAliasName(spaceName,currentProperty.getPropertyName(),currentProperty.getPropertyType().toString());
                        }
                        currentPropertyVO.setPropertyAliasName(propertyAliasName);
                        measurableTypeValueVO.setMeasurableId(((Relation) targetMeasurable).getId());
                    }

                    Object currentPropertyValue=currentProperty.getPropertyValue();
                    switch(currentProperty.getPropertyType()) {
                        case DATE:
                            Date propertyValue=(Date)currentPropertyValue;
                            currentPropertyVO.setPropertyValue(""+propertyValue.getTime());
                            break;
                        default:
                            currentPropertyVO.setPropertyValue(currentPropertyValue.toString());
                            break;
                    }
                }
            }
        }
        return measurableTypeValueVO;
    }

    public static String generateFactTypePropertiesCSV(String spaceName,String factTypeName,String properties) {
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasFactType(factTypeName);
            if(!hasTargetType){
                return null;
            }else{
                InformationExplorer ie=targetSpace.getInformationExplorer();
                ExploreParameters ep=new ExploreParameters();
                ep.setType(factTypeName);
                ep.setPageSize(1000);
                ep.setStartPage(1);
                ep.setEndPage(6);
                List<Fact> resultFactList=ie.discoverFacts(ep);
                return generateMeasurableTypePropertiesCSV(resultFactList,properties);
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
        return null;
    }

    public static String generateDimensionTypePropertiesCSV(String spaceName,String dimensionTypeName,String properties) {
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasDimensionType(dimensionTypeName);
            if(!hasTargetType){
                return null;
            }else{
                InformationExplorer ie=targetSpace.getInformationExplorer();
                ExploreParameters ep=new ExploreParameters();
                ep.setType(dimensionTypeName);
                ep.setPageSize(1000);
                ep.setStartPage(1);
                ep.setEndPage(6);
                List<Dimension> resultDimensionList=ie.discoverDimensions(ep);
                return generateMeasurableTypePropertiesCSV(resultDimensionList,properties);
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
        return null;
    }

    public static String generateRelationTypePropertiesCSV(String spaceName,String relationTypeName,String properties) {
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasRelationType(relationTypeName);
            if(!hasTargetType){
                return null;
            }else{
                InformationExplorer ie=targetSpace.getInformationExplorer();
                ExploreParameters ep=new ExploreParameters();
                ep.setType(relationTypeName);
                ep.setPageSize(1000);
                ep.setStartPage(1);
                ep.setEndPage(6);
                List<Relation> resultRelationList=ie.discoverRelations(ep);
                return generateMeasurableTypePropertiesCSV(resultRelationList,properties);
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
        return null;
    }

    private static String generateMeasurableTypePropertiesCSV(List<? extends Measurable> resultMeasurableList,String properties){
        String[] propertyNameList=properties.split(",");
        if(propertyNameList.length==0){
            return null;
        }
        String titleLineValue=null;
        for(int i=0;i<propertyNameList.length;i++){
            String currentProperty=propertyNameList[i];
            if(i!=0){
                titleLineValue=titleLineValue+","+currentProperty;
            }else{
                titleLineValue=currentProperty;
            }
        }
        StringBuffer csvStringBuffer=new StringBuffer();
        csvStringBuffer.append(titleLineValue+"\r");

        if(resultMeasurableList!=null){
            String currentString=null;
            for(Measurable currentMeasurable:resultMeasurableList){
                for(int i=0;i<propertyNameList.length;i++){
                    String currentPropertyName=propertyNameList[i];
                    Property currentProperty=currentMeasurable.getProperty(currentPropertyName);
                    if(currentProperty==null){
                        currentString=currentString+",";
                    }else {
                        if (currentProperty.getPropertyType().equals(PropertyType.DATE)) {
                            Date propertyValue = (Date) currentProperty.getPropertyValue();
                            if (i == 0) {
                                currentString = "" + propertyValue.getTime();
                            } else {
                                currentString = currentString + "," + propertyValue.getTime();
                            }
                        } else {
                            if (i == 0) {
                                currentString = currentProperty.getPropertyValue().toString();
                            } else {
                                currentString = currentString + "," + currentProperty.getPropertyValue().toString();
                            }
                        }
                    }
                }
                csvStringBuffer.append(currentString+"\r");
            }
        }
        return csvStringBuffer.toString();
    }

    public static List<Map<String,String>> generateFactTypePropertiesJSON(String spaceName, String factTypeName, String properties) {
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasFactType(factTypeName);
            if(!hasTargetType){
                return null;
            }else{
                InformationExplorer ie=targetSpace.getInformationExplorer();
                ExploreParameters ep=new ExploreParameters();
                ep.setType(factTypeName);
                ep.setPageSize(1000);
                ep.setStartPage(1);
                ep.setEndPage(6);
                List<Fact> resultFactList=ie.discoverFacts(ep);
                return generateMeasurableTypePropertiesJSON(resultFactList,properties);
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
        return null;
    }

    public static List<Map<String,String>> generateFactTypePropertiesJSONByQuerySQL(String spaceName, String factTypeName, String properties,String querySQL) {
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasFactType(factTypeName);
            if(!hasTargetType){
                return null;
            }else{
                InformationExplorer ie=targetSpace.getInformationExplorer();
                List<Measurable> resultMeasurableList= ie.discoverMeasurablesByQuerySQL(InformationType.FACT,factTypeName,querySQL);
                return generateMeasurableTypePropertiesJSON(resultMeasurableList,properties);
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
        return null;
    }

    public static MeasurablePropertiesQueryBriefResultSetVO generateFactTypePropertiesBriefDataByQuerySQL(String spaceName, String factTypeName, String properties,String querySQL) {
        MeasurablePropertiesQueryBriefResultSetVO measurablePropertiesQueryBriefResultSetVO=new MeasurablePropertiesQueryBriefResultSetVO();
        measurablePropertiesQueryBriefResultSetVO.setDiscoverSpaceName(spaceName);
        measurablePropertiesQueryBriefResultSetVO.setMeasurableName(factTypeName);
        String aliasName=getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_FACT,factTypeName);
        if(aliasName!=null&&!aliasName.equals("")){
            measurablePropertiesQueryBriefResultSetVO.setMeasurableAliasName(aliasName);
        }
        Map<String,String> propertiesAliasNameMap=new HashMap<>();
        measurablePropertiesQueryBriefResultSetVO.setPropertiesAliasNameMap(propertiesAliasNameMap);

        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasFactType(factTypeName);
            if(!hasTargetType){
                return null;
            }else{
                InformationExplorer ie=targetSpace.getInformationExplorer();
                List<Measurable> resultMeasurableList= ie.discoverMeasurablesByQuerySQL(InformationType.FACT,factTypeName,querySQL);
                List<Map<String,String>> propertyRowData= generateMeasurableTypePropertiesJSON(spaceName,resultMeasurableList,properties,propertiesAliasNameMap);
                measurablePropertiesQueryBriefResultSetVO.setPropertyRowData(propertyRowData);
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
        return measurablePropertiesQueryBriefResultSetVO;
    }

    public static List<Map<String,String>> generateDimensionTypePropertiesJSON(String spaceName,String dimensionTypeName,String properties) {
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasDimensionType(dimensionTypeName);
            if(!hasTargetType){
                return null;
            }else{
                InformationExplorer ie=targetSpace.getInformationExplorer();
                ExploreParameters ep=new ExploreParameters();
                ep.setType(dimensionTypeName);
                ep.setPageSize(1000);
                ep.setStartPage(1);
                ep.setEndPage(6);
                List<Dimension> resultDimensionList=ie.discoverDimensions(ep);
                return generateMeasurableTypePropertiesJSON(resultDimensionList,properties);
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
        return null;
    }

    public static List<Map<String,String>> generateDimensionTypePropertiesJSONByQuerySQL(String spaceName,String dimensionTypeName,String properties,String querySQL) {
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasDimensionType(dimensionTypeName);
            if(!hasTargetType){
                return null;
            }else{
                InformationExplorer ie=targetSpace.getInformationExplorer();
                List<Measurable> resultMeasurableList= ie.discoverMeasurablesByQuerySQL(InformationType.DIMENSION,dimensionTypeName,querySQL);
                return generateMeasurableTypePropertiesJSON(resultMeasurableList,properties);
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
        return null;
    }

    public static MeasurablePropertiesQueryBriefResultSetVO generateDimensionTypePropertiesBriefJSONByQuerySQL(String spaceName,String dimensionTypeName,String properties,String querySQL) {
        MeasurablePropertiesQueryBriefResultSetVO measurablePropertiesQueryBriefResultSetVO=new MeasurablePropertiesQueryBriefResultSetVO();
        measurablePropertiesQueryBriefResultSetVO.setDiscoverSpaceName(spaceName);
        measurablePropertiesQueryBriefResultSetVO.setMeasurableName(dimensionTypeName);
        String aliasName=getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION,dimensionTypeName);
        if(aliasName!=null&&!aliasName.equals("")){
            measurablePropertiesQueryBriefResultSetVO.setMeasurableAliasName(aliasName);
        }
        Map<String,String> propertiesAliasNameMap=new HashMap<>();
        measurablePropertiesQueryBriefResultSetVO.setPropertiesAliasNameMap(propertiesAliasNameMap);

        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasDimensionType(dimensionTypeName);
            if(!hasTargetType){
                return null;
            }else{
                InformationExplorer ie=targetSpace.getInformationExplorer();
                List<Measurable> resultMeasurableList= ie.discoverMeasurablesByQuerySQL(InformationType.DIMENSION,dimensionTypeName,querySQL);
                List<Map<String,String>> propertyRowData= generateMeasurableTypePropertiesJSON(spaceName,resultMeasurableList,properties,propertiesAliasNameMap);
                measurablePropertiesQueryBriefResultSetVO.setPropertyRowData(propertyRowData);
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
        return measurablePropertiesQueryBriefResultSetVO;
    }

    public static List<Map<String,String>> generateRelationTypePropertiesJSON(String spaceName,String relationTypeName,String properties) {
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasRelationType(relationTypeName);
            if(!hasTargetType){
                return null;
            }else{
                InformationExplorer ie=targetSpace.getInformationExplorer();
                ExploreParameters ep=new ExploreParameters();
                ep.setType(relationTypeName);
                ep.setPageSize(1000);
                ep.setStartPage(1);
                ep.setEndPage(6);
                List<Relation> resultRelationList=ie.discoverRelations(ep);
                return generateMeasurableTypePropertiesJSON(resultRelationList,properties);
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
        return null;
    }

    public static List<Map<String,String>> generateRelationTypePropertiesJSONByQuerySQL(String spaceName,String relationTypeName,String properties,String querySQL) {
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasRelationType(relationTypeName);
            if(!hasTargetType){
                return null;
            }else{
                InformationExplorer ie=targetSpace.getInformationExplorer();
                List<Measurable> resultMeasurableList= ie.discoverMeasurablesByQuerySQL(InformationType.RELATION,relationTypeName,querySQL);
                return generateMeasurableTypePropertiesJSON(resultMeasurableList,properties);
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
        return null;
    }

    public static MeasurablePropertiesQueryBriefResultSetVO generateRelationTypePropertiesBriefJSONByQuerySQL(String spaceName,String relationTypeName,String properties,String querySQL) {
        MeasurablePropertiesQueryBriefResultSetVO measurablePropertiesQueryBriefResultSetVO=new MeasurablePropertiesQueryBriefResultSetVO();
        measurablePropertiesQueryBriefResultSetVO.setDiscoverSpaceName(spaceName);
        measurablePropertiesQueryBriefResultSetVO.setMeasurableName(relationTypeName);
        String aliasName=getTypeKindAliasName(spaceName,DiscoverSpaceOperationConstant.TYPEKIND_RELATION,relationTypeName);
        if(aliasName!=null&&!aliasName.equals("")){
            measurablePropertiesQueryBriefResultSetVO.setMeasurableAliasName(aliasName);
        }
        Map<String,String> propertiesAliasNameMap=new HashMap<>();
        measurablePropertiesQueryBriefResultSetVO.setPropertiesAliasNameMap(propertiesAliasNameMap);

        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            boolean hasTargetType=targetSpace.hasRelationType(relationTypeName);
            if(!hasTargetType){
                return null;
            }else{
                InformationExplorer ie=targetSpace.getInformationExplorer();
                List<Measurable> resultMeasurableList= ie.discoverMeasurablesByQuerySQL(InformationType.RELATION,relationTypeName,querySQL);
                List<Map<String,String>> propertyRowData= generateMeasurableTypePropertiesJSON(spaceName,resultMeasurableList,properties,propertiesAliasNameMap);
                measurablePropertiesQueryBriefResultSetVO.setPropertyRowData(propertyRowData);
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
        return measurablePropertiesQueryBriefResultSetVO;
    }

    private static List<Map<String,String>> generateMeasurableTypePropertiesJSON(List<? extends Measurable> resultMeasurableList,String properties){
        String[] propertyNameList=properties.split(",");
        if(propertyNameList.length==0){
            return null;
        }
        String titleLineValue=null;
        for(int i=0;i<propertyNameList.length;i++){
            String currentProperty=propertyNameList[i];
            if(i!=0){
                titleLineValue=titleLineValue+","+currentProperty;
            }else{
                titleLineValue=currentProperty;
            }
        }
        List<Map<String,String>> resultDataList=new ArrayList<>();
        if(resultMeasurableList!=null){
            for(Measurable currentMeasurable:resultMeasurableList){
                Map<String,String> valueMap=new HashMap<>();
                resultDataList.add(valueMap);
                for(int i=0;i<propertyNameList.length;i++){
                    String currentPropertyName=propertyNameList[i];
                    Property currentProperty=currentMeasurable.getProperty(currentPropertyName);
                    if(currentProperty==null){
                        valueMap.put(currentPropertyName,null);
                    }else {
                        if (currentProperty.getPropertyType().equals(PropertyType.DATE)) {
                            Date propertyValue = (Date) currentProperty.getPropertyValue();
                            valueMap.put(currentPropertyName,"" + propertyValue.getTime());
                        } else {
                            valueMap.put(currentPropertyName,currentProperty.getPropertyValue().toString());
                        }
                    }
                }
            }
        }
        return resultDataList;
    }

    private static List<Map<String,String>> generateMeasurableTypePropertiesJSON(String spaceName,List<? extends Measurable> resultMeasurableList,String properties,Map<String,String> propertiesAliasNameMap){
        String[] propertyNameList=properties.split(",");
        if(propertyNameList.length==0){
            return null;
        }
        String titleLineValue=null;
        for(int i=0;i<propertyNameList.length;i++){
            String currentProperty=propertyNameList[i];
            if(i!=0){
                titleLineValue=titleLineValue+","+currentProperty;
            }else{
                titleLineValue=currentProperty;
            }
        }
        List<Map<String,String>> resultDataList=new ArrayList<>();
        if(resultMeasurableList!=null){
            for(Measurable currentMeasurable:resultMeasurableList){
                Map<String,String> valueMap=new HashMap<>();
                resultDataList.add(valueMap);
                for(int i=0;i<propertyNameList.length;i++){
                    String currentPropertyName=propertyNameList[i];
                    Property currentProperty=currentMeasurable.getProperty(currentPropertyName);
                    if(currentProperty==null){
                        valueMap.put(currentPropertyName,null);
                    }else {
                        if (currentProperty.getPropertyType().equals(PropertyType.DATE)) {
                            Date propertyValue = (Date) currentProperty.getPropertyValue();
                            valueMap.put(currentPropertyName,"" + propertyValue.getTime());
                        } else {
                            valueMap.put(currentPropertyName,currentProperty.getPropertyValue().toString());
                        }
                    }

                    if(propertiesAliasNameMap!=null) {
                        String currentPropertyAliasName = propertiesAliasNameMap.get(currentPropertyName);
                        if(currentPropertyAliasName==null){
                            String propertyAliasName=null;
                            if(currentMeasurable instanceof Dimension){
                                propertyAliasName = getTypePropertyAliasName(
                                        spaceName,DiscoverSpaceOperationConstant.TYPEKIND_DIMENSION,((Dimension) currentMeasurable).getType(),currentProperty.getPropertyName());
                                if(propertyAliasName==null||propertyAliasName.equals("")){
                                    propertyAliasName=getCustomPropertyAliasName(spaceName,currentProperty.getPropertyName(),currentProperty.getPropertyType().toString());
                                }
                            }
                            if(currentMeasurable instanceof Fact){
                                propertyAliasName = getTypePropertyAliasName(
                                        spaceName,DiscoverSpaceOperationConstant.TYPEKIND_FACT,((Fact) currentMeasurable).getType(),currentProperty.getPropertyName());
                                if(propertyAliasName==null||propertyAliasName.equals("")){
                                    propertyAliasName=getCustomPropertyAliasName(spaceName,currentProperty.getPropertyName(),currentProperty.getPropertyType().toString());
                                }
                            }
                            if(currentMeasurable instanceof Relation){
                                propertyAliasName = getTypePropertyAliasName(
                                        spaceName,DiscoverSpaceOperationConstant.TYPEKIND_RELATION,((Relation) currentMeasurable).getType(),currentProperty.getPropertyName());
                                if(propertyAliasName==null||propertyAliasName.equals("")){
                                    propertyAliasName=getCustomPropertyAliasName(spaceName,currentProperty.getPropertyName(),currentProperty.getPropertyType().toString());
                                }
                            }
                            if(propertyAliasName!=null&&!propertyAliasName.equals("")) {
                                propertiesAliasNameMap.put(currentPropertyName, propertyAliasName);
                            }
                        }
                    }

                }
            }
        }
        return resultDataList;
    }

    // get type and property alias name logic
    public static final String TYPEKIND_AliasNameFactType="TypeKind_AliasName";
    public static final String TYPEPROPERTY_AliasNameFactType="TypeProperty_AliasName";
    public static final String CUSTOMPROPERTY_AliasNameFactType="CustomProperty_AliasName";
    public static final String MetaConfig_PropertyName_DiscoverSpace="discoverSpace";
    public static final String MetaConfig_PropertyName_TypeKind="typeKind";
    public static final String MetaConfig_PropertyName_TypeName="typeName";
    public static final String MetaConfig_PropertyName_TypeAliasName="typeAliasName";
    public static final String MetaConfig_PropertyName_TypePropertyName="typePropertyName";
    public static final String MetaConfig_PropertyName_TypePropertyAliasName="typePropertyAliasName";
    public static final String MetaConfig_PropertyName_CustomPropertyName="customPropertyName";
    public static final String MetaConfig_PropertyName_CustomPropertyType="customPropertyType";
    public static final String MetaConfig_PropertyName_CustomPropertyAliasName="customPropertyAliasName";

    private static HashMap<String,String> TYPEKIND_AliasNameMap=new HashMap<>();
    private static HashMap<String,String> TypeProperty_AliasNameMap=new HashMap<>();
    private static HashMap<String,String> CustomProperty_AliasNameMap=new HashMap<>();

    private static String getTypeKindAliasName(String spaceName,String typeKind,String typeName){
        String typeKindRecordKey=spaceName+"_"+typeKind+"_"+typeName;
        if(TYPEKIND_AliasNameMap.get(typeKindRecordKey)!=null){
            return TYPEKIND_AliasNameMap.get(typeKindRecordKey);
        }else{
            return "";
        }
    }

    private static String getTypePropertyAliasName(String spaceName,String typeKind,String typeName,String typePropertyName) {
        String propertyRecordKey=spaceName+"_"+typeKind+"_"+typeName+"_"+typePropertyName;
        if(TypeProperty_AliasNameMap.get(propertyRecordKey)!=null){
            return TypeProperty_AliasNameMap.get(propertyRecordKey);
        }else{
            return "";
        }
    }

    public static String getCustomPropertyAliasName(String spaceName,String customPropertyName,String customPropertyType) {
        String propertyRecordKey=spaceName+"_"+customPropertyName+"_"+customPropertyType;
        if(CustomProperty_AliasNameMap.get(propertyRecordKey)!=null){
            return CustomProperty_AliasNameMap.get(propertyRecordKey);
        }else{
            return "";
        }
    }

    public static void clearItemAliasNameCache(){
        TYPEKIND_AliasNameMap.clear();
        TypeProperty_AliasNameMap.clear();
        CustomProperty_AliasNameMap.clear();
    }

    public static void refreshItemAliasNameCache(){
        String metaConfigSpaceName = InfoAnalyseServicePropertyHandler.getPropertyValue(InfoAnalyseServicePropertyHandler.META_CONFIG_DISCOVERSPACE);
        InfoDiscoverSpace metaConfigSpace = null;
        try {
            metaConfigSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(metaConfigSpaceName);
            if(metaConfigSpace.hasFactType(TYPEKIND_AliasNameFactType)){
                ExploreParameters typeKindRecordEP = new ExploreParameters();
                typeKindRecordEP.setType(TYPEKIND_AliasNameFactType);
                typeKindRecordEP.setResultNumber(100000);
                InformationExplorer ie = metaConfigSpace.getInformationExplorer();
                List<Fact> typeAliasRecordFactsList = ie.discoverFacts(typeKindRecordEP);
                if(typeAliasRecordFactsList!=null){
                    String spaceName=null;
                    String typeKind=null;
                    String typeName=null;
                    for(Fact currentFact:typeAliasRecordFactsList){
                        Property _DiscoverSpace=currentFact.getProperty(MetaConfig_PropertyName_DiscoverSpace);
                        if(_DiscoverSpace!=null){
                            spaceName=_DiscoverSpace.getPropertyValue().toString();
                        }else{
                            spaceName=null;
                        }
                        Property _TypeKind=currentFact.getProperty(MetaConfig_PropertyName_TypeKind);
                        if(_TypeKind!=null){
                            typeKind=_TypeKind.getPropertyValue().toString();
                        }else{
                            typeKind=null;
                        }
                        Property _TypeName=currentFact.getProperty(MetaConfig_PropertyName_TypeName);
                        if(_TypeName!=null){
                            typeName=_TypeName.getPropertyValue().toString();
                        }else{
                            typeName=null;
                        }
                        Property _TypeAliasName=currentFact.getProperty(MetaConfig_PropertyName_TypeAliasName);
                        if(_TypeAliasName!=null){
                            String typeKindRecordKey=spaceName+"_"+typeKind+"_"+typeName;
                            String typeAliasName=_TypeAliasName.getPropertyValue().toString();
                            if(TYPEKIND_AliasNameMap.containsKey(typeKindRecordKey)) {
                                TYPEKIND_AliasNameMap.remove(typeKindRecordKey);
                            }
                            TYPEKIND_AliasNameMap.put(typeKindRecordKey,typeAliasName);
                        }
                    }
                }
            }

            if(metaConfigSpace.hasFactType(TYPEPROPERTY_AliasNameFactType)){
                ExploreParameters typeKindRecordEP = new ExploreParameters();
                typeKindRecordEP.setType(TYPEPROPERTY_AliasNameFactType);
                typeKindRecordEP.setResultNumber(100000);
                InformationExplorer ie = metaConfigSpace.getInformationExplorer();
                List<Fact> typePropertyAliasRecordFact = ie.discoverFacts(typeKindRecordEP);
                if(typePropertyAliasRecordFact!=null) {
                    String spaceName=null;
                    String typeKind=null;
                    String typeName=null;
                    String typePropertyName=null;
                    for(Fact currentFact:typePropertyAliasRecordFact){
                        Property _DiscoverSpace=currentFact.getProperty(MetaConfig_PropertyName_DiscoverSpace);
                        if(_DiscoverSpace!=null){
                            spaceName=_DiscoverSpace.getPropertyValue().toString();
                        }else{
                            spaceName=null;
                        }
                        Property _TypeKind=currentFact.getProperty(MetaConfig_PropertyName_TypeKind);
                        if(_TypeKind!=null){
                            typeKind=_TypeKind.getPropertyValue().toString();
                        }else{
                            typeKind=null;
                        }
                        Property _TypeName=currentFact.getProperty(MetaConfig_PropertyName_TypeName);
                        if(_TypeName!=null){
                            typeName=_TypeName.getPropertyValue().toString();
                        }else{
                            typeName=null;
                        }
                        Property _TypePropertyName=currentFact.getProperty(MetaConfig_PropertyName_TypePropertyName);
                        if(_TypePropertyName!=null){
                            typePropertyName=_TypePropertyName.getPropertyValue().toString();
                        }else{
                            typePropertyName=null;
                        }
                        Property _TypePropertyAliasName=currentFact.getProperty(MetaConfig_PropertyName_TypePropertyAliasName);
                        if(_TypePropertyAliasName!=null){
                            String propertyRecordKey=spaceName+"_"+typeKind+"_"+typeName+"_"+typePropertyName;
                            String typePropertyAliasName=_TypePropertyAliasName.getPropertyValue().toString();
                            if(TypeProperty_AliasNameMap.containsKey(propertyRecordKey)) {
                                TypeProperty_AliasNameMap.remove(propertyRecordKey);
                            }
                            TypeProperty_AliasNameMap.put(propertyRecordKey,typePropertyAliasName);
                        }
                    }
                }
            }

            if(metaConfigSpace.hasFactType(CUSTOMPROPERTY_AliasNameFactType)){
                ExploreParameters typeKindRecordEP = new ExploreParameters();
                typeKindRecordEP.setType(CUSTOMPROPERTY_AliasNameFactType);
                typeKindRecordEP.setResultNumber(100000);
                InformationExplorer ie = metaConfigSpace.getInformationExplorer();
                List<Fact> typePropertyAliasRecordFact = ie.discoverFacts(typeKindRecordEP);
                if(typePropertyAliasRecordFact!=null) {
                    String spaceName=null;
                    String customPropertyName=null;
                    String customPropertyType=null;
                    String customPropertyAliasName=null;
                    for(Fact currentFact:typePropertyAliasRecordFact){
                        Property _DiscoverSpace=currentFact.getProperty(MetaConfig_PropertyName_DiscoverSpace);
                        if(_DiscoverSpace!=null){
                            spaceName=_DiscoverSpace.getPropertyValue().toString();
                        }else{
                            spaceName=null;
                        }
                        Property _CustomPropertyName=currentFact.getProperty(MetaConfig_PropertyName_CustomPropertyName);
                        if(_CustomPropertyName!=null){
                            customPropertyName=_CustomPropertyName.getPropertyValue().toString();
                        }else{
                            customPropertyName=null;
                        }
                        Property _CustomPropertyType=currentFact.getProperty(MetaConfig_PropertyName_CustomPropertyType);
                        if(_CustomPropertyType!=null){
                            customPropertyType=_CustomPropertyType.getPropertyValue().toString();
                        }else{
                            customPropertyType=null;
                        }
                        Property _CustomPropertyAliasName=currentFact.getProperty(MetaConfig_PropertyName_CustomPropertyAliasName);
                        if(_CustomPropertyAliasName!=null){
                            customPropertyAliasName=_CustomPropertyAliasName.getPropertyValue().toString();
                        }else{
                            customPropertyAliasName=null;
                        }
                        if(_CustomPropertyAliasName!=null){
                            String propertyRecordKey=spaceName+"_"+customPropertyName+"_"+customPropertyType;
                            String typePropertyAliasName=customPropertyAliasName;
                            if(CustomProperty_AliasNameMap.containsKey(propertyRecordKey)) {
                                CustomProperty_AliasNameMap.remove(propertyRecordKey);
                            }
                            CustomProperty_AliasNameMap.put(propertyRecordKey,typePropertyAliasName);
                        }
                    }
                }
            }
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        }finally {
            if(metaConfigSpace!=null){
                metaConfigSpace.closeSpace();
            }
        }
    }

    public static TypeInstanceSimilarDataDetailVO getSimilarRelationableConnectedSameDimensions(String spaceName, String sourceRelationableId, String relatedDimensionsListStr, String filteringPattern){
        String[] dimensionsIdArray=relatedDimensionsListStr.split(",");
        List<String> relatedDimensionsList=new ArrayList<>();
        for(String currentDimensionId:dimensionsIdArray){
            relatedDimensionsList.add(currentDimensionId);
        }
        if(relatedDimensionsList==null||relatedDimensionsList.size()==0){
            return null;
        }
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            MeasurableInstanceDetailInfoVO sourceRelationableVO=getMeasurableInstanceDetailInfoVO(targetSpace,sourceRelationableId);
            if(sourceRelationableVO==null){
                return null;
            }

            TypeInstanceSimilarDataDetailVO typeInstanceSimilarDataDetailVO=new TypeInstanceSimilarDataDetailVO();

            List<Relation> sourceRelationableRelations=null;
            List<RelationDetailInfoVO> sourceRelationableAndDimensionsRelationsInfoList=new ArrayList<>();
            Measurable sourceMeasurable=targetSpace.getMeasurableById(sourceRelationableId);

            if(sourceMeasurable instanceof Fact){
                Fact currentFact=(Fact)sourceMeasurable;
                sourceRelationableRelations=currentFact.getAllRelations();
            }
            if(sourceMeasurable instanceof Dimension){
                Dimension currentDimension=(Dimension)sourceMeasurable;
                sourceRelationableRelations=currentDimension.getAllRelations();
            }

            for(Relation currentRelation:sourceRelationableRelations){
                Relationable fromRelationable=currentRelation.getFromRelationable();
                Relationable toRelationable=currentRelation.getToRelationable();
                String otherSideRelationableId=null;
                if(fromRelationable.getId().equals(sourceRelationableId)){
                    otherSideRelationableId=toRelationable.getId();
                    if(relatedDimensionsList.contains(otherSideRelationableId)){
                        RelationDetailInfoVO matchedRelationDetailInfoVO =getRelationDetailInfoById(targetSpace,currentRelation.getId());
                        sourceRelationableAndDimensionsRelationsInfoList.add(matchedRelationDetailInfoVO);
                    }
                }else{
                    otherSideRelationableId=fromRelationable.getId();
                    if(relatedDimensionsList.contains(otherSideRelationableId)){
                        RelationDetailInfoVO matchedRelationDetailInfoVO =getRelationDetailInfoById(targetSpace,currentRelation.getId());
                        sourceRelationableAndDimensionsRelationsInfoList.add(matchedRelationDetailInfoVO);
                    }
                }
            }
            MeasurableInstanceAndRelationsDetailInfoVO sourceMeasurableInstanceAndRelationsDetailInfoVO=new MeasurableInstanceAndRelationsDetailInfoVO();
            sourceMeasurableInstanceAndRelationsDetailInfoVO.setMeasurableInstanceDetailInfo(sourceRelationableVO);
            sourceMeasurableInstanceAndRelationsDetailInfoVO.setRelationsDetailInfoList(sourceRelationableAndDimensionsRelationsInfoList);
            typeInstanceSimilarDataDetailVO.setSourceTypeInstanceDetailInfo(sourceMeasurableInstanceAndRelationsDetailInfoVO);

            List<MeasurableInstanceAndRelationsDetailInfoVO> similarMeasurableInstanceAndRelationsDetailInfoList=new ArrayList<>();
            typeInstanceSimilarDataDetailVO.setSimilarTypeInstancesDetailInfoList(similarMeasurableInstanceAndRelationsDetailInfoList);

            InformationExplorer ie=targetSpace.getInformationExplorer();
            List<Relationable> similarRelationableList=null;
            if("ALL".equals(filteringPattern)){
                similarRelationableList=ie.discoverSimilarRelationablesRelatedToSameDimensions(sourceRelationableId,relatedDimensionsList,InformationExplorer.FilteringPattern.AND);
            }
            if("ANY".equals(filteringPattern)){
                similarRelationableList=ie.discoverSimilarRelationablesRelatedToSameDimensions(sourceRelationableId,relatedDimensionsList,InformationExplorer.FilteringPattern.OR);
            }
            if(similarRelationableList!=null) {
                for (Relationable currentRelationable : similarRelationableList) {
                    MeasurableInstanceDetailInfoVO currentMeasurableInstanceDetailInfoVO=getMeasurableInstanceDetailInfoVO(targetSpace,currentRelationable.getId());
                    MeasurableInstanceAndRelationsDetailInfoVO currentMeasurableInstanceAndRelationsDetailInfoVO=new MeasurableInstanceAndRelationsDetailInfoVO();
                    currentMeasurableInstanceAndRelationsDetailInfoVO.setMeasurableInstanceDetailInfo(currentMeasurableInstanceDetailInfoVO);
                    similarMeasurableInstanceAndRelationsDetailInfoList.add(currentMeasurableInstanceAndRelationsDetailInfoVO);

                    List<Relation> currentRelationableRelations=null;
                    List<RelationDetailInfoVO> currentRelationableAndDimensionsRelationsInfoList=new ArrayList<>();
                    if(currentRelationable instanceof Fact){
                        Fact currentFact=(Fact)currentRelationable;
                        currentRelationableRelations=currentFact.getAllRelations();
                    }
                    if(currentRelationable instanceof Dimension){
                        Dimension currentDimension=(Dimension)currentRelationable;
                        currentRelationableRelations=currentDimension.getAllRelations();
                    }

                    for(Relation currentRelation:currentRelationableRelations){
                        Relationable fromRelationable=currentRelation.getFromRelationable();
                        Relationable toRelationable=currentRelation.getToRelationable();
                        String otherSideRelationableId=null;
                        if(fromRelationable.getId().equals(currentRelationable.getId())){
                            otherSideRelationableId=toRelationable.getId();
                            if(relatedDimensionsList.contains(otherSideRelationableId)){
                                RelationDetailInfoVO matchedRelationDetailInfoVO =getRelationDetailInfoById(targetSpace,currentRelation.getId());
                                currentRelationableAndDimensionsRelationsInfoList.add(matchedRelationDetailInfoVO);
                            }
                        }else{
                            otherSideRelationableId=fromRelationable.getId();
                            if(relatedDimensionsList.contains(otherSideRelationableId)){
                                RelationDetailInfoVO matchedRelationDetailInfoVO =getRelationDetailInfoById(targetSpace,currentRelation.getId());
                                currentRelationableAndDimensionsRelationsInfoList.add(matchedRelationDetailInfoVO);
                            }
                        }
                    }
                    currentMeasurableInstanceAndRelationsDetailInfoVO.setRelationsDetailInfoList(currentRelationableAndDimensionsRelationsInfoList);
                }
            }
            return typeInstanceSimilarDataDetailVO;
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
        return null;
    }

    public static ShortestPathBetweenTwoMeasurablesDetailInfoVO getShortestPathBetweenTwoRelationable(String spaceName, String relationable1Id, String relationable2Id){
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            MeasurableInstanceDetailInfoVO relationableAVO=getMeasurableInstanceDetailInfoVO(targetSpace,relationable1Id);
            if(relationableAVO==null){
                return null;
            }
            MeasurableInstanceDetailInfoVO relationableBVO=getMeasurableInstanceDetailInfoVO(targetSpace,relationable2Id);
            if(relationableBVO==null){
                return null;
            }
            ShortestPathBetweenTwoMeasurablesDetailInfoVO shortestPathBetweenTwoMeasurablesDetailInfoVO=new ShortestPathBetweenTwoMeasurablesDetailInfoVO();
            shortestPathBetweenTwoMeasurablesDetailInfoVO.setMeasurableA(relationableAVO);
            shortestPathBetweenTwoMeasurablesDetailInfoVO.setMeasurableB(relationableBVO);

            InformationExplorer ie=targetSpace.getInformationExplorer();
            Stack<Relation> shortestPathRelationsStack=ie.discoverRelationablesShortestPath(relationable1Id,relationable2Id);
            if(shortestPathRelationsStack==null){
                return null;
            }
            List<RelationDetailInfoVO> pathRelationsDetailInfo =new ArrayList<>();
            shortestPathBetweenTwoMeasurablesDetailInfoVO.setPathRelationsDetailInfo(pathRelationsDetailInfo);
            for(int i=0;i<shortestPathRelationsStack.size();i++){
                Relation currentRelation=shortestPathRelationsStack.elementAt(i);
                RelationDetailInfoVO currentRelationDetailInfoVO=getRelationDetailInfoById(targetSpace, currentRelation.getId());
                pathRelationsDetailInfo.add(currentRelationDetailInfoVO);
            }
            return shortestPathBetweenTwoMeasurablesDetailInfoVO;
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
        return null;
    }

    public static MeasurablesPathInfoVO getSpecifiedPathBetweenTwoRelationable(String spaceName,String relationable1Id,String relationable2Id,String pathRelationIds){
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            MeasurableInstanceDetailInfoVO relationableAVO=getMeasurableInstanceDetailInfoVO(targetSpace,relationable1Id);
            if(relationableAVO==null){
                return null;
            }
            MeasurableInstanceDetailInfoVO relationableBVO=getMeasurableInstanceDetailInfoVO(targetSpace,relationable2Id);
            if(relationableBVO==null){
                return null;
            }
            String[] relationsIdArray=pathRelationIds.split(",");
            List<String> pathRelationsList=new ArrayList<>();
            for(String currentRelationId:relationsIdArray){
                pathRelationsList.add(currentRelationId);
            }
            if(pathRelationsList==null||pathRelationsList.size()==0){
                return null;
            }
            List<RelationDetailInfoVO> pathRelationList=new ArrayList<>();
            for(String currentRelationId:pathRelationsList){
                RelationDetailInfoVO currentRelationDetailInfoVO=getRelationDetailInfoById(targetSpace, currentRelationId);
                if(currentRelationDetailInfoVO==null){
                    return null;
                }
                pathRelationList.add(currentRelationDetailInfoVO);
            }
            MeasurablesPathInfoVO measurablesPathInfoVO=new MeasurablesPathInfoVO();
            measurablesPathInfoVO.setMeasurableA(relationableAVO);
            measurablesPathInfoVO.setMeasurableB(relationableBVO);
            measurablesPathInfoVO.setPathRelationsDetailInfo(pathRelationList);
            return measurablesPathInfoVO;
        }finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
    }

    public static AllPathsBetweenTwoMeasurablesDetailInfoVO getAllPathsBetweenTwoRelationable(String spaceName,String relationable1Id,String relationable2Id){
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            MeasurableInstanceDetailInfoVO relationableAVO=getMeasurableInstanceDetailInfoVO(targetSpace,relationable1Id);
            if(relationableAVO==null){
                return null;
            }
            MeasurableInstanceDetailInfoVO relationableBVO=getMeasurableInstanceDetailInfoVO(targetSpace,relationable2Id);
            if(relationableBVO==null){
                return null;
            }
            AllPathsBetweenTwoMeasurablesDetailInfoVO allPathsBetweenTwoMeasurablesDetailInfoVO=new AllPathsBetweenTwoMeasurablesDetailInfoVO();
            allPathsBetweenTwoMeasurablesDetailInfoVO.setMeasurableA(relationableAVO);
            allPathsBetweenTwoMeasurablesDetailInfoVO.setMeasurableB(relationableBVO);

            InformationExplorer ie=targetSpace.getInformationExplorer();
            Stack<Relation> shortestPathRelationsStack=ie.discoverRelationablesShortestPath(relationable1Id,relationable2Id);
            if(shortestPathRelationsStack==null){
                return null;
            }
            List<RelationDetailInfoVO> shortestPathRelationsDetailInfo =new ArrayList<>();
            allPathsBetweenTwoMeasurablesDetailInfoVO.setShortestPathRelationsDetailInfo(shortestPathRelationsDetailInfo);
            for(int i=0;i<shortestPathRelationsStack.size();i++){
                Relation currentRelation=shortestPathRelationsStack.elementAt(i);
                RelationDetailInfoVO currentRelationDetailInfoVO=getRelationDetailInfoById(targetSpace, currentRelation.getId());
                shortestPathRelationsDetailInfo.add(currentRelationDetailInfoVO);
            }

            ie=targetSpace.getInformationExplorer();
            List<Stack<Relation>> allPathsRelationsStackList=ie.discoverRelationablesAllPaths(relationable1Id,relationable2Id);

            List<List<RelationDetailInfoVO>> allPathsRelationsDetailInfo=new ArrayList<>();
            allPathsBetweenTwoMeasurablesDetailInfoVO.setAllPathsRelationsDetailInfo(allPathsRelationsDetailInfo);
            if(allPathsRelationsStackList!=null) {
                for (Stack<Relation> currentPathInfo : allPathsRelationsStackList) {
                    List<RelationDetailInfoVO> currentPathDetailInfoList = new ArrayList<>();
                    allPathsRelationsDetailInfo.add(currentPathDetailInfoList);
                    for (int i = 0; i < currentPathInfo.size(); i++) {
                        Relation currentRelation = currentPathInfo.elementAt(i);
                        RelationDetailInfoVO currentRelationDetailInfoVO = getRelationDetailInfoById(targetSpace, currentRelation.getId());
                        currentPathDetailInfoList.add(currentRelationDetailInfoVO);
                    }
                }
            }
            return allPathsBetweenTwoMeasurablesDetailInfoVO;
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
        return null;
    }

    public static PathsBetweenTwoMeasurablesDetailInfoVO getPathsBetweenTwoRelationable(String spaceName, String relationable1Id, String relationable2Id, String pathType, int pathNumber){
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            MeasurableInstanceDetailInfoVO relationableAVO=getMeasurableInstanceDetailInfoVO(targetSpace,relationable1Id);
            if(relationableAVO==null){
                return null;
            }
            MeasurableInstanceDetailInfoVO relationableBVO=getMeasurableInstanceDetailInfoVO(targetSpace,relationable2Id);
            if(relationableBVO==null){
                return null;
            }
            PathsBetweenTwoMeasurablesDetailInfoVO pathsBetweenTwoMeasurablesDetailInfoVO=new PathsBetweenTwoMeasurablesDetailInfoVO();
            pathsBetweenTwoMeasurablesDetailInfoVO.setMeasurableA(relationableAVO);
            pathsBetweenTwoMeasurablesDetailInfoVO.setMeasurableB(relationableBVO);

            InformationExplorer ie=targetSpace.getInformationExplorer();
            List<Stack<Relation>> relationStackList=null;
            if("LONGEST".equals(pathType)){
                relationStackList=ie.discoverRelationablesLongestPaths(relationable1Id,relationable2Id,pathNumber);
            }
            if("SHORTEST".equals(pathType)){
                relationStackList=ie.discoverRelationablesShortestPaths(relationable1Id,relationable2Id,pathNumber);
            }
            List<List<RelationDetailInfoVO>> pathsRelationsDetailInfo=new ArrayList<>();
            pathsBetweenTwoMeasurablesDetailInfoVO.setPathsRelationsDetailInfo(pathsRelationsDetailInfo);
            if(relationStackList!=null) {
                for (Stack<Relation> currentPathInfo : relationStackList) {
                    List<RelationDetailInfoVO> currentPathDetailInfoList = new ArrayList<>();
                    pathsRelationsDetailInfo.add(currentPathDetailInfoList);
                    for (int i = 0; i < currentPathInfo.size(); i++) {
                        Relation currentRelation = currentPathInfo.elementAt(i);
                        RelationDetailInfoVO currentRelationDetailInfoVO = getRelationDetailInfoById(targetSpace, currentRelation.getId());
                        currentPathDetailInfoList.add(currentRelationDetailInfoVO);
                    }
                }
            }
            return pathsBetweenTwoMeasurablesDetailInfoVO;
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
        return null;
    }

    public static PathsBetweenTwoMeasurablesDetailInfoVO getPathsContainPointedDatasBetweenTwoRelationables(String spaceName,String relationable1Id,String relationable2Id,String pathContainedDataIdsList){
        String[] pathDataIdArray=pathContainedDataIdsList.split(",");
        List<String> pathDataIdsList=new ArrayList<>();
        for(String currentId:pathDataIdArray){
            pathDataIdsList.add(currentId);
        }
        if(pathDataIdsList==null||pathDataIdsList.size()==0){
            return null;
        }
        InfoDiscoverSpace targetSpace=null;
        try {
            targetSpace = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
            MeasurableInstanceDetailInfoVO relationableAVO=getMeasurableInstanceDetailInfoVO(targetSpace,relationable1Id);
            if(relationableAVO==null){
                return null;
            }
            MeasurableInstanceDetailInfoVO relationableBVO=getMeasurableInstanceDetailInfoVO(targetSpace,relationable2Id);
            if(relationableBVO==null){
                return null;
            }
            PathsBetweenTwoMeasurablesDetailInfoVO pathsBetweenTwoMeasurablesDetailInfoVO=new PathsBetweenTwoMeasurablesDetailInfoVO();
            pathsBetweenTwoMeasurablesDetailInfoVO.setMeasurableA(relationableAVO);
            pathsBetweenTwoMeasurablesDetailInfoVO.setMeasurableB(relationableBVO);

            InformationExplorer ie=targetSpace.getInformationExplorer();
            List<Stack<Relation>> relationStackList=ie.discoverPathsConnectedWithSpecifiedRelationables(relationable1Id,relationable2Id,pathDataIdsList);
            List<List<RelationDetailInfoVO>> pathsRelationsDetailInfo=new ArrayList<>();
            pathsBetweenTwoMeasurablesDetailInfoVO.setPathsRelationsDetailInfo(pathsRelationsDetailInfo);
            if(relationStackList!=null) {
                for (Stack<Relation> currentPathInfo : relationStackList) {
                    List<RelationDetailInfoVO> currentPathDetailInfoList = new ArrayList<>();
                    pathsRelationsDetailInfo.add(currentPathDetailInfoList);
                    for (int i = 0; i < currentPathInfo.size(); i++) {
                        Relation currentRelation = currentPathInfo.elementAt(i);
                        RelationDetailInfoVO currentRelationDetailInfoVO = getRelationDetailInfoById(targetSpace, currentRelation.getId());
                        currentPathDetailInfoList.add(currentRelationDetailInfoVO);
                    }
                }
            }
            return pathsBetweenTwoMeasurablesDetailInfoVO;
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } finally {
            if(targetSpace!=null){
                targetSpace.closeSpace();
            }
        }
        return null;
    }
}
