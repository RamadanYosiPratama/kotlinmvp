package com.lazday.poslaravel.activity.agent.search

import com.lazday.poslaravel.data.model.agent.ResponseAgentList

interface AgentSearchContract {

    interface Presenter {
        fun getAgent()
        fun searchAgent(keyword: String)
    }

    interface View {
        fun initActivity()
        fun onLoadingAgent(loading: Boolean)
        fun onResultAgent(responseAgentList: ResponseAgentList)
    }
}