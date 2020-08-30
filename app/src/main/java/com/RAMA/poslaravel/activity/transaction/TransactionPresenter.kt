package com.lazday.poslaravel.activity.transaction

import com.lazday.poslaravel.data.model.transaction.ResponseTransactionList
import com.lazday.poslaravel.data.model.transaction.detail.ResponseTransactionDetail
import com.lazday.poslaravel.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionPresenter(val view: TransactionContract.View): TransactionContract.Presenter {

    override fun getTransactionByUsername( username: String ){
        view.onLoadingTransaction(true)
        ApiService.endpoint.getTransactionByUsername( username )
            .enqueue(object : Callback<ResponseTransactionList>{
                override fun onFailure(call: Call<ResponseTransactionList>, t: Throwable) {
                    view.onLoadingTransaction(false)
                }

                override fun onResponse(
                    call: Call<ResponseTransactionList>,
                    response: Response<ResponseTransactionList> ) {
                    view.onLoadingTransaction(false)
                    if (response.isSuccessful) {
                        val responseTransactionList: ResponseTransactionList? = response.body()
                        view.onResultTransaction(responseTransactionList!!)
                    }
                }

            })
    }
}