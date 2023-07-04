package com.mostafa.quran.di
import com.mostafa.quran.data.remote.ApiServices
import com.mostafa.quran.data.remote.response.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    private const val BASE_URL_1 = "https://api.aladhan.com/v1/"
    private const val BASE_URL_2 = "https://api.quran.com/api/v4/"




    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Singleton
    @Provides
    @Named("BaseUrl1")
    fun provideRetrofit1(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_1)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(httpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named("BaseUrl2")
    fun provideRetrofit2(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_2)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(httpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named("BaseUrl1")
    fun provideApi1(@Named("BaseUrl1") retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    @Singleton
    @Provides
    @Named("BaseUrl2")
    fun provideApi2(@Named("BaseUrl2") retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

}


