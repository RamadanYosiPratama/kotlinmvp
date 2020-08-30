package com.lazday.poslaravel.activity.login

import com.lazday.poslaravel.data.database.PrefsManager
import com.lazday.poslaravel.data.model.login.DataLogin
import com.lazday.poslaravel.data.model.login.ResponseLogin

interface LoginContract {

    interface Presenter {
        fun doLogin(username:String, password:String)
        fun setPrefs(prefsManager: PrefsManager, dataLogin: DataLogin)
    }

    interface View {
        fun initActivity()
        fun onLoading(loading: Boolean)
        fun onResult(responseLogin: ResponseLogin)
        fun showMessage(message: String)
    }

}