package com.example.amity_sdk_flutter.feature

import android.net.Uri
import com.amity.socialcloud.sdk.AmityCoreClient
import com.amity.socialcloud.sdk.core.file.AmityImage
import com.amity.socialcloud.sdk.core.file.AmityUploadResult
import com.amity.socialcloud.sdk.core.reaction.AmityReaction
import com.amity.socialcloud.sdk.social.AmitySocialClient
import com.amity.socialcloud.sdk.social.feed.AmityPost
import com.amity.socialcloud.sdk.social.feed.AmityUserFeedSortOption
import com.example.amity_sdk_flutter.helper.LogUtil
import com.example.amity_sdk_flutter.interfaces.RepositoryResponseListener
import com.example.amity_sdk_flutter.interfaces.ResponseType
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.NotNull

class AmityPost(
    @NotNull val repositoryResponseListener: RepositoryResponseListener
) {
    private val feedRepository = AmitySocialClient.newFeedRepository()
    private val postRepository = AmitySocialClient.newPostRepository()
    private val fileUploadRepository = AmityCoreClient.newFileRepository()

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
        fileUploadRepository.uploadImage(Uri.parse(imagesUrl))
            .isFullImage(true)
            .build()
            .transfer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                when(it) {
                    is AmityUploadResult.COMPLETE -> {
                        val aImg = it.getFile()
                        uploadImagePost(postText, postType, id, aImg)
                    }
                    is AmityUploadResult.PROGRESS -> {

                    }
                    is AmityUploadResult.ERROR, AmityUploadResult.CANCELLED -> {

                    }
                }
            }
            .doOnError {

            }
            .subscribe()


    }

    fun uploadImagePost(
        postText: String,
        postType: String,
        id: String?,
        img: AmityImage
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
                        .image(img)
                        .text(postText)
                        .build()
                        .post()
                        .doOnSuccess {
                            repositoryResponseListener.onSuccess("", Gson().toJson(it), ResponseType.IMAGE_TEXT_POST)
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
                        .image(img)
                        .text(postText)
                        .build()
                        .post()
                        .doOnSuccess {
                            repositoryResponseListener.onSuccess("", Gson().toJson(it), ResponseType.IMAGE_TEXT_POST)
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
                        .image(img)
                        .text(postText)
                        .build()
                        .post()
                        .doOnSuccess {
                            repositoryResponseListener.onSuccess("", Gson().toJson(it), ResponseType.IMAGE_TEXT_POST)
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

    fun getUserPosts(
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

    fun addReactionOnPost(
        amityPost: AmityPost
    ) {
        amityPost.react()
            .addReaction("like")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    companion object {
        const val POST_TEXT_KEY = "postText"
        const val ID_KEY = "id"
        const val POST_TYPE_KEY = "postType"
        const val IMAGE_URL_KEY = "imageUrl"
    }
}

enum class PostType {
    TARGET_SELF, TARGET_USER, TARGET_COMMUNITY
}