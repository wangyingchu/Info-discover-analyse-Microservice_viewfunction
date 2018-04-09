package com.infoDiscover.infoAnalyse.service.restful.vo.dataCRUD;

import java.util.List;

public class DataPayloadVO {

    private String type;
    private String typeName;
    private String recordId;
    private List<DataPropertyPayloadVO> properties;
    private String propertyNames;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<DataPropertyPayloadVO> getProperties() {
        return properties;
    }

    public void setProperties(List<DataPropertyPayloadVO> properties) {
        this.properties = properties;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPropertyNames() {
        return propertyNames;
    }

    public void setPropertyNames(String propertyNames) {
        this.propertyNames = propertyNames;
    }
}


