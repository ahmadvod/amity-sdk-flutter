package com.example.amity_sdk_flutter.feature

import com.amity.socialcloud.sdk.social.AmitySocialClient
import com.amity.socialcloud.sdk.social.feed.AmityUserFeedSortOption
import com.example.amity_sdk_flutter.helper.LogUtil
import com.example.amity_sdk_flutter.interfaces.RepositoryResponseListener
import com.example.amity_sdk_flutter.interfaces.ResponseType
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.NotNull

class AmityPost(
    @NotNull val repositoryResponseListener: RepositoryResponseListener
) {
    private val feedRepository = AmitySocialClient.newFeedRepository()

    fun createTextPost(
        postText: String,
        postType: String,
        id: String?
    ) {
        val feedRep = feedRepository.createPost()
        var type: PostType? = null
        try {
            type = PostType.valueOf(postType)
        } catch (e: Exception) {
            LogUtil.log("Casting issue")
        }

        type?.let {
            when(type) {
                PostType.TARGET_SELF -> {
                    feedRep.targetMe()
                        .text(postText)
                        .build()
                        .post()
                        .doOnSuccess {
                            repositoryResponseListener.onSuccess("", Gson().toJson(it), ResponseType.TEXT_POST)
                        }
                        .doOnError {
                            repositoryResponseListener.onError("", it.message)
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                }

                PostType.TARGET_USER -> {
                    feedRep.targetUser(id!!)
                        .text(postText)
                        .build()
                        .post()
                        .doOnSuccess {
                            repositoryResponseListener.onSuccess("", Gson().toJson(it), ResponseType.TEXT_POST)
                        }
                        .doOnError {
                            repositoryResponseListener.onError("", it.message)
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                }

                PostType.TARGET_COMMUNITY -> {
                    feedRep.targetCommunity(id!!)
                        .text(postText)
                        .build()
                        .post()
                        .doOnSuccess {
                            repositoryResponseListener.onSuccess("", Gson().toJson(it), ResponseType.TEXT_POST)
                        }
                        .doOnError {
                            repositoryResponseListener.onError("", it.message)
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                }
            }
        }
    }

    fun createImageWithTextPost(
        postText: String,
        postType: String,
        id: String?,
        imagesUrl: String
    ) {
        val feedRep = feedRepository.createPost()
        var type: PostType? = null
        try {
            type = PostType.valueOf(postType)
        } catch (e: Exception) {
            LogUtil.log("Casting issue")
        }

        type?.let {
            when(type) {
                PostType.TARGET_SELF -> {
                    feedRep.targetMe()
//                        .image()
                        .text(postText)
                        .build()
                        .post()
                        .doOnSuccess {

                        }
                        .doOnError {

                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                }

                PostType.TARGET_USER -> {
                    feedRep.targetUser(id!!)
                        .text(postText)
                        .build()
                        .post()
                        .doOnSuccess {

                        }
                        .doOnError {

                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                }

                PostType.TARGET_COMMUNITY -> {
                    feedRep.targetCommunity(id!!)
                        .text(postText)
                        .build()
                        .post()
                        .doOnSuccess {

                        }
                        .doOnError {

                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                }
            }
        }
    }

    fun getPeerFeeds(
        userId: String
    ){
        feedRepository
            .getUserFeed(userId)
            .includeDeleted(false)
            .sortBy(AmityUserFeedSortOption.LAST_CREATED)
            .build()
            .query()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                repositoryResponseListener.onSuccess("", Gson().toJson(it), ResponseType.GET_PEER_FEEDS)
            }.doOnError {
                repositoryResponseListener.onError("", it.message)
            }.subscribe()
    }

    companion object {
        const val POST_TEXT_KEY = "postText"
        const val ID_KEY = "id"
        const val POST_TYPE_KEY = "postType"
    }
}

enum class PostType {
    TARGET_SELF, TARGET_USER, TARGET_COMMUNITY
}