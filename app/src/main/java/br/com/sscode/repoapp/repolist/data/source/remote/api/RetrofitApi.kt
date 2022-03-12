package br.com.sscode.repoapp.repolist.data.source.remote.api

import br.com.sscode.repoapp.repolist.data.source.remote.service.RepoPageService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.github.com/search/"

class RetrofitApi {

    private val instance: Retrofit by lazy {
        buildRetrofit()
    }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(buildConverterFactory())
            .client(buildHttpClient())
            .build()
    }

    private fun buildConverterFactory(): GsonConverterFactory {
        val gsonConfig = GsonBuilder().setLenient().create()
        return GsonConverterFactory.create(gsonConfig)
    }

    private fun buildHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    fun getRepoPageService(): RepoPageService = instance.create(RepoPageService::class.java)
}