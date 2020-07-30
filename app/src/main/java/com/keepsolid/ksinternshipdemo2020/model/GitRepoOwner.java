package com.keepsolid.ksinternshipdemo2020.model;

import androidx.room.ColumnInfo;

public class GitRepoOwner {

    @ColumnInfo(name="userId")
    private int id;

    @ColumnInfo(name="userLogin")
    private String login;

    @ColumnInfo(name="userAvatar")
    private String avatar_url;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitRepoOwner that = (GitRepoOwner) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}
