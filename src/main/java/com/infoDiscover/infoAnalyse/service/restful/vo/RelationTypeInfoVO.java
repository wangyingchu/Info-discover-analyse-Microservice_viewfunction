package com.infoDiscover.infoAnalyse.service.restful.vo;

import java.util.List;

/**
 * Created by wangychu on 2/21/17.
 */
public class RelationTypeInfoVO {
    private String typeName;
    private long typeDataRecordCount;
    private int descendantRelationTypesNumber;
    private List<RelationTypeInfoVO> childRelationTypeInfosVOList;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public long getTypeDataRecordCount() {
        return typeDataRecordCount;
    }

    public void setTypeDataRecordCount(long typeDataRecordCount) {
        this.typeDataRecordCount = typeDataRecordCount;
    }

    public int getDescendantRelationTypesNumber() {
        return descendantRelationTypesNumber;
    }

    public void setDescendantRelationTypesNumber(int descendantRelationTypesNumber) {
        this.descendantRelationTypesNumber = descendantRelationTypesNumber;
    }

    public List<RelationTypeInfoVO> getChildRelationTypeInfosVOList() {
        return childRelationTypeInfosVOList;
    }

    public void setChildRelationTypeInfosVOList(List<RelationTypeInfoVO> childRelationTypeInfosVOList) {
        this.childRelationTypeInfosVOList = childRelationTypeInfosVOList;
    }
}
