package com.infoDiscover.infoAnalyse.service.restful.vo;

/**
 * Created by wangychu on 3/5/17.
 */
public class TypePropertyVO {

    private String propertyName;
    private String propertyType;
    private String propertySourceOwner;
    private boolean mandatoryProperty;
    private boolean readOnlyProperty;
    private boolean nullableProperty;

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

    public String getPropertySourceOwner() {
        return propertySourceOwner;
    }

    public void setPropertySourceOwner(String propertySourceOwner) {
        this.propertySourceOwner = propertySourceOwner;
    }

    public boolean getMandatoryProperty() {
        return mandatoryProperty;
    }

    public void setMandatoryProperty(boolean mandatoryProperty) {
        this.mandatoryProperty = mandatoryProperty;
    }

    public boolean getReadOnlyProperty() {
        return readOnlyProperty;
    }

    public void setReadOnlyProperty(boolean readOnlyProperty) {
        this.readOnlyProperty = readOnlyProperty;
    }

    public boolean getNullableProperty() {
        return nullableProperty;
    }

    public void setNullableProperty(boolean nullableProperty) {
        this.nullableProperty = nullableProperty;
    }
}
