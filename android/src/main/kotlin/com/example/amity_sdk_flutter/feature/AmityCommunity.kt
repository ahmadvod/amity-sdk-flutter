package com.example.amity_sdk_flutter.feature

import com.amity.socialcloud.sdk.core.error.AmityError
import com.amity.socialcloud.sdk.social.AmitySocialClient
import com.example.amity_sdk_flutter.helper.LogUtil
import com.example.amity_sdk_flutter.interfaces.RepositoryResponseListener
import com.example.amity_sdk_flutter.interfaces.ResponseType
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.NotNull

class AmityCommunity(
    @NotNull val repositoryResponseListener: RepositoryResponseListener
) {
    private val communityRepository = AmitySocialClient.newCommunityRepository()

    fun createCommunity (
        communityName: String,
        isPublic: Boolean,
        description: String,
        categoryIds: String?,
        userIds: String?
    ) {
        LogUtil.log("in -> createCommunit")
        val comBuilder = communityRepository
            .createCommunity(communityName)
            .isPublic(isPublic)
            .description(description)

        categoryIds?.let {
            comBuilder.categoryIds(categoryIds.split(","))
        }

        userIds?.let {
            comBuilder.userIds(userIds.split(","))
        }

        comBuilder
            .build()
            .create()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                repositoryResponseListener.onSuccess("", Gson().toJson(it), ResponseType.CREATE_COMMUNITY)
            }
            .doOnError {
                repositoryResponseListener.onError("", it.message)
            }
            .subscribe()
    }

    fun joinCommunity(
        communityName: String
    ) {
        val comBuilder = communityRepository
            .joinCommunity(communityName)

        comBuilder
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                repositoryResponseListener.onSuccess("", "Community joined successfully", ResponseType.JOIN_COMMUNITY)
            }
            .doOnError {
                when {
                    AmityError.from(it) == AmityError.ITEM_NOT_FOUND -> {
                        repositoryResponseListener.onError("", it.message)
                    }
                    AmityError.from(it) == AmityError.USER_IS_BANNED -> {
                        repositoryResponseListener.onError("", it.message)
                    }
                }
            }
            .subscribe()
    }

    companion object {
        const val COMMUNITY_NAME_KEY = "communityName"
        const val IS_PUBLIC_KEY = "isPublic"
        const val DESCRIPTION_KEY = "description"
        const val CATEGORIES_IDS_KEY = "categoryIds"
        const val USER_IDS_KEY = "userIds"
    }
}