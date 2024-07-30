package com.example.projectcompose.ui.theme

import android.content.ContentResolver
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

fun uriToByteArray(contentResolver: ContentResolver, uri: Uri): ByteArray {
    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    val byteBuffer = ByteArrayOutputStream()
    val buffer = ByteArray(1024)
    var len: Int

    while (inputStream?.read(buffer).also { len = it ?: -1 } != -1) {
        byteBuffer.write(buffer, 0, len)
    }

    inputStream?.close()
    return byteBuffer.toByteArray()
}
