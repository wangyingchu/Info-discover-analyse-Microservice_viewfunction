package com.infoDiscover.infoAnalyse.service.restful.vo;

/**
 * Created by wangychu on 3/6/17.
 */
public class PropertyVO {

    private String propertyName;
    private String propertyAliasName;
    private String propertyType;
    private String propertyValue;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyAliasName() {
        return propertyAliasName;
    }

    public void setPropertyAliasName(String propertyAliasName) {
        this.propertyAliasName = propertyAliasName;
    }
}
