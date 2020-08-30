package com.lazday.poslaravel.data.model.error

import com.google.gson.annotations.SerializedName

data class ResponseError (
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String
)

