package com.lazday.poslaravel.activity.agent.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazday.poslaravel.R
import com.lazday.poslaravel.data.Constant
import com.lazday.poslaravel.data.model.agent.DataAgent
import com.lazday.poslaravel.data.model.agent.ResponseAgentList
import kotlinx.android.synthetic.main.activity_agent_search.*

class AgentSearchActivity : AppCompatActivity(),
    AgentSearchContract.View {

    private lateinit var agentSearchPresenter: AgentSearchPresenter
    private lateinit var agentSearchAdapter: AgentSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent_search)
        supportActionBar!!.title = "Pilih Agen"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        agentSearchPresenter = AgentSearchPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        agentSearchPresenter.getAgent()
    }

    override fun initActivity() {
        agentSearchAdapter = AgentSearchAdapter(this, arrayListOf()) {
                dataAgent: DataAgent, position: Int, type: String ->
            Constant.AGENT_ID = dataAgent.kd_agen!!
            Constant.AGENT_NAME = dataAgent.nama_toko!!
            Constant.IS_CHANGED = true
            finish()
        }

        rcvAgent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = agentSearchAdapter
        }

        swipe.setOnRefreshListener {
            agentSearchPresenter.getAgent()
        }

        edtSearch.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                agentSearchPresenter.searchAgent( edtSearch.text.toString() )
                true
            } else {
                false
            }
        }
    }

    override fun onLoadingAgent(loading: Boolean) {
        when (loading) {
            true -> swipe.isRefreshing = true
            false -> swipe.isRefreshing = false
        }
    }

    override fun onResultAgent(responseAgentList: ResponseAgentList) {
        val dataAgent: List<DataAgent> = responseAgentList.dataAgent
        agentSearchAdapter.updateAgent(dataAgent)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
