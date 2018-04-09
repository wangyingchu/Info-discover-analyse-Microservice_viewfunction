package com.infoDiscover.infoAnalyse.service.restful.vo.typeInstance;

import com.infoDiscover.infoAnalyse.service.restful.vo.MeasurableInstanceDetailInfoVO;
import com.infoDiscover.infoAnalyse.service.restful.vo.RelationDetailInfoVO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangychu on 5/3/17.
 */
@XmlRootElement(name = "AllPathsBetweenTwoMeasurablesDetailInfoVO")
public class AllPathsBetweenTwoMeasurablesDetailInfoVO {

    private MeasurableInstanceDetailInfoVO measurableA;
    private MeasurableInstanceDetailInfoVO measurableB;
    private List<RelationDetailInfoVO> shortestPathRelationsDetailInfo;
    private List<List<RelationDetailInfoVO>> allPathsRelationsDetailInfo;

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


    public List<RelationDetailInfoVO> getShortestPathRelationsDetailInfo() {
        return shortestPathRelationsDetailInfo;
    }

    public void setShortestPathRelationsDetailInfo(List<RelationDetailInfoVO> shortestPathRelationsDetailInfo) {
        this.shortestPathRelationsDetailInfo = shortestPathRelationsDetailInfo;
    }

    public List<List<RelationDetailInfoVO>> getAllPathsRelationsDetailInfo() {
        return allPathsRelationsDetailInfo;
    }

    public void setAllPathsRelationsDetailInfo(List<List<RelationDetailInfoVO>> allPathsRelationsDetailInfo) {
        this.allPathsRelationsDetailInfo = allPathsRelationsDetailInfo;
    }
}
