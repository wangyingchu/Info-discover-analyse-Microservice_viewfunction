package com.infoDiscover.infoAnalyse.service.restful.vo;

import java.util.List;

/**
 * Created by wangychu on 3/6/17.
 */
public class DimensionTypeSummaryVO extends TypePropertyableVO{

    private long dimensionSelfDataCount;
    private long dimensionDescendantDataCount;
    private String parentDimensionTypeName;
    private List<String> childDimensionTypes;

    public long getDimensionSelfDataCount() {
        return dimensionSelfDataCount;
    }

    public void setDimensionSelfDataCount(long dimensionSelfDataCount) {
        this.dimensionSelfDataCount = dimensionSelfDataCount;
    }

    public long getDimensionDescendantDataCount() {
        return dimensionDescendantDataCount;
    }

    public void setDimensionDescendantDataCount(long dimensionDescendantDataCount) {
        this.dimensionDescendantDataCount = dimensionDescendantDataCount;
    }

    public String getParentDimensionTypeName() {
        return parentDimensionTypeName;
    }

    public void setParentDimensionTypeName(String parentDimensionTypeName) {
        this.parentDimensionTypeName = parentDimensionTypeName;
    }

    public List<String> getChildDimensionTypes() {
        return childDimensionTypes;
    }

    public void setChildDimensionTypes(List<String> childDimensionTypes) {
        this.childDimensionTypes = childDimensionTypes;
    }
}
