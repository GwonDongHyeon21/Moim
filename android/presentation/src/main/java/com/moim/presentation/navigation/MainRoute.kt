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
    LOGIN(
        selectedIconId = R.drawable.ic_launcher_foreground,
        unselectedIconId = R.drawable.ic_launcher_foreground,
        titleTextId = R.string.app_name,
        route = Login
    ),
    HOME(
        selectedIconId = R.drawable.ic_launcher_foreground,
        unselectedIconId = R.drawable.ic_launcher_foreground,
        titleTextId = R.string.app_name,
        route = Home
    )
}