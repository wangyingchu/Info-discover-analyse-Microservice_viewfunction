package com.infoDiscover.infoAnalyse.service.restful.vo.typeInstance;

import com.infoDiscover.infoAnalyse.service.restful.vo.RelationInfoVO;
import com.infoDiscover.infoAnalyse.service.restful.vo.RelationableValueVO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangychu on 2/15/17.
 */

@XmlRootElement(name = "TypeInstanceRelationsCycleVO")
public class TypeInstanceRelationsCycleVO {

    private RelationableValueVO sourceTypeInstance;

    private List<RelationInfoVO> relationsInfo;

    public RelationableValueVO getSourceTypeInstance() {
        return sourceTypeInstance;
    }

    public void setSourceTypeInstance(RelationableValueVO sourceTypeInstance) {
        this.sourceTypeInstance = sourceTypeInstance;
    }

    public List<RelationInfoVO> getRelationsInfo() {
        return relationsInfo;
    }

    public void setRelationsInfo(List<RelationInfoVO> relationsInfo) {
        this.relationsInfo = relationsInfo;
    }
}
