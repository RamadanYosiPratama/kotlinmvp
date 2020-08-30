package com.lazday.poslaravel.activity.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.lazday.poslaravel.R
import com.lazday.poslaravel.activity.agent.AgentActivity
import com.lazday.poslaravel.activity.login.LoginActivity
import com.lazday.poslaravel.activity.user.UserActivity
import com.lazday.poslaravel.activity.transaction.TransactionActivity
import com.lazday.poslaravel.data.database.PrefsManager
import kotlinx.android.synthetic.main.activity_main.*

class   MainActivity : AppCompatActivity(), MainContract.View {
    private val tagLog: String = "MainActivity"

    lateinit var presenter: MainPresenter
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun onResume() {
        super.onResume()
        when ( prefsManager.prefsIsLogin ) {
            true -> {
                txvUsername.text = prefsManager.prefsUsername
                txvUsername.visibility = View.VISIBLE
                txvLoginAs.visibility = View.VISIBLE
                crvUser.visibility = View.VISIBLE
                btnLogin.visibility = View.GONE
            }
            false -> {
                txvUsername.text = ""
                txvUsername.visibility = View.GONE
                txvLoginAs.visibility = View.GONE
                crvUser.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
            }
        }
    }

    override fun initActivity(){
        crvTransaction.setOnClickListener {
            if (prefsManager.prefsIsLogin)
                startActivity(Intent(this, TransactionActivity::class.java))
            else
                showMessage("Anda belum login")
        }
        crvAgent.setOnClickListener {
            if (prefsManager.prefsIsLogin)
                startActivity(Intent(this, AgentActivity::class.java))
            else
                showMessage("Anda belum login")
        }
        crvUser.setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
        }
        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
