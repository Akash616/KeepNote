package com.akashgupta.keepnote.api

import com.akashgupta.keepnote.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager

    //intercept -> api request ko observe karaga.
    //Agar log karni hai, header add karna hai, etc.
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder() //new request object

        //Header Add
        val token = tokenManager.getToken()
        request.addHeader("Authorization", "Bearer $token") //API requirement-> Bearer

        return chain.proceed(request.build())
    }

}