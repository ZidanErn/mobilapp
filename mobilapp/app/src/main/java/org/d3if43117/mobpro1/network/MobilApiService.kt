package org.d3if43117.mobpro1.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if43117.mobpro1.model.Mobil
import org.d3if43117.mobpro1.model.OpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://mobilapizidan.000webhostapp.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface MobilApiService {
    @GET("Mobil.php")
    suspend fun getMobil(
        @Header("Authorization") userId: String
    ): List<Mobil>

    @Multipart
    @POST("Mobil.php")
    suspend fun postMobil(
        @Header("Authorization") userId: String,
        @Part("id") id: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("namaLatin") namaLatin: RequestBody,
        @Part("isUserInputted") isUserInputted: Int,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("Mobil.php")
    suspend fun deleteMobil(
        @Header("Authorization") userId: String,
        @Query("id") id: String
    ): OpStatus
}

object MobilApi {
    val service: MobilApiService by lazy {
        retrofit.create(MobilApiService::class.java)
    }

    fun getMobilUrl(imageId: String): String {
        return "${BASE_URL}image.php?id=$imageId"
    }

    enum class ApiStatus{LOADING, SUCCESS, FAILED}
}