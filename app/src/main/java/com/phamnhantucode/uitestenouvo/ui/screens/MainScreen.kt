package com.phamnhantucode.uitestenouvo.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import com.phamnhantucode.uitestenouvo.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.phamnhantucode.uitestenouvo.*
import com.phamnhantucode.uitestenouvo.domain.model.Approver

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController
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
                            navController.navigate(Screens.ApprovalMatrixScreen.route)
                        }
                        Divider()
                        ListFilterBar(filter = listOf())
                        ListMatrixBar()
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
    filter: List<String>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
//        filter.forEach {
//            FilterBar(
//                onClick = {
//
//                },
//                title = it,
//                isClicked = false
//                )
//        }
        FilterBar(
            onClick = {

            },
            title = "Default",
            isClicked = false
        )
        FilterBar(
            onClick = {

            },
            title = "Transfer Online",
            isClicked = true
        )
    }
}

@Composable
fun ListMatrixBar(

) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            CardApprovalMatrix(
                0,
                50000,
                1,
                listOf(Approver("TEST1, TEST2"))
            )
        }
        item {
            CardApprovalMatrix(
                50000,
                100000,
                2,
                listOf(Approver("TEST4, TEST5"), Approver("TEST1, TEST2, TEST3"))
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
    minimumIDR: Long = 0,
    maximumIDR: Long = 50000,
    numOfApproval: Int = 1,
    approver: List<Approver> = listOf(Approver("TEST1, TEST2"))
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                Color.LightGray,
                shape = RoundedCornerShape(15.dp)
            )
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
                            text = Utils.numberCovert(minimumIDR),
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
                            text = Utils.numberCovert(maximumIDR),
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
                    text = numOfApproval.toString(), modifier = Modifier.weight(1f),
                    style = TextStyle(
                        color = MaterialTheme.colors.primaryVariant,
                        textAlign = TextAlign.End,
                        fontSize = 10.sp
                    )
                )
            }
            Divider()
            for (i in 0 until approver.size) {
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
                        text = approver[i].approvers,
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