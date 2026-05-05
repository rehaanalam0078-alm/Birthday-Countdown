package com.example.waittillbday  // keep your own package name

import android.os.Bundle
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Button
import android.widget.Toast
import android.content.ContentValues
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.OutputStream
import java.io.File
import java.io.FileOutputStream




class FullScreenImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)

        val imageView = findViewById<ImageView>(R.id.fullImage)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val imageResId = intent.getIntExtra("image", 0)
        imageView.setImageResource(imageResId)

        btnSave.setOnClickListener {
            saveImage(imageResId)
        }
    }

    private fun saveImage(imageResId: Int) {
        val bitmap = BitmapFactory.decodeResource(resources, imageResId)

        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        val fos: OutputStream?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/MyApp")
            }
            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = uri?.let { resolver.openOutputStream(it) }
        } else {
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), filename)
            fos = FileOutputStream(file)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }

        Toast.makeText(this, "Saved ✅", Toast.LENGTH_SHORT).show()
    }
}