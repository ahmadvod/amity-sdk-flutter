package com.example.amity_sdk_flutter.feature

import com.amity.socialcloud.sdk.AmityCoreClient
import com.amity.socialcloud.sdk.core.user.AmityFollowStatusFilter
import com.example.amity_sdk_flutter.interfaces.RepositoryResponseListener
import com.example.amity_sdk_flutter.interfaces.ResponseType
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.NotNull

class AmityConnections(
    @NotNull val repositoryResponseListener: RepositoryResponseListener
) {
    private val userRepository = AmityCoreClient.newUserRepository()

    fun followUser(
        userId: String
    ){
        userRepository
            .relationship()
            .user(userId)
            .follow()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                repositoryResponseListener.onSuccess("", Gson().toJson(it), ResponseType.FRIEND_REQUEST_SENT)
            }
            .doOnError {
                repositoryResponseListener.onError("", it.message)
            }
            .subscribe()
    }

    fun getFriendRequestList() {
        userRepository
            .relationship()
            .me()
            .getFollowers()
            .status(AmityFollowStatusFilter.ACCEPTED)
            .build()
            .query()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                repositoryResponseListener.onSuccess("", Gson().toJson(it), ResponseType.FRIEND_REQUEST_LIST)
            }
            .doOnError {
                repositoryResponseListener.onError("", it.message)
            }
            .subscribe()
    }

    fun acceptUserFriendRequest(
        userId: String
    ){
        userRepository
            .relationship()
            .me()
            .accept(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                repositoryResponseListener.onSuccess("", "accepted", ResponseType.FRIEND_REQUEST_ACCEPTED)
            }
            .doOnError {
                repositoryResponseListener.onError("", it.message)
            }
            .subscribe()
    }

    fun unfriendOrDeclineRequest(
        userId: String
    ){
        userRepository
            .relationship()
            .me()
            .decline(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                repositoryResponseListener.onSuccess("", "rejected", ResponseType.FRIEND_REQUEST_DECLINE)
            }
            .doOnError {
                repositoryResponseListener.onError("", it.message)
            }
            .subscribe()
    }

    companion object {
        const val ID_KEY = "id"
    }
}