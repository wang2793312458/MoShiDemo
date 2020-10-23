package com.example.moshidemo.http

import android.os.Bundle
import android.util.Log
import com.example.moshidemo.BaseActivity
import com.example.moshidemo.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val webResponse = HttpFactory.instance.getPartsAsync()
                Log.d(TAG, "onCreate: " + webResponse.id)
            } catch (e: IOException) {

            }
        }
    }
}