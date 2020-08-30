package com.lazday.poslaravel.activity.transaction.detail

import android.util.Log
import com.lazday.poslaravel.data.model.transaction.detail.ResponseTransactionDetail
import com.lazday.poslaravel.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionDetailPresenter(val view: TransactionDetailContract.View): TransactionDetailContract.Presenter {

    override fun getTransactionByInvoice( no_faktur: String ) {
        view.onLoadingDetail(true)
        ApiService.endpoint.getTransactionByInvoice( no_faktur )
            .enqueue(object : Callback<ResponseTransactionDetail>{
                override fun onFailure(call: Call<ResponseTransactionDetail>, t: Throwable) {
                    Log.e("TransDetailPresenter", t.toString())
                    view.onLoadingDetail(false)
                }

                override fun onResponse(call: Call<ResponseTransactionDetail>,
                                        response: Response<ResponseTransactionDetail> ) {
                    Log.e("TransDetailPresenter", response.toString())
                    view.onLoadingDetail(false)
                    if (response.isSuccessful) {
                        val responseTransactionDetail: ResponseTransactionDetail? = response.body()
                        view.onResultDetail(responseTransactionDetail!!)
                    }
                }

            })
    }
}