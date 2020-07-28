package com.keepsolid.ksinternshipdemo2020.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GitResponse {

    @SerializedName("total_count")
    private long totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteRequests;

    @SerializedName("items")
    private List<GitRepoItem> repoItems;


    public long getTotalcount() {
        return totalCount;
    }

    public void setTotalcount(long totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteRequests() {
        return incompleteRequests;
    }

    public void setIncompleteRequests(boolean incompleteRequests) {
        this.incompleteRequests = incompleteRequests;
    }

    public List<GitRepoItem> getItems() {
        return repoItems;
    }

    public void setItems(List<GitRepoItem> items) {
        this.repoItems = items;
    }
}
