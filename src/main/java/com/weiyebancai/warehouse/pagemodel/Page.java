/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.weiyebancai.warehouse.pagemodel;

import java.io.Serializable;

/**
 * 分页对象
 *
 * @author caohao 2018/2/8
 */
public class Page implements Serializable {

    /**
     * 页码，从1开始
     */
    private int pageNum = 1;
    /**
     * 页面大小
     */
    private int pageSize = 20;

    /**
     * 总数
     */
    private long total = 0;
    /**
     * 总页数
     */
    private int pages = 0;
    /**
     * 排序
     */
    private String orderBy = "asc";

    /**
     * 获取 页码，从1开始
     */
    public int getPageNum() {
        return this.pageNum;
    }

    /**
     * 设置 页码，从1开始
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * 获取 页面大小
     */
    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * 设置 页面大小
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取 总数
     */
    public long getTotal() {
        return this.total;
    }

    /**
     * 设置 总数
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * 获取 总页数
     */
    public int getPages() {
        return this.pages;
    }

    /**
     * 设置 总页数
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    /**
     * 获取 排序
     */
    public String getOrderBy() {
        return this.orderBy;
    }

    /**
     * 设置 排序
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
