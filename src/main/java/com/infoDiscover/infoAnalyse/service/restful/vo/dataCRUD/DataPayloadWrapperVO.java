package com.infoDiscover.infoAnalyse.service.restful.vo.dataCRUD;

import java.util.List;

public class DataPayloadWrapperVO {

    private List<DataPayloadVO> data;

    public List<DataPayloadVO> getData() {
        return data;
    }

    public void setData(List<DataPayloadVO> data) {
        this.data = data;
    }
}
