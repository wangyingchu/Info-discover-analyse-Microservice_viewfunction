package com.infoDiscover.infoAnalyse.service.restful.vo;

import java.util.List;

/**
 * Created by wangychu on 3/6/17.
 */
public class RelationTypeSummaryVO extends TypePropertyableVO{

    private long relationSelfDataCount;
    private long relationDescendantDataCount;
    private String parentRelationTypeName;
    private List<String> childRelationTypes;

    public long getRelationSelfDataCount() {
        return relationSelfDataCount;
    }

    public void setRelationSelfDataCount(long relationSelfDataCount) {
        this.relationSelfDataCount = relationSelfDataCount;
    }

    public long getRelationDescendantDataCount() {
        return relationDescendantDataCount;
    }

    public void setRelationDescendantDataCount(long relationDescendantDataCount) {
        this.relationDescendantDataCount = relationDescendantDataCount;
    }

    public String getParentRelationTypeName() {
        return parentRelationTypeName;
    }

    public void setParentRelationTypeName(String parentRelationTypeName) {
        this.parentRelationTypeName = parentRelationTypeName;
    }

    public List<String> getChildRelationTypes() {
        return childRelationTypes;
    }

    public void setChildRelationTypes(List<String> childRelationTypes) {
        this.childRelationTypes = childRelationTypes;
    }
}
