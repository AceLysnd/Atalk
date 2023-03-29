package com.ace.atalk.data.model

data class User(
    val accountId: String?,
    val username: String?,
    val email: String?,
    val friends: List<String>?,
    val chatRoom: List<ChatRoom?>?
)