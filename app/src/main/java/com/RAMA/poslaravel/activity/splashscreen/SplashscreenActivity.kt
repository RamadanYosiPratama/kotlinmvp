package com.lazday.poslaravel.activity.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.lazday.poslaravel.R
import com.lazday.poslaravel.activity.agent.AgentActivity
import com.lazday.poslaravel.activity.home.MainActivity
import com.lazday.poslaravel.activity.login.LoginActivity

class SplashscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
//            startActivity(Intent(this, AgentActivity::class.java))
            finish()
        }, 1000)
    }
}
