package com.akashgupta.keepnote.models.signup

import com.importantconcept.notesapp.models.signup.User

data class UserResponse(
    val token: String,
    val user: User
)