package com.lazday.poslaravel.activity.transaction.detail

import com.lazday.poslaravel.data.model.transaction.detail.ResponseTransactionDetail

interface TransactionDetailContract {

    interface Presenter {
        fun getTransactionByInvoice( no_faktur: String )
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingDetail(loading: Boolean)
        fun onResultDetail(responseTransactionDetail: ResponseTransactionDetail)
    }
}