package com.infoDiscover.infoAnalyse.service.restful.vo;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangychu on 5/2/17.
 */
@XmlRootElement(name = "MeasurableInstanceAndRelationsDetailInfoVO")
public class MeasurableInstanceAndRelationsDetailInfoVO {

    private MeasurableInstanceDetailInfoVO measurableInstanceDetailInfo;
    private List<RelationDetailInfoVO> relationsDetailInfoList;

    public MeasurableInstanceDetailInfoVO getMeasurableInstanceDetailInfo() {
        return measurableInstanceDetailInfo;
    }

    public void setMeasurableInstanceDetailInfo(MeasurableInstanceDetailInfoVO measurableInstanceDetailInfo) {
        this.measurableInstanceDetailInfo = measurableInstanceDetailInfo;
    }

    public List<RelationDetailInfoVO> getRelationsDetailInfoList() {
        return relationsDetailInfoList;
    }

    public void setRelationsDetailInfoList(List<RelationDetailInfoVO> relationsDetailInfoList) {
        this.relationsDetailInfoList = relationsDetailInfoList;
    }
}
