package com.lazday.poslaravel.activity.agent.insert

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.lazday.poslaravel.R
import com.lazday.poslaravel.activity.agent.AgentMapsActivity
import com.lazday.poslaravel.data.Constant
import com.lazday.poslaravel.data.model.agent.ResponseAgentUpdate
import com.lazday.poslaravel.util.FileUtils
import com.lazday.poslaravel.util.GalleryHelper
import com.lazday.poslaravel.util.MapsHelper
import kotlinx.android.synthetic.main.activity_agent_insert.*

class AgentInsertActivity : AppCompatActivity(), AgentInsertContract.View {

    private var uriImage: Uri? = null
    private val pickImage = 1

    lateinit var presenter: AgentInsertPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent_insert)
        supportActionBar!!.title = ""
        supportActionBar!!.elevation = 0f
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        init()

        MapsHelper.permissionMap(this, this)
        presenter = AgentInsertPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        if (Constant.LATITUDE.isNotEmpty()) {
            edtLocation?.setText( "${Constant.LATITUDE}, ${Constant.LONGITUDE}" )
        }
    }

    private fun init() {

        edtLocation.setOnClickListener {
            startActivity(Intent(this, AgentMapsActivity::class.java))
        }

        imvImage.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }

        btnSubmit.setOnClickListener {

            val nameStore   = edtNameStore.text
            val nameOwner   = edtNameOwner.text
            val address     = edtAddress.text
            val location    = edtLocation.text

            if ( nameStore.isNullOrEmpty() || nameOwner.isNullOrEmpty() || address.isNullOrEmpty() ||
                location.isNullOrEmpty() || uriImage == null ) {
                showMessage( "Lengkapi data dengan benar" )
            } else {
                presenter.insertAgent(
                    nameStore.toString(), nameOwner.toString(), address.toString(), Constant.LATITUDE,
                    Constant.LONGITUDE, FileUtils.getFile(this, uriImage)
                )
            }

        }
    }

    override fun onLoading(loading: Boolean) {
        when ( loading ) {
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

    override fun onResult(response: ResponseAgentUpdate) {
        showMessage( response.msg )
        this.finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            imvImage.setImageURI(uriImage)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.LATITUDE = ""
        Constant.LONGITUDE = ""
    }
}
