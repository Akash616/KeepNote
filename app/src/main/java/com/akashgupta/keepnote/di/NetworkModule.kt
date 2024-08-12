package com.akashgupta.keepnote.di

import com.akashgupta.keepnote.api.AuthInterceptor
import com.akashgupta.keepnote.api.NotesAPI
import com.akashgupta.keepnote.api.UserAPI
import com.akashgupta.keepnote.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)//pura application ka andar sirf ak hi object hoga
@Module
class NetworkModule {

    /*@Singleton
    @Provides
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }*/

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder { //return Builder object
        return Retrofit.Builder() //builder pattern use
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    /*fun providesAuthRetrofit(okHttpClient: OkHttpClient) : Retrofit{ //new retrofit object
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient) //client added
            .baseUrl(BASE_URL)
            .build()
    } //But same code
    */

    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder): UserAPI {
        return retrofitBuilder.build().create(UserAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesNotesAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): NotesAPI {
        return retrofitBuilder
            .client(okHttpClient)
            .build().create(NotesAPI::class.java)
    }

}