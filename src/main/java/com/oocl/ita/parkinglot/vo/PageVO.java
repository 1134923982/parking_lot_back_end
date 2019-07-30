package com.oocl.ita.parkinglot.vo;

import java.util.List;

public class PageVO<T> {

    private List<T> pageContent;

    private int total;

    public List<T> getPageContent() {
        return pageContent;
    }

    public void setPageContent(List<T> pageContent) {
        this.pageContent = pageContent;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
