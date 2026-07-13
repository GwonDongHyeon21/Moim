package com.moim.presentation.screen.component

import androidx.annotation.DrawableRes
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.moim.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoimTopBar(
    value: String,
    @DrawableRes navigationIcon: Int? = null,
    @DrawableRes actionIcon: Int? = null,
    onClickNavigationIcon: () -> Unit = {},
    onClickActionIcon: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(text = value) },
        navigationIcon = {
            navigationIcon?.let {
                IconButton(onClick = onClickNavigationIcon) {
                    Icon(
                        painter = painterResource(it),
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            actionIcon?.let {
                IconButton(onClick = onClickActionIcon) {
                    Icon(
                        painter = painterResource(it),
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    MoimTopBar(
        value = "test",
        onClickNavigationIcon = {},
        onClickActionIcon = {},
        navigationIcon = R.drawable.arrow_back_24,
        actionIcon = R.drawable.more_vert_24
    )
}