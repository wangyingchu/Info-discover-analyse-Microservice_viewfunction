package com.infoDiscover.infoAnalyse.service.restful.vo;

import java.util.List;

/**
 * Created by wangychu on 3/13/17.
 */
public class MeasurableInstanceDetailInfoVO {

    private String discoverSpaceName;
    private String measurableType;
    private String measurableName;
    private String measurableAliasName;
    private List<PropertyVO> measurableProperties;
    private List<TypePropertyVO> typePropertyList;
    private String measurableId;

    public String getDiscoverSpaceName() {
        return discoverSpaceName;
    }

    public void setDiscoverSpaceName(String discoverSpaceName) {
        this.discoverSpaceName = discoverSpaceName;
    }

    public String getMeasurableType() {
        return measurableType;
    }

    public void setMeasurableType(String measurableType) {
        this.measurableType = measurableType;
    }

    public List<TypePropertyVO> getTypePropertyList() {
        return typePropertyList;
    }

    public void setTypePropertyList(List<TypePropertyVO> typePropertyList) {
        this.typePropertyList = typePropertyList;
    }

    public List<PropertyVO> getMeasurableProperties() {
        return measurableProperties;
    }

    public void setMeasurableProperties(List<PropertyVO> measurableProperties) {
        this.measurableProperties = measurableProperties;
    }

    public String getMeasurableName() {
        return measurableName;
    }

    public void setMeasurableName(String measurableName) {
        this.measurableName = measurableName;
    }

    public String getMeasurableId() {
        return measurableId;
    }

    public void setMeasurableId(String measurableId) {
        this.measurableId = measurableId;
    }

    public String getMeasurableAliasName() {
        return measurableAliasName;
    }

    public void setMeasurableAliasName(String measurableAliasName) {
        this.measurableAliasName = measurableAliasName;
    }
}
