package com.infoDiscover.infoAnalyse.service.restful.vo.dataCRUD;

public class DataPropertyPayloadVO {

    private String propertyType;
    private String propertyName;
    private Object propertyValue;
    private boolean isUniqueKey;

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Object getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(Object propertyValue) {
        this.propertyValue = propertyValue;
    }

    public boolean getIsUniqueKey() {
        return isUniqueKey;
    }

    public void setIsUniqueKey(boolean uniqueKey) {
        isUniqueKey = uniqueKey;
    }
}