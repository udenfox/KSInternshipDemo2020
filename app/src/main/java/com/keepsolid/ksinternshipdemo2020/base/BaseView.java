package com.keepsolid.ksinternshipdemo2020.base;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showProgress();

    void hideProgress();

    void hideKeyboard();

}
