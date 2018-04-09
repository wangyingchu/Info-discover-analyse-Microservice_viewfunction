package com.infoDiscover.infoAnalyse.service.restful.vo;

import java.util.List;

/**
 * Created by wangychu on 2/19/17.
 */
public class DimensionTypeInfoVO {
    private String typeName;
    private long typeDataRecordCount;
    private int descendantDimensionTypesNumber;
    private List<DimensionTypeInfoVO> childDimensionTypeInfosVOList;

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

    public int getDescendantDimensionTypesNumber() {
        return descendantDimensionTypesNumber;
    }

    public void setDescendantDimensionTypesNumber(int descendantDimensionTypesNumber) {
        this.descendantDimensionTypesNumber = descendantDimensionTypesNumber;
    }

    public List<DimensionTypeInfoVO> getChildDimensionTypeInfosVOList() {
        return childDimensionTypeInfosVOList;
    }

    public void setChildDimensionTypeInfosVOList(List<DimensionTypeInfoVO> childDimensionTypesVO) {
        this.childDimensionTypeInfosVOList = childDimensionTypesVO;
    }
}
