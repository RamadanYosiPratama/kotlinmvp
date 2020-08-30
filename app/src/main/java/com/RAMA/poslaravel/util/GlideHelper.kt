package com.lazday.poslaravel.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.lazday.poslaravel.R
import com.lazday.poslaravel.data.Constant

class GlideHelper {
    companion object {

        fun setImage(context: Context, urlImage: String, imageView: ImageView){
            Glide .with(context)
                .load(urlImage)
                .centerCrop()
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image)
                .into(imageView)
        }
    }
}