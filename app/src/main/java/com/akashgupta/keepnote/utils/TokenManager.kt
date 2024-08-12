package com.akashgupta.keepnote.utils

import android.content.Context
import com.akashgupta.keepnote.utils.Constants.PREFS_TOKEN_FILE
import com.akashgupta.keepnote.utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

//Shared Pref. ko access karna ka leya huma context ka object chahiya hoga
//Annotation for accessing the context - use Hilt easily available
//@ApplicationContext , @ActivityContext , etc.
//we use here qualifier
class TokenManager @Inject constructor(@ApplicationContext context: Context) { //Application context use

    private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE) //Shared pref. object

    fun saveToken(token: String){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? { //String? - nullable type -> bec. we return default value null.
        return prefs.getString(USER_TOKEN, null)
    }

}