package com.infoDiscover.infoAnalyse.service.restful.vo;

import java.util.List;

/**
 * Created by wangychu on 3/6/17.
 */
public class MeasurableVO {

    private List<PropertyVO> measurableProperties;
    private String recordId;

    public List<PropertyVO> getMeasurableProperties() {
        return measurableProperties;
    }

    public void setMeasurableProperties(List<PropertyVO> measurableProperties) {
        this.measurableProperties = measurableProperties;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
