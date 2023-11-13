package com.apex.sdk.openelevation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apex.sdk.elevation.ElevationAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val elevationAPI = ElevationAPI("https://10.0.2.2:8080/")
        CoroutineScope(Dispatchers.IO).launch {
            elevationAPI.getLocations()
        }
    }
}