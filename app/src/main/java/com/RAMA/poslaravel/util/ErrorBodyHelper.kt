package com.lazday.poslaravel.util

import com.google.gson.Gson
import com.lazday.poslaravel.data.model.error.ResponseError
import com.lazday.poslaravel.data.model.login.ResponseLogin
import retrofit2.Response

class ErrorBodyHelper {

    companion object {

        fun showMessage(response: Response<ResponseLogin>): String {
            val gson = Gson()
            val responseError = gson.fromJson( response.errorBody()!!.string(),
                ResponseError::class.java )

            return responseError.msg
        }
    }
}