package com.weiyebancai.warehouse.pagemodel;

public class DataResult<T> extends BaseResult {

    private T data;

    private Page page;

    public DataResult() {

    }

    public DataResult(T data) {
        this.setData(data);
    }

    public DataResult(T data, Page page) {
        this.setData(data);
        this.setPage(page);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Page getPage() {
        return this.page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
