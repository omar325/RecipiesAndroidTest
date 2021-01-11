package com.example.recipiestest.infrastructure

import com.example.recipiestest.infrastructure.models.ApiResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesApi {
    suspend fun queryRecipes(query: String): ApiResponse

    interface Default: RecipesApi {

        @GET("search.php")
        override suspend fun queryRecipes(@Query("s") query: String): ApiResponse

        companion object {
            private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

            private val logger by lazy {
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            }

            fun create(): Default =
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(createClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Default::class.java)

            private fun createClient() : OkHttpClient =
                with(OkHttpClient.Builder()) {
                    addInterceptor(logger)
                    build()
                }
        }
    }
}