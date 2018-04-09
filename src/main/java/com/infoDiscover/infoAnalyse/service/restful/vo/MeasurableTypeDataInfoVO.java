package com.infoDiscover.infoAnalyse.service.restful.vo;

import java.util.List;

/**
 * Created by wangychu on 24/05/2017.
 */
public class MeasurableTypeDataInfoVO {

    private String measurableName;
    private String discoverSpaceName;
    private String measurableType;
    private long measurableRecordCount;
    private List<MeasurableTypeValueVO> measurableValues;
    private List<TypePropertyVO> typeProperties;

    public String getMeasurableName() {
        return measurableName;
    }

    public void setMeasurableName(String measurableName) {
        this.measurableName = measurableName;
    }

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

    public long getMeasurableRecordCount() {
        return measurableRecordCount;
    }

    public void setMeasurableRecordCount(long measurableRecordCount) {
        this.measurableRecordCount = measurableRecordCount;
    }

    public List<MeasurableTypeValueVO> getMeasurableValues() {
        return measurableValues;
    }

    public void setMeasurableValues(List<MeasurableTypeValueVO> measurableValues) {
        this.measurableValues = measurableValues;
    }

    public List<TypePropertyVO> getTypeProperties() {
        return typeProperties;
    }

    public void setTypeProperties(List<TypePropertyVO> typeProperties) {
        this.typeProperties = typeProperties;
    }
}
