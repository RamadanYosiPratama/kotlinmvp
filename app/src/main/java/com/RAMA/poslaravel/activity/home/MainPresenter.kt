package com.lazday.poslaravel.activity.home

class MainPresenter (val view : MainContract.View) {

    init {
        view.initActivity()
    }
}