package com.infoDiscover.infoAnalyse.service.restful.vo;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangychu on 4/11/17.
 */

@XmlRootElement(name = "RelationableValueDetailVO")
public class RelationableValueDetailVO {
    private String id;
    private String discoverSpaceName;
    private String relationableTypeName;
    private String relationableTypeAliasName;
    private String relationableTypeKind;
    private List<PropertyVO> propertiesValueList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiscoverSpaceName() {
        return discoverSpaceName;
    }

    public void setDiscoverSpaceName(String discoverSpaceName) {
        this.discoverSpaceName = discoverSpaceName;
    }

    public String getRelationableTypeName() {
        return relationableTypeName;
    }

    public void setRelationableTypeName(String relationableTypeName) {
        this.relationableTypeName = relationableTypeName;
    }

    public String getRelationableTypeKind() {
        return relationableTypeKind;
    }

    public void setRelationableTypeKind(String relationableTypeKind) {
        this.relationableTypeKind = relationableTypeKind;
    }

    public List<PropertyVO> getPropertiesValueList() {
        return propertiesValueList;
    }

    public void setPropertiesValueList(List<PropertyVO> propertiesValueList) {
        this.propertiesValueList = propertiesValueList;
    }

    public String getRelationableTypeAliasName() {
        return relationableTypeAliasName;
    }

    public void setRelationableTypeAliasName(String relationableTypeAliasName) {
        this.relationableTypeAliasName = relationableTypeAliasName;
    }
}
