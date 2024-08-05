package com.importantconcept.notesapp.models.signup

data class UserRequest( //request send
    val email: String,
    val password: String,
    val username: String
)