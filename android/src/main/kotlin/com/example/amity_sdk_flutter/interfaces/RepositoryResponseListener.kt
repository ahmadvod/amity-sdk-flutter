package com.example.amity_sdk_flutter.interfaces

interface RepositoryResponseListener {
    fun onSuccess(extra: String, msg: String, responseType: ResponseType)
    fun onError(extra: String, error: String?)
}

enum class ResponseType {
    CREATE_COMMUNITY, JOIN_COMMUNITY
}