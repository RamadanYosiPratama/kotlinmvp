package com.lazday.poslaravel.activity.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lazday.poslaravel.R
import com.lazday.poslaravel.activity.cart.add.CartAddActivity
import com.lazday.poslaravel.activity.agent.search.AgentSearchActivity
import com.lazday.poslaravel.data.Constant
import com.lazday.poslaravel.data.database.PrefsManager
import com.lazday.poslaravel.data.model.cart.DataCart
import com.lazday.poslaravel.data.model.cart.ResponseCartUpdate
import com.lazday.poslaravel.data.model.cart.ResponseCartList
import com.lazday.poslaravel.data.model.checkout.ResponseCheckout
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity(), CartContract.View, View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var prefsManager: PrefsManager
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartPresenter: CartPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        supportActionBar!!.title = "Keranjang"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        prefsManager = PrefsManager(this)
        cartAdapter = CartAdapter(this, arrayListOf()){ dataCart: DataCart, position: Int ->
            cartPresenter.deleteItemCart( dataCart.kd_keranjang!! )
        }
        cartPresenter = CartPresenter(this)
        cartPresenter.getCart( prefsManager.prefsUsername )
    }

    override fun onResume() {
        super.onResume()
        if (Constant.IS_CHANGED) {
            Constant.IS_CHANGED = false
            cartPresenter.getCart( prefsManager.prefsUsername )
            edtAgent.setText( Constant.AGENT_NAME )
        }
    }

    override fun initActivity() {
        txvReset.visibility = View.GONE
        edtAgent.visibility = View.GONE

        rcvCart.apply { layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }

        swipe.setOnRefreshListener (this)
        btnAdd.setOnClickListener(this)
        txvReset.setOnClickListener (this)
        edtAgent.setOnClickListener(this)
        btnCheckout.setOnClickListener (this)
    }

    override fun onClick(view: View?) {
        when(view!!.id) {
            btnAdd.id   -> startActivity(Intent(this, CartAddActivity::class.java))
            txvReset.id -> showDialog()
            edtAgent.id -> startActivity(Intent(this, AgentSearchActivity::class.java))
            btnCheckout.id -> {
                if (cartAdapter.dataCart.isNullOrEmpty()) {
                    showMessage("Keranjang kosong")
                } else {
                    if (edtAgent.text.isNullOrEmpty()) {
                        edtAgent.error = "Tidak boleh kosong"
                    } else {
                        cartPresenter.checkOut( prefsManager.prefsUsername, Constant.AGENT_ID )
                    }
                }
            }
        }
    }

    override fun onRefresh() {
        cartPresenter.getCart(prefsManager.prefsUsername)
    }

    override fun onLoadingCart(loading: Boolean) {
        when (loading) {
            true -> swipe.isRefreshing = true
            false -> swipe.isRefreshing = false
        }
    }

    override fun onResultCart(responseCartList: ResponseCartList) {
        val dataCart: List<DataCart> = responseCartList.dataCart
        if (dataCart.isNullOrEmpty()) {
            txvReset.visibility = View.GONE
            edtAgent.visibility = View.GONE
        } else {
            cartAdapter.updateCart(dataCart)
            txvReset.visibility = View.VISIBLE
            edtAgent.visibility = View.VISIBLE
        }
    }

    override fun onResultDelete(responseCartAdd: ResponseCartUpdate) {
        cartPresenter.getCart( prefsManager.prefsUsername )
        cartAdapter.removeAll()
    }

    override fun onLoadingCheckout(loading: Boolean) {
        when (loading) {
            true -> {
                btnCheckout.isEnabled = false
                btnCheckout.setText("Memuat...")
            }
            false -> {
                btnCheckout.isEnabled = true
                btnCheckout.setText("Checkout")
            }
        }
    }

    override fun onResultCheckout(responseCheckout: ResponseCheckout) {
        cartPresenter.getCart( prefsManager.prefsUsername )
        cartAdapter.removeAll()
    }

    override fun showDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Hapus semua item dalam Keranjang?")

        dialog.setPositiveButton("Hapus") { dialog, which ->
            cartPresenter.deleteCart( prefsManager.prefsUsername )
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.CART_ID = 0
        Constant.AGENT_NAME = ""
    }

}
