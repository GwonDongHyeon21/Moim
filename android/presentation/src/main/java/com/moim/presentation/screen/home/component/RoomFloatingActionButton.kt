package com.moim.presentation.screen.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.moim.presentation.R
import com.moim.presentation.screen.home.component.RoomFloatingActionButton.ANIMATE_TARGET_VALUE
import com.moim.presentation.screen.home.component.RoomFloatingActionButton.ROTATION_LABEL
import com.moim.presentation.screen.home.model.RoomOptions
import com.moim.presentation.theme.MoimSpace

private object RoomFloatingActionButton {
    const val ANIMATE_TARGET_VALUE = 60f
    const val ROTATION_LABEL = "fab_rotation"
}

@Composable
fun RoomFloatingActionButton(onClickOption: (RoomOptions) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) ANIMATE_TARGET_VALUE else 0f,
        label = ROTATION_LABEL
    )

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(MoimSpace.SpaceMedium),
    ) {
        AnimatedVisibility(visible = isExpanded) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(MoimSpace.SpaceMedium)
            ) {
                SmallFloatingActionButton(onClick = { onClickOption(RoomOptions.CREATE) }) {
                    Text(text = stringResource(R.string.add))
                }

                SmallFloatingActionButton(onClick = { onClickOption(RoomOptions.JOIN) }) {
                    Text(text = stringResource(R.string.join))
                }
            }
        }

        FloatingActionButton(onClick = { isExpanded = !isExpanded }) {
            Icon(
                painter = painterResource(R.drawable.add_24),
                contentDescription = stringResource(R.string.expand_menu_description),
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoomFloatingActionButtonPreview() {
    RoomFloatingActionButton(onClickOption = {})
}