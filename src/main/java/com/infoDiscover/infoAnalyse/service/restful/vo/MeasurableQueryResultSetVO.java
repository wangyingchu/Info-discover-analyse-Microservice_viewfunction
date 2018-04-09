package com.infoDiscover.infoAnalyse.service.restful.vo;

import java.util.List;

/**
 * Created by wangychu on 3/6/17.
 */
public class MeasurableQueryResultSetVO {
    private String measurableName;
    private String discoverSpaceName;
    private String measurableType;
    private long measurableRecordCount;
    private int pageSize;
    private int currentPage;
    private String querySQL;
    private List<MeasurableVO> measurableValues;
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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getQuerySQL() {
        return querySQL;
    }

    public void setQuerySQL(String querySQL) {
        this.querySQL = querySQL;
    }

    public List<MeasurableVO> getMeasurableValues() {
        return measurableValues;
    }

    public void setMeasurableValues(List<MeasurableVO> measurableValues) {
        this.measurableValues = measurableValues;
    }

    public List<TypePropertyVO> getTypeProperties() {
        return typeProperties;
    }

    public void setTypeProperties(List<TypePropertyVO> typeProperties) {
        this.typeProperties = typeProperties;
    }
}
