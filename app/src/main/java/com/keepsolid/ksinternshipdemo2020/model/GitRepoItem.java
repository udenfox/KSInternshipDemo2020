package com.keepsolid.ksinternshipdemo2020.model;


import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class GitRepoItem {

    private int id;
    private String name;

    @SerializedName("html_url")
    private Uri url;
    private String description;
    private GitRepoOwner owner;

    public GitRepoItem(int id, String name, Uri url, String description) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUrl() {
        return url;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GitRepoOwner getOwner() {
        return owner;
    }

    public void setOwner(GitRepoOwner owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitRepoItem that = (GitRepoItem) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

}