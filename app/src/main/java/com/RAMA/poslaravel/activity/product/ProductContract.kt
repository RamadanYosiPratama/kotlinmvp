package com.lazday.poslaravel.activity.product

import com.lazday.poslaravel.data.model.product.ResponseCategoryList
import com.lazday.poslaravel.data.model.product.ResponseProductList

interface ProductContract {

    interface Presenter {
        fun getCategory()
        fun getProduct(kd_kategori: Long)
    }

    interface View {
        fun initActivity()
        fun onLoading(loading: Boolean)
        fun onResultCategory(responseCategoryList: ResponseCategoryList)
        fun onResultProduct(responseProductList: ResponseProductList)
    }

}