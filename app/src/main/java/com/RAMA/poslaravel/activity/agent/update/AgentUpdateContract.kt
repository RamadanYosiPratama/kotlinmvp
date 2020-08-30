package com.lazday.poslaravel.activity.agent.update

import com.lazday.poslaravel.data.model.agent.ResponseAgentUpdate
import com.lazday.poslaravel.data.model.agent.ResponseAgentDetail
import java.io.File

interface AgentUpdateContract {

    interface Presenter {
        fun getDetail(kd_agen: Long)
        fun updateAgent(kd_agen: Long, nama_toko: String, nama_pemilik: String, alamat: String, latitude: String,
                        longitude: String, gambar_toko: File?)
    }

    interface View {
        fun onLoading(loading: Boolean)
        fun onResultDetail(responseAgentDetail: ResponseAgentDetail)
        fun onResultUpdate(responseAgentUpdate: ResponseAgentUpdate)
        fun showMessage(message: String)
    }
}