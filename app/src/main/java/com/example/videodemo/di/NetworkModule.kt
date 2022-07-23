package com.example.videodemo.di

import androidx.viewbinding.BuildConfig
import com.example.videodemo.data.IVideoService
import com.example.videodemo.util.Constants
import com.example.videodemo.util.Constants.Companion.AUTHORIZATION_KEY
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofitInstance(httpClient: OkHttpClient,gson: Gson) : Retrofit{
        return  Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun providesGsonInstance() : Gson{
        return GsonBuilder().serializeNulls().setLenient().create()
    }

    @Provides
    @Singleton
    fun providesHttpClient(interceptor: Interceptor) : OkHttpClient{
        val httpBuilder = OkHttpClient.Builder().addInterceptor(interceptor)
        return httpBuilder.build()
    }

    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor{
        return Interceptor{
            val originalRequest = it.request()
            val requestBuilder = originalRequest.newBuilder()
                                .addHeader(AUTHORIZATION_KEY,com.example.videodemo.BuildConfig.API_KEY)
            val request = requestBuilder.build()
            it.proceed(request)
        }

    }

    @Provides
    @Singleton
    fun getVideoService(retrofit: Retrofit) : IVideoService{
        return retrofit.create(IVideoService :: class.java)
    }
}