package com.lazday.poslaravel.activity.cart.add

import com.lazday.poslaravel.data.model.cart.ResponseCartUpdate
import com.lazday.poslaravel.network.ApiEndpoint
import com.lazday.poslaravel.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartAddPresenter (val view : CartAddContract.View): CartAddContract.Presenter {

    init {
        view.initActivity()
        view.onLoading(false)
    }

    override fun addCart(username: String, kdProduk: Long, jumlah: Long) {
        view.onLoading(true)
        ApiService.endpoint.addCart(username, kdProduk, jumlah)
            .enqueue(object : Callback<ResponseCartUpdate> {
                override fun onFailure(call: Call<ResponseCartUpdate>, t: Throwable) {
                    view.onLoading(false)
                }

                override fun onResponse(call: Call<ResponseCartUpdate>,
                                        response: Response<ResponseCartUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseCartAdd: ResponseCartUpdate? = response.body()
                        view.showMessage( responseCartAdd!!.msg )
                        view.onResult( responseCartAdd )
                    }
                }
            })
    }
}