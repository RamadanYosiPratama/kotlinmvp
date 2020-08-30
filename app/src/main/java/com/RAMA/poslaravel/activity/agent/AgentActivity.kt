package com.lazday.poslaravel.activity.agent

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lazday.poslaravel.R
import com.lazday.poslaravel.activity.agent.insert.AgentInsertActivity
import com.lazday.poslaravel.activity.agent.update.AgentUpdateActivity
import com.lazday.poslaravel.data.Constant
import com.lazday.poslaravel.data.model.agent.DataAgent
import com.lazday.poslaravel.data.model.agent.ResponseAgentList
import com.lazday.poslaravel.data.model.agent.ResponseAgentUpdate
import com.lazday.poslaravel.util.GlideHelper

import kotlinx.android.synthetic.main.activity_agent.*
import kotlinx.android.synthetic.main.content_agent.*
import kotlinx.android.synthetic.main.dialog_agent.view.*

class AgentActivity : AppCompatActivity(), AgentContract.View, OnMapReadyCallback {
    private val tagLog: String = "AgentActivity"

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(agent.latitude!!.toDouble(), agent.longitude!!.toDouble())
        googleMap.addMarker(MarkerOptions().position(latLng).title( agent.nama_toko ))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

    private lateinit var agentPresenter: AgentPresenter
    private lateinit var agentAdapter: AgentAdapter
    private lateinit var agent: DataAgent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Agen"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        agentPresenter = AgentPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        agentPresenter.getAgent()
    }

    override fun initActivity(){
        agentAdapter = AgentAdapter(this, arrayListOf()) {
                dataAgent: DataAgent, position: Int, type: String ->
            agent = dataAgent
            when (type) {
                "detail" -> showDialogDetail( dataAgent, position )
                "update" -> startActivity(Intent(this, AgentUpdateActivity::class.java))
                "delete" -> showDialogDelete( dataAgent, position )
            }
        }

        rcvAgent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = agentAdapter
        }

        swipe.setOnRefreshListener {
            agentPresenter.getAgent()
        }

        fab.setOnClickListener {
            startActivity( Intent(this, AgentInsertActivity::class.java) )
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
        agentAdapter.updateAgent(dataAgent)
    }

    override fun onResultDelete(responseAgentUpdate: ResponseAgentUpdate) {
        showMessage( responseAgentUpdate.msg )
    }

    override fun showDialogDelete(dataAgent: DataAgent, position: Int) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Hapus ${dataAgent.nama_toko}?")

        dialog.setPositiveButton("Hapus") { dialog, which ->
            agentPresenter.deleteAgent( Constant.AGENT_ID )
            agentAdapter.removeAgent(position)
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun showDialogDetail(dataAgent: DataAgent, position: Int) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_agent, null)

        GlideHelper.setImage( applicationContext,
            dataAgent.gambar_toko!!, view.imvStore!! )

        view.txvNameStore.text = dataAgent.nama_toko
        view.txvName.text = dataAgent.nama_pemilik
        view.txvAddress.text = dataAgent.alamat

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        view.imvClose.setOnClickListener {
            supportFragmentManager.beginTransaction().remove(mapFragment).commit()
            dialog.dismiss()
        }


        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}
