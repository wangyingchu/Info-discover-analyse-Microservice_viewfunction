package com.infoDiscover.infoAnalyse.service.restful.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangychu on 2/15/17.
 */

@XmlRootElement(name = "RelationInfoVO")
public class RelationInfoVO {
    private String id;
    private String discoverSpaceName;
    private String relationTypeName;
    private String relationTypeAliasName;
    private RelationableValueVO fromRelationable;
    private RelationableValueVO toRelationable;

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

    public RelationableValueVO getFromRelationable() {
        return fromRelationable;
    }

    public void setFromRelationable(RelationableValueVO fromRelationable) {
        this.fromRelationable = fromRelationable;
    }

    public RelationableValueVO getToRelationable() {
        return toRelationable;
    }

    public void setToRelationable(RelationableValueVO toRelationable) {
        this.toRelationable = toRelationable;
    }

    public String getRelationTypeAliasName() {
        return relationTypeAliasName;
    }

    public void setRelationTypeAliasName(String relationTypeAliasName) {
        this.relationTypeAliasName = relationTypeAliasName;
    }
}
