package com.moim.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey
import com.moim.presentation.R

enum class MainBottomBarRoute(
    @param:DrawableRes val selectedIconId: Int,
    @param:DrawableRes val unselectedIconId: Int,
    @param:StringRes val titleTextId: Int,
    val route: NavKey
) {
    HOME(
        selectedIconId = R.drawable.home_24,
        unselectedIconId = R.drawable.home_24,
        titleTextId = R.string.home,
        route = Home
    ),
    USER(
        selectedIconId = R.drawable.account_circle_24,
        unselectedIconId = R.drawable.account_circle_24,
        titleTextId = R.string.user,
        route = User
    )
}