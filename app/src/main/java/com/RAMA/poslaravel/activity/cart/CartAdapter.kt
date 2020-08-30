package com.lazday.poslaravel.activity.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lazday.poslaravel.R
import com.lazday.poslaravel.data.model.cart.DataCart
import com.lazday.poslaravel.util.GlideHelper
import kotlinx.android.synthetic.main.adapter_cart.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartAdapter (val context: Context, var dataCart: ArrayList<DataCart>,
                   val clickListener: (DataCart, Int) -> Unit): RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
            LayoutInflater.from( parent.context ).inflate( R.layout.adapter_cart,
                    parent, false)
    )

    override fun getItemCount() = dataCart.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataCart[position])
        GlideHelper.setImage(context, dataCart[position].gambar_produk!!, holder.view.imvImage)
        holder.view.imvDelete.setOnClickListener {
            clickListener(dataCart[position], position)
            removeCart( position )
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val view = view
        fun bing(dataCart: DataCart){
            view.txvCategory.text = dataCart.kategori
            view.txvNameProduct.text = dataCart.nama_produk
            view.txvPrice.text = "${dataCart.harga_rupiah} x${dataCart.jumlah}"

            val total: Double = dataCart.jumlah!!.toDouble() * dataCart.harga!!.toDouble()
            val totalRupiah: String = NumberFormat.getNumberInstance(Locale.GERMANY).format(total)
            view.txvTotal.text = "Rp $totalRupiah"
        }
    }

    fun updateCart(newCart: List<DataCart>){
        dataCart.clear()
        dataCart.addAll(newCart)
        notifyDataSetChanged()
    }

    fun removeCart(position: Int) {
        dataCart.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataCart.size)
    }

    fun removeAll() {
        dataCart.clear()
        notifyDataSetChanged()
    }
}