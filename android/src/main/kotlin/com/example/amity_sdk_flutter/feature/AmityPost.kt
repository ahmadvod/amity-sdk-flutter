package com.example.amity_sdk_flutter.feature

import com.amity.socialcloud.sdk.social.AmitySocialClient
import com.example.amity_sdk_flutter.helper.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AmityPost {
    private val postRepository = AmitySocialClient.newFeedRepository()

    fun createTextPost(
        postText: String,
        postType: String,
        id: String?
    ) {
        val feedRep = postRepository.createPost()
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

    fun createImageWithTextPost(
        postText: String,
        postType: String,
        id: String?,
        imagesUrl: String
    ) {
        val feedRep = postRepository.createPost()
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

    companion object {
        const val POST_TEXT_KEY = "postText"
        const val ID_KEY = "id"
        const val POST_TYPE_KEY = "postType"
    }
}

enum class PostType {
    TARGET_SELF, TARGET_USER, TARGET_COMMUNITY
}