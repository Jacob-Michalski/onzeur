package fr.uavignon.ceri.onzeur

import retrofit2.Response
import retrofit2.http.*

interface TheAudioService {


    @GET("album.php")
    suspend fun getAlbumInfo(@Query("m") albumId : String): Response<Albums>

    @GET("searchtrack.php")
    suspend fun getTrackInfo(@Query("s") artist : String, @Query("t") title: String): Response<Tracks>
}