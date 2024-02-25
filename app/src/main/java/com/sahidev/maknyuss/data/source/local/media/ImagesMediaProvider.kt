package com.sahidev.maknyuss.data.source.local.media

import android.graphics.Bitmap
import android.net.Uri

interface ImagesMediaProvider {
    fun getImage(displayName: String): Image?
    suspend fun getBitmapFromUrl(url: String): Bitmap
    fun saveBitmap(
        bitmap: Bitmap,
        displayName: String,
        format: Bitmap.CompressFormat,
        mimeType: String
    ): Uri
}

data class Image(
    val id: Long,
    val name: String,
    val uri: Uri
)