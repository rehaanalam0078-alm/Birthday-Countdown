package com.example.waittillbday

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MemoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memories)

        val imageIds = listOf(
            R.id.p1, R.id.p2, R.id.p3, R.id.p4,
            R.id.p5, R.id.p6, R.id.p7, R.id.p8
        )

        val drawableIds = listOf(
            R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4,
            R.drawable.p5, R.drawable.p6, R.drawable.p7, R.drawable.p8
        )

        for (i in imageIds.indices) {
            val imageView = findViewById<ImageView>(imageIds[i])

            imageView.setOnClickListener {
                openFullScreen(drawableIds[i])
            }
        }

        val button = findViewById<Button>(R.id.openConfession)

        button.setOnClickListener {
            startActivity(Intent(this, ConfessionActivity::class.java))
        }
    }

    // ✅ MOVE FUNCTION OUTSIDE onCreate
    private fun openFullScreen(imageResId: Int) {
        val intent = Intent(this, FullScreenImageActivity::class.java)
        intent.putExtra("image", imageResId)
        startActivity(intent)
    }
}