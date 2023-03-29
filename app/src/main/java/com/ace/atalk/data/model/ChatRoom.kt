package com.ace.atalk.data.model

data class ChatRoom(
    val oppositeId: String?,
    val oppositeUsername: String,
    val chat: List<Chat?>?
)
