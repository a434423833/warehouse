package com.weiyebancai.warehouse.pagemodel;

import com.google.gson.annotations.SerializedName;

import java.io.StringWriter;

/**
 * 返回数据基类<br>
 *
 * @author wangran
 * @since 2016年3月04日
 */
public class BaseResult {
    /**
     * 错误信息<br>
     * 操作成功，此属性为null;
     */
    private String error = null;
    /**
     * 异常信息
     */
    private String exception;
    /**
     * 响应状态<br>
     * 业务错误码从10000开始<br>
     * 操作成功，返回 0
     */
    private int status = 0;
    /**
     * 是否报错 erp
     */
    private Boolean success = true;
    /**
     * 记录总数量
     */
    private Integer totalCount = 0;


    public BaseResult(){

    }
    public BaseResult(int result, String error) {
        setSuccess(false);
        this.setStatus(result);
        this.setError(error);
    }

    public BaseResult(Exception exception) {
        setSuccess(false);
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new java.io.PrintWriter(sw));
        this.setException(sw.toString());
    }

    public BaseResult(String error, Exception exception) {
        this(exception);
        this.setError(error);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
