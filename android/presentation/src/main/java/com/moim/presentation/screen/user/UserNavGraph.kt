package com.moim.presentation.screen.user

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.moim.presentation.navigation.User

fun EntryProviderScope<NavKey>.user(
    metadata: Map<String, Any>,
    modifier: Modifier = Modifier
) {
    entry<User>(metadata = metadata) {
        UserScreen(modifier)
    }
}