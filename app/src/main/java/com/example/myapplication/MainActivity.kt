package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val photoAdapter = PhotoAdapter(DataManager.photos)
        photoAdapter.onItemClick = { photo, position ->
            val intent = Intent(this, PhotoView::class.java)
            intent.putExtra(EXTRA_PHOTO_POSITION, position)
            this.startActivity(intent)
        }
        binding.photoDisplay.adapter = photoAdapter
        layoutManager = GridLayoutManager(this, calculateNoOfColumns(this, 100.toFloat()))
        binding.photoDisplay.layoutManager = layoutManager


        binding.addButton.setOnClickListener {
            // start photo taking activity
           val intent = Intent(this, PhotoCapture::class.java)
            this.startActivity(intent)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        layoutManager.spanCount = calculateNoOfColumns(this, 100.toFloat())
        super.onConfigurationChanged(newConfig)
    }

    fun calculateNoOfColumns(
        context: Context,
        columnWidthDp: Float
    ): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }

}