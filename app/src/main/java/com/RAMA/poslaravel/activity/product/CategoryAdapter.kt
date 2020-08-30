package com.lazday.poslaravel.activity.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lazday.poslaravel.R
import com.lazday.poslaravel.data.Constant
import com.lazday.poslaravel.data.model.product.DataCategory
import com.lazday.poslaravel.util.GlideHelper
import kotlinx.android.synthetic.main.adapter_category.view.*

class CategoryAdapter (val context: Context, var category: ArrayList<DataCategory>,
                       val clickListener: (DataCategory, Int) -> Unit): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
            LayoutInflater.from( parent.context ).inflate( R.layout.adapter_category,
                    parent, false)
    )

    override fun getItemCount() = category.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(category[position])
        GlideHelper.setImage(context,category[position].gambar_kategori!!, holder.view.imvImage )
        holder.view.relCategory.setOnClickListener {
            Constant.CATEGORY_ID = category[position].kd_kategori!!
            clickListener(category[position], position)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val view = view
        fun bing(category: DataCategory){
            view.txvCategory.text = category.kategori
        }
    }

    fun updateCategory(newCategory: List<DataCategory>){
        category.clear()
        category.addAll(newCategory)
        notifyDataSetChanged()
    }
}