package fr.uavignon.ceri.onzeur

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TheAudioDbApi {
    const val TOKEN : String = "523532"
    const val BASE_URL: String = "https://www.theaudiodb.com/api/v1/json/$TOKEN/"

    private val gson : Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private val httpClient : OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService : TheAudioService by lazy{
        retrofit.create(TheAudioService::class.java)
    }
}