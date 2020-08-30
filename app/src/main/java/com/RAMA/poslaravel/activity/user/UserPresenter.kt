package com.lazday.poslaravel.activity.user

import com.lazday.poslaravel.data.database.PrefsManager

class UserPresenter (val view : UserContract.View): UserContract.Presenter {

    init {
        view.initActivity()
    }

    override fun doLogin(prefsManager: PrefsManager) {
        if (prefsManager.prefsIsLogin) view.onResultLogin(prefsManager)
    }

    override fun doLogout(prefsManager: PrefsManager) {
        prefsManager.logout()
        view.showMessage("Berhasil keluar")
        view.onResultLogout()
    }
}