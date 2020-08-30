package com.lazday.poslaravel.activity.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.lazday.poslaravel.R
import com.lazday.poslaravel.data.database.PrefsManager
import com.lazday.poslaravel.data.model.login.ResponseLogin
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    lateinit var presenter: LoginPresenter
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.elevation = 0f
        presenter = LoginPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun initActivity(){
        val username = edtUsername.text
        val password = edtPassword.text

        btnLogin.setOnClickListener {
            if (username.isNullOrEmpty()) {
                edtUsername.error = "Tidak boleh kosong"
                edtUsername.requestFocus()
            } else if (password.isNullOrEmpty()) {
                edtPassword.error = "Tidak boleh kosong"
                edtPassword.requestFocus()
            } else {
                presenter.doLogin(
                    username.toString(), password.toString()
                )
            }
        }
    }

    override fun onResult(responseLogin: ResponseLogin) {
        presenter.setPrefs(prefsManager, responseLogin.pegawai!!)
        finish()
    }

    override fun onLoading(loading: Boolean) {
        when(loading) {
            true -> {
                progress.visibility = View.VISIBLE
                btnLogin.visibility = View.GONE
            }

            false -> {
                progress.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
            }
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
