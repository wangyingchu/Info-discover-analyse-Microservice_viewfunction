package com.infoDiscover.infoAnalyse.service.restful.vo.dataCRUD;

import com.infoDiscover.infoAnalyse.service.restful.vo.MeasurableQueryResultSetVO;

public class DataRetrieveResultVO {

    public enum operationResultCodeValue {
        SUCCESS,FAILURE,INVALID_INPUT,UNKNOWN
    }

    public DataRetrieveResultVO(){
        this.setOperationReturnCode(operationResultCodeValue.UNKNOWN);
    }

    private MeasurableQueryResultSetVO measurableQueryResultSet;

    private long operationExecuteTime;

    private operationResultCodeValue operationReturnCode;

    public MeasurableQueryResultSetVO getMeasurableQueryResultSet() {
        return measurableQueryResultSet;
    }

    public void setMeasurableQueryResultSet(MeasurableQueryResultSetVO measurableQueryResultSet) {
        this.measurableQueryResultSet = measurableQueryResultSet;
    }

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
}
