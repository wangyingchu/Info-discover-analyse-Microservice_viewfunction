package com.infoDiscover.infoAnalyse.service.restful.vo;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangychu on 4/11/17.
 */
@XmlRootElement(name = "RelationDetailInfoVO")
public class RelationDetailInfoVO {
    private String id;
    private String discoverSpaceName;
    private String relationTypeName;
    private String relationTypeAliasName;
    private RelationableValueDetailVO fromRelationable;
    private RelationableValueDetailVO toRelationable;
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

    public String getRelationTypeName() {
        return relationTypeName;
    }

    public void setRelationTypeName(String relationTypeName) {
        this.relationTypeName = relationTypeName;
    }

    public RelationableValueDetailVO getFromRelationable() {
        return fromRelationable;
    }

    public void setFromRelationable(RelationableValueDetailVO fromRelationable) {
        this.fromRelationable = fromRelationable;
    }

    public RelationableValueDetailVO getToRelationable() {
        return toRelationable;
    }

    public void setToRelationable(RelationableValueDetailVO toRelationable) {
        this.toRelationable = toRelationable;
    }

    public List<PropertyVO> getPropertiesValueList() {
        return propertiesValueList;
    }

    public void setPropertiesValueList(List<PropertyVO> propertiesValueList) {
        this.propertiesValueList = propertiesValueList;
    }

    public String getRelationTypeAliasName() {
        return relationTypeAliasName;
    }

    public void setRelationTypeAliasName(String relationTypeAliasName) {
        this.relationTypeAliasName = relationTypeAliasName;
    }
}
