package com.apex.sdk.elevation

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST


//    curl -X POST \
//    https://0.0.0.0/8080/api/v1/lookup \
//    -H 'Accept: application/json' \
//    -H 'Content-Type: application/json' \
//    -d '{
//{
//    "locations":[
//        {
//            "latitude": 10,
//            "longitude": 10
//        },
//        {
//            "latitude":20,
//            "longitude": 20
//        },
//        {
//            "latitude":41.161758,
//            "longitude":-8.583933
//        }
//    ]
//}

interface Api {
    @Headers("Content-Type: application/json")
    @POST("api/v1/lookup")
    fun elevations(@Body elevationRequest: ElevationsRequest): Call<ElevationResponse>
}

class ElevationAPI(val base_url: String) {

    private val retrofit = Retrofit.Builder()
        .baseUrl(base_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun init(context: Context) {
        (context as LifecycleOwner).lifecycle.addObserver(
            object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    TODO("Not yet implemented")
                }
            }
        )
    }

    suspend fun getLocations(elevationRequest : ElevationsRequest): Flow<ElevationResponse> {
        val api = retrofit.create(Api::class.java)
        api.elevations(elevationRequest).also { call ->
            val response = call.execute()
            if(response.isSuccessful) {
                return flow {
                    emit(response.body()!!)
                }
            }else
            {
                throw Exception(response.errorBody()!!.string())
            }
        }
    }
}

private fun ElevationsRequest.toJsonObject(): JsonObject {
    Gson().toJson(this).let {
        return Gson().fromJson(it, JsonObject::class.java)
    }
}
