package com.example.myapplication

data class photo(val path: String) {}

object DataManager {
    var photos: ArrayList<photo> = ArrayList()

    init{
        SeedData()
    }

    private fun SeedData() {
        val drawablePath: String = "drawable://"
        photos = arrayListOf(
            photo(drawablePath + R.drawable.one),
            photo(drawablePath + R.drawable.two),
            photo(drawablePath + R.drawable.three),
            photo(drawablePath + R.drawable.four),
            photo(drawablePath + R.drawable.five),
            photo(drawablePath + R.drawable.six),
        )
    }
}

