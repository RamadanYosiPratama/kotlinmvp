package com.lazday.poslaravel.activity.agent.insert

import com.lazday.poslaravel.data.model.agent.ResponseAgentUpdate
import java.io.File

interface AgentInsertContract {

    interface Presenter {
        fun insertAgent(nama_toko: String, nama_pemilik: String, alamat: String, latitude: String,
                        longitude: String, gambar_toko: File)
    }

    interface View {
        fun onLoading(loading: Boolean)
        fun onResult(responseAgentUpdate: ResponseAgentUpdate)
        fun showMessage(message: String)
    }
}