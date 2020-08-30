package com.lazday.poslaravel.activity.cart.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.lazday.poslaravel.R
import com.lazday.poslaravel.activity.product.ProductActivity
import com.lazday.poslaravel.data.Constant
import com.lazday.poslaravel.data.database.PrefsManager
import com.lazday.poslaravel.data.model.cart.ResponseCartUpdate
import kotlinx.android.synthetic.main.activity_cart_add.*

class CartAddActivity : AppCompatActivity(), CartAddContract.View {
    private val tagLog: String = "CartAddActivity"

    lateinit var cartAddPresenter: CartAddPresenter
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_add)
        supportActionBar!!.title = "Tambah Produk"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        prefsManager = PrefsManager(this)
        cartAddPresenter = CartAddPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        if (Constant.IS_CHANGED) {
            Constant.IS_CHANGED = false
            edtProduct.setText( Constant.PRODUCT_NAME )
            txvQty.visibility = View.VISIBLE
            npQuantity.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.PRODUCT_ID = 0
        Constant.PRODUCT_NAME = ""
        Constant.PRODUCT_QTY = 0
    }

    override fun initActivity() {
        txvQty.visibility = View.GONE
        npQuantity.visibility = View.GONE

        edtProduct.setOnClickListener {
            startActivity(Intent(this, ProductActivity::class.java))
        }

        npQuantity.minValue = 1
        npQuantity.maxValue = 25
        npQuantity.wrapSelectorWheel = true
        npQuantity.setOnValueChangedListener { numberPicker, i, i2 ->
            Constant.PRODUCT_QTY = i2.toLong()
            Log.d(tagLog, "qty: i $i i2 $i2")
        }

        btnSubmit.setOnClickListener {
            if (Constant.PRODUCT_ID > 0) {
                Constant.PRODUCT_QTY = if (Constant.PRODUCT_QTY > 0) Constant.PRODUCT_QTY else 1
                cartAddPresenter.addCart(
                    prefsManager.prefsUsername, Constant.PRODUCT_ID, Constant.PRODUCT_QTY
                )
            } else {
                edtProduct.error = "Tidak boleh kosong"
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading) {
            true -> {
                progress.visibility = View.VISIBLE
                btnSubmit.visibility = View.GONE
            }

            false -> {
                progress.visibility = View.GONE
                btnSubmit.visibility = View.VISIBLE
            }
        }
    }

    override fun onResult(responseCartAdd: ResponseCartUpdate) {
        if (responseCartAdd.status) {
            Constant.IS_CHANGED = true
            finish()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
