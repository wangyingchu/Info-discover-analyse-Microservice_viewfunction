package com.infoDiscover.infoAnalyse.service.restful.vo.dataCRUD;

import java.util.List;

public class FilteringItemVO {

    public enum filteringItemType {
        BETWEEN,EQUAL,GREATER_THAN_EQUAL,GREATER_THAN,IN_VALUE,LESS_THAN_EQUAL,LESS_THAN,NOT_EQUAL,NULL_VALUE,REGULAR_MATCH,SIMILAR
    }

    public enum matchingType{
        BeginWith,EndWith,Contain
    }

    public FilteringItemVO.filteringItemType getFilteringItemType() {
        return filteringItemType;
    }

    public void setFilteringItemType(FilteringItemVO.filteringItemType filteringItemType) {
        this.filteringItemType = filteringItemType;
    }

    private filteringItemType filteringItemType;
    private boolean reverseCondition=false;
    private String attributeType;
    private String attributeName;
    private Object attributeValue;
    private Object attributeFromValue;
    private Object attributeToValue;
    private List<Object> attributeValues;
    private matchingType matchingType;

    public boolean getReverseCondition() {
        return reverseCondition;
    }

    public void setReverseCondition(boolean reverseCondition) {
        this.reverseCondition = reverseCondition;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Object getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(Object attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Object getAttributeFromValue() {
        return attributeFromValue;
    }

    public void setAttributeFromValue(Object attributeFromValue) {
        this.attributeFromValue = attributeFromValue;
    }

    public Object getAttributeToValue() {
        return attributeToValue;
    }

    public void setAttributeToValue(Object attributeToValue) {
        this.attributeToValue = attributeToValue;
    }

    public List<Object> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(List<Object> attributeValues) {
        this.attributeValues = attributeValues;
    }

    public FilteringItemVO.matchingType getMatchingType() {
        return matchingType;
    }

    public void setMatchingType(FilteringItemVO.matchingType matchingType) {
        this.matchingType = matchingType;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }
}
