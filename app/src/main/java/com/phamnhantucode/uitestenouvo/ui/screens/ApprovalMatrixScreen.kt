package com.phamnhantucode.uitestenouvo.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.phamnhantucode.uitestenouvo.*
import com.phamnhantucode.uitestenouvo.R
import com.phamnhantucode.uitestenouvo.ui.theme.BrandingGrey
import com.phamnhantucode.uitestenouvo.ui.theme.LightGrey0
import com.phamnhantucode.uitestenouvo.ui.theme.LightGrey1

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ApprovalMatrixScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBarApprovalMatrixScreen() {
                navController.popBackStack()
            }
        }, content = {

            Surface(
                color = MaterialTheme.colors.primary
            ) {
                var listOptions by remember {
                    mutableStateOf(listOf("Default", "Transfer Online"))
                }
                var onShowListOption = remember {
                    mutableStateOf(false)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(Color.White)
                        .padding(horizontal = 25.dp, vertical = 15.dp)
                ) {
                    GroupInputField(
                        onShowListOption,
                        modifier = Modifier.weight(Float.MAX_VALUE)
                    )

                    //Buttons
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ButtonApprovalMatrixScreen(
                            textColor = Color.White,
                            backgroundColor = MaterialTheme.colors.primaryVariant,
                            title = stringResource(id = R.string.add_to_list).uppercase()) {

                        }
                        ButtonApprovalMatrixScreen(
                            textColor = MaterialTheme.colors.primaryVariant,
                            backgroundColor = Color.White,
                            title = stringResource(id = R.string.reset).uppercase()) {

                        }
                    }
                }
                //Bottom sheet list option
                BottomSheetSelection(listSelect = listOptions, onShowListOption, "Select Feature")
            }
        })
}

@Composable
fun GroupInputField(
    onShowListOption: MutableState<Boolean>,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        var value by remember {
            mutableStateOf("")
        }
        HeaderBar("Create New Approval Matrix")
        TopFieldBar(value = value,
            onClickSelectionField = {
                onShowListOption.value = true
            }
        ) {
            value = it
        }
        Divider()
        BottomFieldBar() {
            onShowListOption.value = true
        }
    }
}


@Composable
fun BottomSheetSelection(
    listSelect: List<String>,
    onShowListOption: MutableState<Boolean>,
    title: String
) {
    AnimatedVisibility(
        visible = onShowListOption.value,
        enter = slideInVertically(
            initialOffsetY = {
                it
            }
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = {
                it
            }
        ) + fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color(0x80000000))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 25.dp)
                ) {
                    //Header bottom sheet
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        //Header
                        Text(
                            text = title,
                            style = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            ),
                            modifier = Modifier.align(Alignment.Center)
                        )
                        //Icon exit
                        IconButton(
                            onClick = {
                                onShowListOption.value = false
                            },
                            modifier = Modifier.align(Alignment.CenterEnd)

                        ) {
                            Icon(
                                imageVector = Icons.Default.Close, contentDescription = "Close",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF444444))
                                    .padding(5.dp)
                            )


                        }
                    }

                    //Search bottom sheet
                    SearchField(value = "", onValueChange = {

                    })
                }
                //list selection
                CheckBoxGroup(listSelect = listSelect)
            }
        }
    }
}

@Composable
private fun CheckBoxGroup(
    listSelect: List<String>,
) {

    val (selectedOption: String, onOptionSelected: (String) -> Unit) = remember {
        mutableStateOf(
            listSelect[0]
        )
    }

    Column(
        Modifier
            .selectableGroup()
            .fillMaxWidth()
    ) {
        listSelect.forEach { text ->
            SelectOption(
                text = text,
                isSelectedOption = selectedOption == text,
                onSelectOption = onOptionSelected
            )
        }
    }
}

@Composable
fun SelectOption(
    text: String,
    isSelectedOption: Boolean,
    onSelectOption: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGrey0)
            .padding(vertical = 20.dp, horizontal = 25.dp)
            .clickable { onSelectOption(text) }
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 15.sp,
                color = Color.Black,
            )
        )
        Box(
            modifier = Modifier
                .size(15.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(Color.White)
                .border(
                    color = MaterialTheme.colors.primaryVariant,
                    width = 1.dp,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(1.dp)
        ) {
            if (isSelectedOption) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_stroke),
                    contentDescription = "icon stroke",
                    tint = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        maxLines = 1,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 15.sp,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 10.dp, horizontal = 14.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colors.primaryVariant
                )
                Box(
                    modifier = Modifier
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.search_keyword),
                            style = TextStyle(
                                color = Color.LightGray,
                                fontSize = 15.sp,
                            ),
                        )
                    } else
                        innerTextField()
                }
            }
        }
    )
}

@Composable
fun BottomFieldBar(
    onClickSelectionField: () -> Unit
) {
    var value by remember {
        mutableStateOf("")
    }
    var number by remember {
        mutableStateOf(0)
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        RangeOfApprovalField(type = "Minimum", value = value) {
            value = it
        }
        RangeOfApprovalField(type = "Maxmum", value = value) {
            value = it
        }
        NumOfApprovalField(value = if (number == 0) "" else number.toString(), onValueChange = {
            number = if (it.isBlank()) 0 else it.toInt()
        })
        //Approver selection
        for (i in 1..number) {
            SelectionField(
                listSelect = listOf(
                    "GROUP1, GROUP2",
                    "GROUP1, GROUP2, GROUP3"
                ),
                label = "${stringResource(id = R.string.approver)} (${stringResource(id = R.string.sequence)} $i)",
                onSelected = {

                },
                modifier = Modifier.clickable { onClickSelectionField() }
            )
        }
    }

}

@Composable
fun NumOfApprovalField(
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.number_of_approval),
            style = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
            ),
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(vertical = 10.dp, horizontal = 14.dp)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.input_number),
                            style = TextStyle(
                                color = Color.LightGray,
                                fontSize = 15.sp,
                            ),
                        )
                    } else
                        innerTextField()
                }
            }
        )
        //error text field
        Text(
            text = "",
            style = TextStyle(
                fontSize = 10.sp,
                color = Color.Red,
            )
        )
    }
}

@Composable
fun RangeOfApprovalField(
    type: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.range_of_approval) + " ($type)",
            style = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
            ),
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(vertical = 10.dp, horizontal = 14.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.currency),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 15.sp,
                        ),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.input_text_here),
                            style = TextStyle(
                                color = Color.LightGray,
                                fontSize = 15.sp,
                            ),
                        )
                    } else
                        innerTextField()
                }
            }
        )
        //error text field
        Text(
            text = "",
            style = TextStyle(
                fontSize = 10.sp,
                color = Color.Red,
            )
        )
    }
}

@Composable
fun TopFieldBar(
    value: String,
    onClickSelectionField: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        //Approval matrix alias
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.approval_matrix_alias),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                ),
            )
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                maxLines = 1,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .padding(vertical = 10.dp, horizontal = 14.dp)
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.holder_field_input_matrix_name),
                                style = TextStyle(
                                    color = Color.LightGray,
                                    fontSize = 15.sp,
                                ),
                            )
                        } else {
                            innerTextField()
                        }
                    }
                }
            )
            //error text field
            Text(
                text = "",
                style = TextStyle(
                    fontSize = 10.sp,
                    color = Color.Red,
                )
            )
        }

        //FeatureField
        SelectionField(
            modifier = Modifier.clickable { onClickSelectionField.invoke() },
            listSelect = listOf("Default", "Transfer Online"),
            label = stringResource(id = R.string.feature)
        ) {

        }
    }

}

@Composable
fun SelectionField(
    modifier: Modifier,
    listSelect: List<String>,
    label: String,
    onSelected: (String) -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
    ) {
        Text(
            text = label,
            style = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
            ),
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.select_feature),
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 15.sp,
                ),
                modifier = Modifier
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "KeyboardArrowDown",
                modifier = Modifier,
                tint = Color(0xFF919192)
            )

        }
        //error text field
        Text(
            text = "",
            style = TextStyle(
                fontSize = 10.sp,
                color = Color.Red,
            )
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarApprovalMatrixScreen(
    onBack: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack, contentDescription = "Back",
                modifier = Modifier
                    .padding(5.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { onBack.invoke() },
                tint = MaterialTheme.colors.primary
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colors.primary
        )
    )
}

@Composable
fun ButtonApprovalMatrixScreen(
    textColor: Color,
    backgroundColor: Color,
    title: String,
    enable: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (enable) backgroundColor else LightGrey1,
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (enable) textColor else BrandingGrey
            )
        )
    }
}

@Composable
fun HeaderBar(
    title: String,
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Divider(color = Color(0xFFD1D2D4))
    }

}

