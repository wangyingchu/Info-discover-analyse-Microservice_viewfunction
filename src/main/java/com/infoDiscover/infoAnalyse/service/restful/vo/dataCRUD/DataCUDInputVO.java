package com.infoDiscover.infoAnalyse.service.restful.vo.dataCRUD;

public class DataCUDInputVO {

    private String discoverSpaceName;
    private DataPayloadWrapperVO dataPayload;

    public String getDiscoverSpaceName() {
        return discoverSpaceName;
    }

    public void setDiscoverSpaceName(String discoverSpaceName) {
        this.discoverSpaceName = discoverSpaceName;
    }

    public DataPayloadWrapperVO getDataPayload() {
        return dataPayload;
    }

    public void setDataPayload(DataPayloadWrapperVO dataPayload) {
        this.dataPayload = dataPayload;
    }
}
