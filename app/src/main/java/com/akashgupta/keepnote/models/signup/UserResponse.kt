package com.importantconcept.notesapp.models.signup

data class UserResponse(
    val token: String,
    val user: User
)