package com.infoDiscover.infoAnalyse.service.restful.vo.dataCRUD;

public class DataCUDResultVO {

    public enum operationResultCodeValue {
        SUCCESS,FAILURE,INVALID_INPUT,UNKNOWN,DATA_NOT_MODIFIED
    }

    public DataCUDResultVO(){
        this.setOperationReturnCode(operationResultCodeValue.UNKNOWN);
    }

    private long operationExecuteTime;
    private operationResultCodeValue operationReturnCode;
    private long modifiedDataCount;

    public long getOperationExecuteTime() {
        return operationExecuteTime;
    }

    public void setOperationExecuteTime(long operationExecuteTime) {
        this.operationExecuteTime = operationExecuteTime;
    }

    public operationResultCodeValue getOperationReturnCode() {
        return operationReturnCode;
    }

    public void setOperationReturnCode(operationResultCodeValue operationReturnCode) {
        this.operationReturnCode = operationReturnCode;
    }

    public long getModifiedDataCount() {
        return modifiedDataCount;
    }

    public void setModifiedDataCount(long modifiedDataCount) {
        this.modifiedDataCount = modifiedDataCount;
    }
}
