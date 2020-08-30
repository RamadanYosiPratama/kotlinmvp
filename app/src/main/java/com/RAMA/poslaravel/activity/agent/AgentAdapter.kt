package com.lazday.poslaravel.activity.agent

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lazday.poslaravel.R
import com.lazday.poslaravel.activity.agent.update.AgentUpdateActivity
import com.lazday.poslaravel.data.Constant
import com.lazday.poslaravel.data.model.agent.DataAgent
import com.lazday.poslaravel.util.GlideHelper
import kotlinx.android.synthetic.main.adapter_agent.view.*
import androidx.appcompat.widget.PopupMenu


class AgentAdapter (val context: Context, var dataAgent: ArrayList<DataAgent>,
                    val clickListener: (DataAgent, Int, String) -> Unit): RecyclerView.Adapter<AgentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
            LayoutInflater.from( parent.context ).inflate( R.layout.adapter_agent,
                    parent, false)
    )

    override fun getItemCount() = dataAgent.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataAgent[position])
        GlideHelper.setImage(context, dataAgent[position].gambar_toko!!, holder.view.imvImage)
        holder.view.imvImage.setOnClickListener {
            Constant.AGENT_ID = dataAgent[position].kd_agen!!
            clickListener(dataAgent[position], position, "detail")
        }
        holder.view.txvOptions.setOnClickListener {
            val popup = PopupMenu(context, holder.view.txvOptions)
            popup.inflate(R.menu.menu_options)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_update -> {
                        Constant.AGENT_ID = dataAgent[position].kd_agen!!
                        clickListener(dataAgent[position], position, "update")
                    }

                    R.id.action_delete -> {
                        Constant.AGENT_ID = dataAgent[position].kd_agen!!
                        clickListener(dataAgent[position], position, "delete")
                    }
                }
                true
            }
            popup.show()
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val view = view
        fun bing(dataAgent: DataAgent){
            view.txvNameStore.text = dataAgent.nama_toko
            view.txvLocation.text = dataAgent.alamat
        }
    }

    fun updateAgent(newDataAgent: List<DataAgent>){
        dataAgent.clear()
        dataAgent.addAll(newDataAgent)
        notifyDataSetChanged()
    }

    fun removeAgent(position: Int) {
        dataAgent.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataAgent.size)
    }
}