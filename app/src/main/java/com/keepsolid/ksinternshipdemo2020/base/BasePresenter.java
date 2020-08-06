package com.keepsolid.ksinternshipdemo2020.base;

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();

}
