package com.infoDiscover.infoAnalyse.service.restful.vo.typeInstance;

import com.infoDiscover.infoAnalyse.service.restful.vo.MeasurableInstanceDetailInfoVO;
import com.infoDiscover.infoAnalyse.service.restful.vo.RelationDetailInfoVO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangychu on 5/9/17.
 */
@XmlRootElement(name = "PathsBetweenTwoMeasurablesDetailInfoVO")
public class PathsBetweenTwoMeasurablesDetailInfoVO {

    private MeasurableInstanceDetailInfoVO measurableA;
    private MeasurableInstanceDetailInfoVO measurableB;
    private List<List<RelationDetailInfoVO>> pathsRelationsDetailInfo;

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


    public List<List<RelationDetailInfoVO>> getPathsRelationsDetailInfo() {
        return pathsRelationsDetailInfo;
    }

    public void setPathsRelationsDetailInfo(List<List<RelationDetailInfoVO>> pathsRelationsDetailInfo) {
        this.pathsRelationsDetailInfo = pathsRelationsDetailInfo;
    }
}
