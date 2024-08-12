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
import java.util.concurrent.TimeUnit
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
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            // Set timeouts to avoid SocketTimeoutException
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
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

    /*The value 30 in this context represents the duration of the timeout in seconds. Here’s what each timeout setting means:

    connectTimeout(30, TimeUnit.SECONDS): Specifies that the client will wait for up to 30 seconds while trying to establish
    a connection to the server. If the connection is not established within 30 seconds, a SocketTimeoutException will be thrown.

    readTimeout(30, TimeUnit.SECONDS): Specifies that the client will wait for up to 30 seconds for the server to send a
    response after the connection has been established. If no data is received within 30 seconds, a SocketTimeoutException
    will be thrown.

    writeTimeout(30, TimeUnit.SECONDS): Specifies that the client will wait for up to 30 seconds for the client to send all
    the data to the server. If the client cannot send the data within 30 seconds, a SocketTimeoutException will be thrown.

    In summary, 30 is the number of seconds that the client will wait before timing out for each respective operation.
    You can adjust these values according to your application's needs.*/

}