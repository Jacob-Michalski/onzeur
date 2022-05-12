package fr.uavignon.ceri.onzeur

import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    @GET("extract/{cmd}/{ref}")
    suspend fun getRequest(@Path("cmd") cmd : String, @Path("ref") ref: String): Response<ResponseAPI>

    @GET("play/{artist}/{title}")
    suspend fun getPlay(@Path("artist") artist : String, @Path("title") title: String): Response<String>
}