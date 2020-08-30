package com.lazday.poslaravel.activity.product

import com.lazday.poslaravel.data.model.product.ResponseCategoryList
import com.lazday.poslaravel.data.model.product.ResponseProductList
import com.lazday.poslaravel.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PoductPresenter (val view : ProductContract.View): ProductContract.Presenter {

    init {
        view.initActivity()
    }

    override fun getCategory() {
        view.onLoading(true)
        ApiService.endpoint.getCategory()
            .enqueue(object : Callback<ResponseCategoryList> {
                override fun onFailure(call: Call<ResponseCategoryList>, t: Throwable) {
                    view.onLoading(false)
                }

                override fun onResponse(call: Call<ResponseCategoryList>,
                                        response: Response<ResponseCategoryList>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseCategoryList: ResponseCategoryList? = response.body()
                        view.onResultCategory( responseCategoryList!! )
                    }
                }
            })
    }

    override fun getProduct(kd_kategori: Long) {
        view.onLoading(true)
        ApiService.endpoint.getProductByCategory(kd_kategori)
            .enqueue(object : Callback<ResponseProductList> {
                override fun onFailure(call: Call<ResponseProductList>, t: Throwable) {
                    view.onLoading(false)
                }

                override fun onResponse(call: Call<ResponseProductList>,
                                        response: Response<ResponseProductList>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseProductList: ResponseProductList? = response.body()
                        view.onResultProduct( responseProductList!! )
                    }
                }
            })
    }
}