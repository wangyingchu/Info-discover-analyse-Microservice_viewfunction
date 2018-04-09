package com.infoDiscover.infoAnalyse.service.restful.vo.typeInstance;

import com.infoDiscover.infoAnalyse.service.restful.vo.MeasurableInstanceDetailInfoVO;
import com.infoDiscover.infoAnalyse.service.restful.vo.RelationDetailInfoVO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangychu on 5/3/17.
 */
@XmlRootElement(name = "ShortestPathBetweenTwoMeasurablesDetailInfoVO")
public class ShortestPathBetweenTwoMeasurablesDetailInfoVO {

    private MeasurableInstanceDetailInfoVO measurableA;
    private MeasurableInstanceDetailInfoVO measurableB;
    private List<RelationDetailInfoVO> pathRelationsDetailInfo;

    public MeasurableInstanceDetailInfoVO getMeasurableA() {
        return measurableA;
    }

    public void setMeasurableA(MeasurableInstanceDetailInfoVO measurableA) {
        this.measurableA = measurableA;
    }

    public MeasurableInstanceDetailInfoVO getMeasurableB() {
        return measurableB;
    }

    public void setMeasurableB(MeasurableInstanceDetailInfoVO measurableB) {
        this.measurableB = measurableB;
    }


    public List<RelationDetailInfoVO> getPathRelationsDetailInfo() {
        return pathRelationsDetailInfo;
    }

    public void setPathRelationsDetailInfo(List<RelationDetailInfoVO> pathRelationsDetailInfo) {
        this.pathRelationsDetailInfo = pathRelationsDetailInfo;
    }
}
