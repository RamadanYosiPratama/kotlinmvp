package com.lazday.poslaravel.activity.cart

import com.lazday.poslaravel.data.model.cart.ResponseCartUpdate
import com.lazday.poslaravel.data.model.cart.ResponseCartList
import com.lazday.poslaravel.data.model.checkout.ResponseCheckout

interface CartContract {

    interface Presenter {
        fun getCart(username: String)
        fun deleteItemCart(kd_keranjang: Long)
        fun deleteCart(username: String)
        fun checkOut(username: String, kd_agent: Long)
    }

    interface View {
        fun initActivity()
        fun onLoadingCart(loading: Boolean)
        fun onResultCart(responseCartList: ResponseCartList)
        fun onResultDelete(responseCartUpdate: ResponseCartUpdate)

        fun onLoadingCheckout(loading: Boolean)
        fun onResultCheckout(responseCheckout: ResponseCheckout)

        fun showDialog()
        fun showMessage(message: String)
    }

}