package com.lazday.poslaravel.activity.cart.add

import com.lazday.poslaravel.data.model.cart.ResponseCartUpdate

interface CartAddContract {

    interface Presenter {
        fun addCart(username: String, kdProduk: Long, jumlah: Long)
    }

    interface View {
        fun initActivity()
        fun onLoading(loading: Boolean)
        fun onResult(responseCartAdd: ResponseCartUpdate)
        fun showMessage(message: String)
    }

}