package com.infoDiscover.infoAnalyse.service.restful.vo.typeInstance;

import com.infoDiscover.infoAnalyse.service.restful.vo.RelationDetailInfoVO;
import com.infoDiscover.infoAnalyse.service.restful.vo.RelationableValueDetailVO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangychu on 4/11/17.
 */

@XmlRootElement(name = "TypeInstanceRelationsDetailVO")
public class TypeInstanceRelationsDetailVO {

    private RelationableValueDetailVO sourceTypeInstance;
    private List<RelationDetailInfoVO> relationsInfo;

    public RelationableValueDetailVO getSourceTypeInstance() {
        return sourceTypeInstance;
    }

    public void setSourceTypeInstance(RelationableValueDetailVO sourceTypeInstance) {
        this.sourceTypeInstance = sourceTypeInstance;
    }

    public List<RelationDetailInfoVO> getRelationsInfo() {
        return relationsInfo;
    }

    public void setRelationsInfo(List<RelationDetailInfoVO> relationsInfo) {
        this.relationsInfo = relationsInfo;
    }
}
