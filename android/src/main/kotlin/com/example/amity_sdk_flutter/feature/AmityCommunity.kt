package com.example.amity_sdk_flutter.feature

import com.amity.socialcloud.sdk.social.AmitySocialClient
import com.example.amity_sdk_flutter.helper.LogUtil
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AmityCommunity {
    private val communityRepository = AmitySocialClient.newCommunityRepository()

    fun createCommunity (
        communityName: String,
        isPublic: Boolean,
        description: String,
        categoryIds: List<String>?,
        userIds: List<String>?
    ) {
        val comBuilder = communityRepository
            .createCommunity(communityName)
            .isPublic(isPublic)
            .description(description)

        categoryIds?.let {
            comBuilder.categoryIds(categoryIds)
        }

        userIds?.let {
            comBuilder.userIds(userIds)
        }

        comBuilder
            .build()
            .create()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                LogUtil.log(Gson().toJson(it))
            }
            .doOnError {
                LogUtil.log(Gson().toJson(it.message))
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