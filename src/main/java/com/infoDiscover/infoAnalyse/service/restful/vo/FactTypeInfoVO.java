package com.infoDiscover.infoAnalyse.service.restful.vo;

/**
 * Created by wangychu on 2/21/17.
 */
public class FactTypeInfoVO {

    private String typeName;
    private long typeDataRecordCount;

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
}
