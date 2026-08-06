package com.moim.presentation.screen.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import com.moim.presentation.navigation.MainBottomBarRoute

@Composable
fun MoimBottomBar(
    currentDestination: NavKey?,
    onNavigateToDestination: (MainBottomBarRoute) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        MainBottomBarRoute.entries.forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination.route,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        painter = painterResource(destination.selectedIconId),
                        contentDescription = stringResource(destination.titleTextId)
                    )
                },
                label = { Text(text = stringResource(destination.titleTextId)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoimBottomBarPreview() {
    MoimBottomBar(
        currentDestination = MainBottomBarRoute.HOME.route,
        onNavigateToDestination = {}
    )
}