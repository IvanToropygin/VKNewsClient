package com.sumin.vknewsclient.data.models

import com.google.gson.annotations.SerializedName

data class LikesCountDto(
    @SerializedName("likes") val count: Int,
)
