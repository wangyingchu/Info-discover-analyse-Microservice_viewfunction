package com.infoDiscover.infoAnalyse.service.restful.vo;

import java.util.List;

/**
 * Created by wangychu on 3/5/17.
 */
public class TypePropertyableVO {

    private String typeName;
    private List<TypePropertyVO> typePropertyList;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<TypePropertyVO> getTypePropertyList() {
        return typePropertyList;
    }

    public void setTypePropertyList(List<TypePropertyVO> typePropertyList) {
        this.typePropertyList = typePropertyList;
    }
}
