package com.lazday.poslaravel.activity.home

interface MainContract {

    interface View {
        fun initActivity()
        fun showMessage(message: String)
    }

}