package com.infoDiscover.infoAnalyse.service.restful.vo;

import java.util.List;
import java.util.Map;

/**
 * Created by wangychu on 6/7/17.
 */
public class MeasurablePropertiesQueryBriefResultSetVO {

    private String measurableName;
    private String measurableAliasName;
    private String discoverSpaceName;
    private List<Map<String,String>> propertyRowData;
    private Map<String,String> propertiesAliasNameMap;

    public String getMeasurableName() {
        return measurableName;
    }

    public void setMeasurableName(String measurableName) {
        this.measurableName = measurableName;
    }

    public String getMeasurableAliasName() {
        return measurableAliasName;
    }

    public void setMeasurableAliasName(String measurableAliasName) {
        this.measurableAliasName = measurableAliasName;
    }

    public String getDiscoverSpaceName() {
        return discoverSpaceName;
    }

    public void setDiscoverSpaceName(String discoverSpaceName) {
        this.discoverSpaceName = discoverSpaceName;
    }

    public List<Map<String, String>> getPropertyRowData() {
        return propertyRowData;
    }

    public void setPropertyRowData(List<Map<String, String>> propertyRowData) {
        this.propertyRowData = propertyRowData;
    }

    public Map<String, String> getPropertiesAliasNameMap() {
        return propertiesAliasNameMap;
    }

    public void setPropertiesAliasNameMap(Map<String, String> propertiesAliasNameMap) {
        this.propertiesAliasNameMap = propertiesAliasNameMap;
    }
}
