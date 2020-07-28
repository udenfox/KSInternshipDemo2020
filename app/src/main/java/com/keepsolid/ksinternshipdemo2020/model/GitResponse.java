package com.keepsolid.ksinternshipdemo2020.model;

import java.util.List;

public class GitResponse {

    private long total_count;
    private boolean incomplete_requests;
    private List<GitRepoItem> items;

    public long getTotal_count() {
        return total_count;
    }

    public void setTotal_count(long total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_requests() {
        return incomplete_requests;
    }

    public void setIncomplete_requests(boolean incomplete_requests) {
        this.incomplete_requests = incomplete_requests;
    }

    public List<GitRepoItem> getItems() {
        return items;
    }

    public void setItems(List<GitRepoItem> items) {
        this.items = items;
    }
}
