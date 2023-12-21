package com.febiarifin.movien.core.utils

import com.febiarifin.movien.core.BuildConfig

object Constants {
    private const val BEARER = "Bearer "
    const val AUTHORIZATION = "Authorization"

    fun getBearer(): String {
        return BEARER + BuildConfig.API_KEY
    }
}