package com.moim.presentation.screen.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.moim.presentation.R
import com.moim.presentation.screen.component.MoimButton
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimSpace
import com.moim.presentation.theme.MoimTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoimBasicDialog(
    value: String,
    onConfirmValue: String,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onDismissRequestValue: String = stringResource(R.string.cancel),
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        content = {
            Column(
                modifier = Modifier
                    .background(
                        color = MoimTheme.colors.background,
                        shape = MoimTheme.shapes.roundedSmall
                    )
                    .padding(MoimPadding.PaddingSmall),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = value)

                Spacer(modifier = Modifier.height(MoimSpace.SpaceXSmall))
                Row {
                    MoimButton(
                        value = onDismissRequestValue,
                        onClick = onDismissRequest,
                        modifier = Modifier.weight(1f)
                    )
                    MoimButton(
                        value = onConfirmValue,
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MoimBasicDialogPreview() {
    MoimBasicDialog(
        value = "testtest",
        onConfirmValue = "ok",
        onConfirm = {},
        onDismissRequest = {}
    )
}