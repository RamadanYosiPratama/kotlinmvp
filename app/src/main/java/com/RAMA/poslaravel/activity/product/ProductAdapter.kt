package com.lazday.poslaravel.activity.product

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lazday.poslaravel.R
import com.lazday.poslaravel.data.Constant
import com.lazday.poslaravel.data.model.product.DataProduct
import com.lazday.poslaravel.util.GlideHelper
import kotlinx.android.synthetic.main.adapter_product.view.*

class ProductAdapter (val context: Context, var product: ArrayList<DataProduct>):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
            LayoutInflater.from( parent.context ).inflate( R.layout.adapter_product,
                    parent, false)
    )

    override fun getItemCount() = product.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(product[position])
        GlideHelper.setImage(context, product[position].gambar_produk!!, holder.view.imvImage)
        holder.view.linProduct.setOnClickListener {
            Constant.PRODUCT_ID = product[position].kd_produk!!
            Constant.PRODUCT_NAME = product[position].nama_produk!!
            Constant.IS_CHANGED = true
            (context as Activity).finish()
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val view = view
        fun bing(product: DataProduct){
            view.txvName.text = product.nama_produk
            view.txvPrice.text = product.harga_rupiah
            view.txvStock.text = "Stok tersedia (${product.stok})"
        }
    }

    fun updateProduct(newProduct: List<DataProduct>){
        product.clear()
        product.addAll(newProduct)
        notifyDataSetChanged()
    }
}