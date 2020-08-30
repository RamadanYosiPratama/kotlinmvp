package com.lazday.poslaravel.activity.transaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lazday.poslaravel.R

class TransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initView()
    }

    private fun initView() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, TransactionFragment(), "trans_fragment")
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        if (supportFragmentManager.findFragmentByTag("trans_fragment") == null )
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TransactionFragment(), "trans_fragment")
                .commit()
        else
            finish()

        return super.onSupportNavigateUp()
    }
}
