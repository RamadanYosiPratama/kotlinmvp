package com.lazday.poslaravel.activity.transaction

import com.lazday.poslaravel.data.model.transaction.ResponseTransactionList

interface TransactionContract {

    interface Presenter {
        fun getTransactionByUsername( username: String )
    }

    interface View {
        fun initView(view: android.view.View)
        fun onLoadingTransaction(loading: Boolean)
        fun onResultTransaction(responseTransactionList: ResponseTransactionList)
        fun onClickTransaction(invoice: String)
    }
}