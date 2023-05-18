package com.phamnhantucode.uitestenouvo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phamnhantucode.uitestenouvo.R
import com.phamnhantucode.uitestenouvo.ui.screens.ButtonApprovalMatrixScreen
import com.phamnhantucode.uitestenouvo.ui.theme.mediumTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDialog(
    message: String,
    title: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(Color(0x80000000))

    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.Center),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                Text(
                    text = message,
                    style = mediumTextStyle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 35.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ButtonApprovalMatrixScreen(
                        textColor = Color.White,
                        backgroundColor = MaterialTheme.colors.primaryVariant,
                        title = stringResource(id = R.string.confirm).uppercase()
                    ) {
                        onConfirm.invoke()
                    }
                    ButtonApprovalMatrixScreen(
                        textColor = MaterialTheme.colors.primaryVariant,
                        backgroundColor = Color.White,
                        title = stringResource(id = R.string.cancel).uppercase()
                    ) {
                        onCancel.invoke()
                    }
                }
            }
        }
    }
}