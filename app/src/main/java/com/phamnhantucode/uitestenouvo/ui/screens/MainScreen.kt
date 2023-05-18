package com.phamnhantucode.uitestenouvo.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import com.phamnhantucode.uitestenouvo.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.phamnhantucode.uitestenouvo.*
import com.phamnhantucode.uitestenouvo.domain.model.ApprovalView
import com.phamnhantucode.uitestenouvo.domain.model.Approver
import com.phamnhantucode.uitestenouvo.domain.viewmodel.MainScreenViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBarMainScreen()
        }, content = {
            Surface(
                color = MaterialTheme.colors.primary
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(Color.White)
                        .padding(horizontal = 25.dp, vertical = 15.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        NewMatrixBar() {
                            navController.navigate(Screens.ApprovalMatrixScreen.toAddNewMatrix())
                        }
                        Divider()
                        ListFilterBar(filter = viewModel.feature.map { it.feature })
                        ListMatrixBar(navController = navController)
                        Divider()
                    }

                }
            }
        })
}


@Composable
fun TopAppBarMainScreen() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Normal
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        elevation = 10.dp
    )
}

@Composable
fun NewMatrixBar(
    onNewMatrix: () -> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            onClick = {
                onNewMatrix()
            },
            modifier = Modifier.fillMaxWidth(0.5f),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
            contentPadding = PaddingValues(vertical = 2.dp, horizontal = 4.dp),

            ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add icon",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                tint = Color.Blue
            )
            Text(
                text = stringResource(id = R.string.tambah_new_matrix),
                style = TextStyle(
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Composable
fun ListFilterBar(
    filter: List<String>,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val listSelect = remember {
            mutableStateOf(mutableListOf<String>())
        }
        filter.forEach {feature ->
            val isClick = remember {
                mutableStateOf(false)
            }
            FilterBar(
                onClick = {
                    if (!listSelect.value.contains(feature)) {
                        listSelect.value.add(feature)
                    } else {
                        listSelect.value.remove(feature)
                    }
                    isClick.value = !isClick.value
                    viewModel.onEvent(MainScreenViewModel.Event.FilterBy(listSelect.value))
                },
                title = feature,
                isClicked = isClick.value
            )
        }
    }
}

@Composable
fun ListMatrixBar(
    viewModel: MainScreenViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val approvalView = viewModel.approvalViewsFilter
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(approvalView.value) {approvalView ->
            CardApprovalMatrix(
                approvalView,
                onClick = {
                    navController.navigate(Screens.ApprovalMatrixScreen.toEditMatrix(it))
                }

            )
        }
    }
}

@Composable
fun FilterBar(
    onClick: () -> Unit,
    isClicked: Boolean = false,
    title: String,
) {
    val colorBorder = if (isClicked) MaterialTheme.colors.primary else Color.LightGray
    val colorText = if (isClicked) MaterialTheme.colors.primary else Color.Black
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(
                1.dp,
                color = colorBorder,
                shape = RoundedCornerShape(15.dp)
            )
            .clickable {
                onClick.invoke()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .weight(1f),
                style = TextStyle(
                    color = colorText,
                    fontSize = 15.sp
                )
            )
            Spacer(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(colorBorder)
            )
            Text(
                text = title,
                modifier = Modifier
                    .weight(1f),
                style = TextStyle(
                    color = colorText,
                    fontSize = 15.sp
                )
            )
            if (isClicked) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "ArrowDropDown",
                    tint = colorText
                )
            } else {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "PlayArrow",
                    tint = colorText
                )
            }

        }
    }
}

@Composable
fun CardApprovalMatrix(
    approvalView: ApprovalView,
    onClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                Color.LightGray,
                shape = RoundedCornerShape(15.dp)
            )
            .clickable { approvalView.id?.let { onClick(it) } }
            .padding(vertical = 10.dp, horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            //Range Limit of Approval
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.range_of_limite_approval),
                    style = TextStyle(
                        fontSize = 10.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    //Minimum
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(id = R.string.minimum),
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colors.primaryVariant
                                )
                            )
                            Text(
                                text = stringResource(id = R.string.currency),
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colors.primaryVariant
                                )
                            )
                        }
                        Text(
                            text = Utils.numberCovert(approvalView.minimum),
                            style = TextStyle(
                                fontSize = 10.sp,
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colors.primaryVariant,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    //Maximum
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(id = R.string.maximum),
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colors.primaryVariant
                                )
                            )
                            Text(
                                text = stringResource(id = R.string.currency),
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colors.primaryVariant
                                )
                            )
                        }
                        Text(
                            text = Utils.numberCovert(approvalView.maximum),
                            style = TextStyle(
                                fontSize = 10.sp,
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colors.primaryVariant,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }


            }
            Divider()
            //Number of Approval
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()

            ) {
                Text(
                    text = stringResource(id = R.string.numOfApproval),
                    modifier = Modifier.weight(1f),
                    style = TextStyle(
                        fontSize = 10.sp,
                        color = Color.Black
                    )

                )
                Text(
                    text = approvalView.num_of_approver.toString(), modifier = Modifier.weight(1f),
                    style = TextStyle(
                        color = MaterialTheme.colors.primaryVariant,
                        textAlign = TextAlign.End,
                        fontSize = 10.sp
                    )
                )
            }
            Divider()
            for (i in 0 until approvalView.approvers.size) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Text(
                        text = stringResource(id = R.string.approver) + " " + (i + 1).toString(),
                        modifier = Modifier.weight(1f),
                        style = TextStyle(
                            fontSize = 10.sp,
                            color = Color.Black
                        )
                    )
                    Text(
                        text = approvalView.approvers[i].approver,
                        modifier = Modifier.weight(1f),
                        style = TextStyle(
                            color = MaterialTheme.colors.primaryVariant,
                            textAlign = TextAlign.End,
                            fontSize = 10.sp
                        )
                    )
                }
            }

        }
    }

}