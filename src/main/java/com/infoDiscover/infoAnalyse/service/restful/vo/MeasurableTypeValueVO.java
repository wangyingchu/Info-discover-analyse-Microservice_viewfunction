package com.infoDiscover.infoAnalyse.service.restful.vo;

import java.util.List;

/**
 * Created by wangychu on 24/05/2017.
 */
public class MeasurableTypeValueVO {

    private List<PropertyVO> measurableProperties;
    private String measurableId;

    public List<PropertyVO> getMeasurableProperties() {
        return measurableProperties;
    }

    public void setMeasurableProperties(List<PropertyVO> measurableProperties) {
        this.measurableProperties = measurableProperties;
    }

    public String getMeasurableId() {
        return measurableId;
    }

    public void setMeasurableId(String measurableId) {
        this.measurableId = measurableId;
    }
}
