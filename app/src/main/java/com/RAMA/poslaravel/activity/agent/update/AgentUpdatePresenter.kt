package com.lazday.poslaravel.activity.agent.update

import android.util.Log
import com.lazday.poslaravel.data.model.agent.ResponseAgentUpdate
import com.lazday.poslaravel.data.model.agent.ResponseAgentDetail
import com.lazday.poslaravel.network.ApiService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AgentUpdatePresenter(val view: AgentUpdateContract.View): AgentUpdateContract.Presenter {
    private val tagLog: String = "AgentUpdatePresenter"

    init {
        view.onLoading(false)
    }

    override fun getDetail(kd_agen: Long) {
        view.onLoading(true)
        ApiService.endpoint.getAgentDetail( kd_agen )
            .enqueue(object : Callback<ResponseAgentDetail>{
                override fun onFailure(call: Call<ResponseAgentDetail>, t: Throwable) {
                    view.onLoading(false)
                    Log.d(tagLog, "response: $t")
                }

                override fun onResponse(
                    call: Call<ResponseAgentDetail>,
                    response: Response<ResponseAgentDetail>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseAgent: ResponseAgentDetail? = response.body()
                        view.onResultDetail(responseAgent!!)
                    }
                }

            })
    }

    override fun updateAgent( kd_agen: Long, nama_toko: String, nama_pemilik: String, alamat: String,
                              latitude: String, longitude: String, gambar_toko: File? ) {

        val requestBody: RequestBody
        val multipartBody: MultipartBody.Part?

        if (gambar_toko != null) {
            requestBody = RequestBody.create(MediaType.parse("image/*"), gambar_toko)
            multipartBody = MultipartBody.Part.createFormData("gambar_toko",
                gambar_toko.name, requestBody)

        } else {
            requestBody = RequestBody.create(MediaType.parse("image/*"), "")
            multipartBody = MultipartBody.Part.createFormData("gambar_toko",
                "", requestBody)
        }

        view.onLoading(true)
        ApiService.endpoint.updateAgent(kd_agen, nama_toko, nama_pemilik, alamat, latitude, longitude,
            multipartBody, "PATCH")
            .enqueue(object : Callback<ResponseAgentUpdate>{
                override fun onFailure(call: Call<ResponseAgentUpdate>, t: Throwable) {
                    view.onLoading(false)
                    view.onLoading(false)
                }

                override fun onResponse(call: Call<ResponseAgentUpdate>,
                                        response: Response<ResponseAgentUpdate> ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseAgent: ResponseAgentUpdate? = response.body()
                        view.onResultUpdate(responseAgent!!)
                    }
                }

            })
    }
}