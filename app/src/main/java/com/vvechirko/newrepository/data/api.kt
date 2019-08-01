package com.vvechirko.newrepository.data

import com.vvechirko.newrepository.vm.Paginated
import com.vvechirko.newrepository.vm.Wrapped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://reqres.in/api/"

interface Api {

    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("delay") delay: Int = 3
    ): Paginated<UserEntity>

    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") id: Int,
        @Query("delay") delay: Int = 3
    ): Wrapped<UserEntity>
}

private fun client(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
}

fun retrofit() = Retrofit.Builder()
    .client(client())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

inline fun <reified T : Any> provideService(): T = retrofit().create(T::class.java)